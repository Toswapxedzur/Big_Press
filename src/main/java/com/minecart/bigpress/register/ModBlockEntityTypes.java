package com.minecart.bigpress.register;

import com.minecart.bigpress.BigPress;
import com.minecart.bigpress.content.contraptions.big_press.BigPressBlockEntity;
import com.minecart.bigpress.content.contraptions.big_press.BigPressRenderer;
import com.minecart.bigpress.content.contraptions.big_press.BigPressVisual;
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
