package com.minecart.bigpress.ponders;

import com.minecart.bigpress.BigPress;
import com.minecart.bigpress.register.ModBlocks;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class ModPonderScenes {
    public static void register(PonderSceneRegistrationHelper<ResourceLocation> helper){
        PonderSceneRegistrationHelper<ItemProviderEntry<?, ?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        HELPER.forComponents(ModBlocks.BIGPRESS).addStoryBoard("big_mechanical_press/compressing", BPProcessingScenes::compressing);
    }
}
