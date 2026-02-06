package com.minecart.bigpress.compat.jei;

import com.minecart.bigpress.BigPress;
import com.minecart.bigpress.compat.jei.category.CompressingCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;

@JeiPlugin
public class BigPressJEI implements IModPlugin {
    public static final ResourceLocation ID = BigPress.modLoc("jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(CompressingCategory.INFO);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        CompressingCategory.INFO.registerRecipes(registration);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        CompressingCategory.INFO.registerCatalysts(registration);
    }
}
