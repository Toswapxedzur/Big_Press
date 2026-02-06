package com.minecart.bigpress.infrastructure.data.recipe;

import com.minecart.bigpress.BigPress;
import com.minecart.bigpress.content.recipes.compressing.CompressingRecipeGen;
import com.simibubi.create.AllItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class BPCompressingRecipeGen extends CompressingRecipeGen {
    public final GeneratedRecipe GOLDEN_SHEET = compress(Items.GOLD_INGOT, AllItems.GOLDEN_SHEET, 400);

    public BPCompressingRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BigPress.MODID);
    }
}
