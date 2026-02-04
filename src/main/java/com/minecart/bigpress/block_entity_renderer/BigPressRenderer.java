package com.minecart.bigpress.block_entity_renderer;

import com.minecart.bigpress.block.ModPartialModel;
import com.minecart.bigpress.block_entity.BigPressBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;
import dev.engine_room.flywheel.api.visualization.VisualizationManager;
import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.phys.Vec3;

public class BigPressRenderer extends KineticBlockEntityRenderer<BigPressBlockEntity> {
    public BigPressRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public boolean shouldRenderOffScreen(BigPressBlockEntity p_112306_) {
        return true;
    }

    @Override
    protected void renderSafe(BigPressBlockEntity be, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        super.renderSafe(be, partialTicks, ms, buffer, light, overlay);

        if (VisualizationManager.supportsVisualization(be.getLevel()))
            return;

        BlockState blockState = be.getBlockState();

        SuperByteBuffer cachedHead = CachedBuffers.partialFacing(ModPartialModel.BIG_PRESS_HEAD, blockState, blockState.getValue(BlockStateProperties.HORIZONTAL_FACING));
        cachedHead.renderInto(ms, buffer.getBuffer(RenderType.SOLID));
    }

    @Override
    protected BlockState getRenderedBlockState(BigPressBlockEntity be) {
        return shaft(getRotationAxisOf(be));
    }
}
