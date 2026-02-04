package com.minecart.bigpress.block;

import com.minecart.bigpress.BigPress;
import com.minecart.bigpress.block_entity.BigPressBlockEntity;
import com.minecart.bigpress.block_entity_renderer.BigPressRenderer;
import com.minecart.bigpress.visual.BigPressVisual;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.ModelGen;
import com.simibubi.create.foundation.data.TagGen;
import com.simibubi.create.infrastructure.config.CStress;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Blocks;

import static com.minecart.bigpress.BigPress.REGISTRATE;

public class ModBlocks {

    public static final BlockEntry<BigPressBlock> BIGPRESS = REGISTRATE
            .block("big_mechanical_press", BigPressBlock::new)
            .initialProperties(AllBlocks.MECHANICAL_PRESS)
            .properties(properties -> properties.noOcclusion())
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .transform(TagGen.axeOrPickaxe())
            .item()
            .transform(ModelGen.customItemModel())
            .register();

    public static void register(){
    }
}
