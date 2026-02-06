package com.minecart.bigpress.ponders;

import com.simibubi.create.AllItems;
import com.simibubi.create.foundation.ponder.CreateSceneBuilder;
import net.createmod.ponder.api.PonderPalette;
import net.createmod.ponder.api.scene.SceneBuilder;
import net.createmod.ponder.api.scene.SceneBuildingUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class BPProcessingScenes {
    public static void compressing(SceneBuilder builder, SceneBuildingUtil util){
        CreateSceneBuilder scene = new CreateSceneBuilder(builder);
        scene.title("big_mechanical_press", "The Big Mechanical Press");
        scene.configureBasePlate(0, 0, 6);
        scene.showBasePlate();

        // Define Coordinates
        BlockPos bigPressPos = util.grid().at(2, 3, 3);
        BlockPos bigDepotPos = util.grid().at(2, 1, 3);
        BlockPos smallPressPos = util.grid().at(2, 3, 1);
        BlockPos smallDepotPos = util.grid().at(2, 1, 1);

        // 1. Show the shafts and depots first (Level 0 and 1)
        scene.world().showSection(util.select().fromTo(0, 0, 0, 5, 2, 5), Direction.UP);
        scene.idle(10);

        // 2. Show the Small Press (Standard)
        scene.world().showSection(util.select().position(smallPressPos), Direction.DOWN);
        scene.idle(5);

        // 3. Show the Big Press (Your Mod)
        scene.world().showSection(util.select().position(bigPressPos), Direction.DOWN);
        scene.idle(10);

        // 4. Comparison Text
        scene.overlay().showText(60)
                .text("The Big Mechanical Press works just like the standard one...")
                .pointAt(util.vector().topOf(bigPressPos))
                .placeNearTarget();
        scene.idle(70);

        scene.overlay().showText(60)
                .text("...but it requires a 2-block gap between the machine and the processing surface.")
                .pointAt(util.vector().centerOf(bigPressPos.below()))
                .placeNearTarget();
        scene.idle(50);

        scene.overlay().showOutline(PonderPalette.RED, "gap", util.select().fromTo(2, 2, 3, 2, 2, 3), 40);
        scene.idle(60);

        ItemStack iron = new ItemStack(Items.IRON_INGOT);
        scene.world().createItemOnBeltLike(smallDepotPos, Direction.NORTH, iron);
        scene.world().createItemOnBeltLike(bigDepotPos, Direction.NORTH, iron);
        scene.idle(20);

        scene.world().setKineticSpeed(util.select().everywhere(), 64);

        scene.world().setKineticSpeed(util.select().position(smallPressPos), -64);
        scene.world().setKineticSpeed(util.select().position(bigPressPos), -64);

        scene.idle(40); // Wait for press to hit bottom

        // 7. Success Effects
        scene.effects().indicateSuccess(smallDepotPos);
        scene.effects().indicateSuccess(bigDepotPos);

        // Change items to sheets
        ItemStack sheet = new ItemStack(AllItems.IRON_SHEET.asItem());
        scene.world().removeItemsFromBelt(smallDepotPos);
        scene.world().removeItemsFromBelt(bigDepotPos);
        scene.world().createItemOnBeltLike(smallDepotPos, Direction.UP, sheet);
        scene.world().createItemOnBeltLike(bigDepotPos, Direction.UP, sheet);

        scene.idle(20);
        scene.overlay().showText(50)
                .text("It can process items on Depots and Belts.")
                .colored(PonderPalette.GREEN)
                .pointAt(util.vector().topOf(bigDepotPos))
                .placeNearTarget();
        scene.idle(60);
    }
}
