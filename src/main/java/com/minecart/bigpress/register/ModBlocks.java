package com.minecart.bigpress.register;

import com.minecart.bigpress.content.contraptions.big_press.BigPressBlock;
import com.minecart.bigpress.infrastructure.config.ModStress;
import com.simibubi.create.content.processing.AssemblyOperatorBlockItem;
import com.simibubi.create.foundation.data.*;
import com.tterrag.registrate.util.entry.BlockEntry;

import static com.minecart.bigpress.BigPress.REGISTRATE;

public class ModBlocks {

    public static final BlockEntry<BigPressBlock> BIGPRESS = REGISTRATE
            .block("big_mechanical_press", BigPressBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(properties -> properties.noOcclusion())
            .blockstate(BlockStateGen.horizontalBlockProvider(true))
            .transform(ModStress.setImpact(16.0))
            .transform(TagGen.axeOrPickaxe())
            .item(AssemblyOperatorBlockItem::new)
            .transform(ModelGen.customItemModel())
            .register();

    public static void register(){
    }
}
