package com.minecart.bigpress.ponders;

import com.minecart.bigpress.register.ModBlocks;
import com.simibubi.create.infrastructure.ponder.AllCreatePonderTags;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.catnip.registry.RegisteredObjectsHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

public class ModPonderTags {

    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper){
        PonderTagRegistrationHelper<RegistryEntry<?, ?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        PonderTagRegistrationHelper<ItemLike> itemHelper = helper.withKeyFunction(
                RegisteredObjectsHelper::getKeyOrThrow);

        itemHelper.addToTag(AllCreatePonderTags.KINETIC_APPLIANCES, ModBlocks.BIGPRESS.getId());
    }
}
