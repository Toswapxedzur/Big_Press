package com.minecart.bigpress.config;

import com.minecart.bigpress.BigPress;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ModConfigs {

    public static final ModStress SERVER_CONFIG;
    public static final ModConfigSpec SERVER_SPEC;

    static {
        Pair<ModStress, ModConfigSpec> serverPair = new ModConfigSpec.Builder()
                .configure(builder -> new ModStress());
        SERVER_CONFIG = serverPair.getLeft();
        SERVER_SPEC = serverPair.getRight();
    }

    public static void register(ModContainer modContainer) {
        modContainer.registerConfig(ModConfig.Type.SERVER, SERVER_SPEC);
    }
}
