package com.minecart.bigpress.block_entity;

import com.minecart.bigpress.BigPress;
import com.minecart.bigpress.block.ModBlocks;
import com.minecart.bigpress.block_entity_renderer.BigPressRenderer;
import com.minecart.bigpress.visual.BigPressVisual;
import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class ModBlockEntityTypes {
    public static final BlockEntityEntry<BigPressBlockEntity> BIGPRESS = BigPress.REGISTRATE
            .blockEntity("big_mechanical_press", BigPressBlockEntity::new)
            .visual(() -> BigPressVisual::new)
            .validBlock(ModBlocks.BIGPRESS)
            .renderer(() -> BigPressRenderer::new)
            .register();

    public static void register(){
    }
}
