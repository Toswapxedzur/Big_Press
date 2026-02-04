package com.minecart.bigpress.block_entity;

import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.belt.BeltHelper;
import com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour;
import com.simibubi.create.content.kinetics.belt.behaviour.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.content.kinetics.belt.transport.TransportedItemStack;
import com.simibubi.create.content.kinetics.press.PressingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import net.createmod.catnip.math.VecHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour.ProcessingResult.HOLD;
import static com.simibubi.create.content.kinetics.belt.behaviour.BeltProcessingBehaviour.ProcessingResult.PASS;

public class CompressingBehaviour extends BeltProcessingBehaviour {
    public static final int BREAKTIME = 160;

    public Phase phase = Phase.IDLE;
    public int runningTicks;
    public int prevRunningTicks;
    public int currentRecipeDuration;
    public boolean running;

    public List<ItemStack> particleItems = new ArrayList<>();
    public CompressingBehaviourSpecifics specifics;

    public interface CompressingBehaviourSpecifics {
        boolean tryProcessOnBelt(TransportedItemStack input, List<ItemStack> outputList, boolean simulate);

        int getProcessingTime(TransportedItemStack input);

        int getMinSpeedRequire(TransportedItemStack input);

        boolean canProcessInBulk();

        void onPressingCompleted();

        int getParticleAmount();

        float getKineticSpeed();
    }

    public enum Phase {
        IDLE, EXTEND, COMPRESS, CONTRACT
    }

    public <T extends SmartBlockEntity & CompressingBehaviourSpecifics> CompressingBehaviour(T be) {
        super(be);
        this.specifics = be;
        whenItemEnters((s, i) -> onItemReceived(s, i, this));
        whileItemHeld((s, i) -> whenItemHeld(s, i, this));
    }

    // --- LOGIC: Callbacks ---

    private static ProcessingResult onItemReceived(TransportedItemStack transported,
                                                   TransportedItemStackHandlerBehaviour handler, CompressingBehaviour behaviour) {
        float currentSpeed = behaviour.specifics.getKineticSpeed();

        // 1. Basic Check: Is machine stopped or already busy?
        if (currentSpeed == 0 || behaviour.running)
            return ProcessingResult.PASS;

        // 2. Check Recipe Validity
        if (!behaviour.specifics.tryProcessOnBelt(transported, null, true))
            return ProcessingResult.PASS;

        // 3. Check Minimum Speed Requirement
        int requiredSpeed = behaviour.specifics.getMinSpeedRequire(transported);
        if (Math.abs(currentSpeed) < requiredSpeed) {
            // Machine is moving, but too slow for this dense material
            return ProcessingResult.PASS; // Let it go!
        }

        // 4. Check Duration
        int time = behaviour.specifics.getProcessingTime(transported);
        if (time <= 0)
            return ProcessingResult.PASS;

        // 5. Start Process
        behaviour.start(time);
        return ProcessingResult.HOLD;
    }

    private static ProcessingResult whenItemHeld(TransportedItemStack transported, TransportedItemStackHandlerBehaviour handler,
                                                 CompressingBehaviour behaviour) {
        if (behaviour.running) {

            // Only finalize if in COMPRESS phase and duration is met
            if (behaviour.phase == Phase.COMPRESS && behaviour.runningTicks >= behaviour.currentRecipeDuration) {

                ArrayList<ItemStack> results = new ArrayList<>();
                behaviour.specifics.tryProcessOnBelt(transported, results, false);

                boolean bulk = behaviour.specifics.canProcessInBulk() || transported.stack.getCount() == 1;
                transported.clearFanProcessingData();

                List<TransportedItemStack> collect = results.stream()
                        .map(stack -> {
                            TransportedItemStack copy = transported.copy();
                            boolean centered = BeltHelper.isItemUpright(stack);
                            copy.stack = stack;
                            copy.locked = true;
                            copy.angle = centered ? 180 : Create.RANDOM.nextInt(360);
                            return copy;
                        })
                        .collect(Collectors.toList());

                if (bulk) {
                    if (collect.isEmpty())
                        handler.handleProcessingOnItem(transported, TransportedItemStackHandlerBehaviour.TransportedResult.removeItem());
                    else
                        handler.handleProcessingOnItem(transported, TransportedItemStackHandlerBehaviour.TransportedResult.convertTo(collect));
                } else {
                    TransportedItemStack left = transported.copy();
                    left.stack.shrink(1);
                    if (collect.isEmpty())
                        handler.handleProcessingOnItem(transported, TransportedItemStackHandlerBehaviour.TransportedResult.convertTo(left));
                    else
                        handler.handleProcessingOnItem(transported, TransportedItemStackHandlerBehaviour.TransportedResult.convertToAndLeaveHeld(collect, left));
                }

                behaviour.setPhase(Phase.CONTRACT);
            }
            return ProcessingResult.HOLD;
        }
        return ProcessingResult.PASS;
    }

    public void start(int recipeDuration) {
        this.currentRecipeDuration = recipeDuration;
        this.running = true;
        this.particleItems.clear();
        setPhase(Phase.EXTEND);
    }

    public void setPhase(Phase newPhase) {
        this.phase = newPhase;
        this.runningTicks = 0;
        this.prevRunningTicks = 0;
        blockEntity.sendData();
    }

    @Override
    public void tick() {
        super.tick();
        Level level = getWorld();
        if (level == null) return;

        prevRunningTicks = runningTicks;

        if (!running || phase == Phase.IDLE) {
            runningTicks = 0;
            return;
        }

        float speed = specifics.getKineticSpeed();
        if (speed == 0) return;

        int movementSpeed = (int) Mth.lerp(Mth.clamp(Math.abs(speed) / 512f, 0, 1), 1, 60);

        switch (phase) {
            case EXTEND:
                runningTicks += movementSpeed;
                if (runningTicks >= BREAKTIME / 2) {
                    setPhase(Phase.COMPRESS);
                }
                break;

            case COMPRESS:
                if (level.isClientSide && (runningTicks % 3 == 0)) spawnParticles();
                runningTicks += movementSpeed;
                break;

            case CONTRACT:
                runningTicks += movementSpeed;
                if (runningTicks >= BREAKTIME / 2) {
                    running = false;
                    setPhase(Phase.IDLE);
                    specifics.onPressingCompleted();
                }
                break;
        }
    }

    // --- VISUALS ---

    public float getRenderedHeadOffset(float partialTicks) {
        if (!running || phase == Phase.IDLE) return 0f;
        if (phase == Phase.COMPRESS) return 1f;

        float currentTick = Mth.lerp(partialTicks, prevRunningTicks, runningTicks);
        float target = BREAKTIME / 2f;

        if (phase == Phase.EXTEND) {
            float progress = Mth.clamp(currentTick / target, 0, 1);
            return (float) Math.pow(progress, 3);
        }
        else if (phase == Phase.CONTRACT) {
            float progress = Mth.clamp(currentTick / target, 0, 1);
            return 1f - progress;
        }
        return 0f;
    }

    protected void spawnParticles() {
        if (particleItems.isEmpty() || getWorld().isClientSide) return;

        BlockPos pos = getPos();
        Vec3 vec = VecHelper.getCenterOf(pos).add(0, -0.5f, 0);

        for (ItemStack stack : particleItems) {
            ItemParticleOption data = new ItemParticleOption(ParticleTypes.ITEM, stack);
            for (int i = 0; i < specifics.getParticleAmount(); i++) {
                Vec3 m = VecHelper.offsetRandomly(Vec3.ZERO, getWorld().random, .125f);
                getWorld().addParticle(data, vec.x, vec.y, vec.z, m.x, m.y, m.z);
            }
        }
    }

    @Override
    public void write(CompoundTag nbt, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(nbt, registries, clientPacket);
        nbt.putInt("Phase", phase.ordinal());
        nbt.putInt("RunningTicks", runningTicks);
        nbt.putInt("RecipeDuration", currentRecipeDuration);
        nbt.putBoolean("Running", running);
    }

    @Override
    public void read(CompoundTag nbt, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(nbt, registries, clientPacket);
        phase = Phase.values()[nbt.getInt("Phase")];
        runningTicks = nbt.getInt("RunningTicks");
        prevRunningTicks = runningTicks;
        currentRecipeDuration = nbt.getInt("RecipeDuration");
        running = nbt.getBoolean("Running");
    }
}
