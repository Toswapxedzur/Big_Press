package com.minecart.bigpress.block;

import com.minecart.bigpress.BigPress;
import com.minecart.bigpress.block_entity.BigPressBlockEntity;
import com.minecart.bigpress.block_entity_renderer.BigPressRenderer;
import com.minecart.bigpress.visual.BigPressVisual;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.data.*;
import com.simibubi.create.infrastructure.config.CStress;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Blocks;

import static com.minecart.bigpress.BigPress.REGISTRATE;

public class ModBlocks {

    public static final BlockEntry<BigPressBlock> BIGPRESS = REGISTRATE
            .block("big_mechanical_press", BigPressBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(properties -> properties.noOcclusion())
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .transform(TagGen.axeOrPickaxe())
            .item(AssemblyOperatorBlockItem::new)
            .transform(ModelGen.customItemModel())
            .register();

    public static void register(){
    }
}
