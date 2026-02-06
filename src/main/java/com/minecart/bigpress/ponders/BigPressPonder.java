package com.minecart.bigpress.ponders;

import com.minecart.bigpress.BigPress;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class BigPressPonder implements PonderPlugin {
    @Override
    public String getModId() {
        return BigPress.MODID;
    }

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderPlugin.super.registerScenes(helper);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderPlugin.super.registerTags(helper);
    }
}
