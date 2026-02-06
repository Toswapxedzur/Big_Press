package com.minecart.bigpress;

import com.minecart.bigpress.ponders.BigPressPonder;
import com.minecart.bigpress.register.ModBlocks;
import com.minecart.bigpress.register.ModPartialModel;
import com.minecart.bigpress.register.ModBlockEntityTypes;
import com.minecart.bigpress.infrastructure.config.ModConfigs;
import com.minecart.bigpress.register.ModItems;
import com.minecart.bigpress.register.ModRecipes;
import com.mojang.logging.LogUtils;
import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.createmod.catnip.lang.FontHelper;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.slf4j.Logger;

@Mod(BigPress.MODID)
public class BigPress {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "bigpress";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(BigPress.MODID);

    static {
        REGISTRATE.setCreativeTab(AllCreativeModeTabs.BASE_CREATIVE_TAB);
        REGISTRATE.setTooltipModifierFactory(item -> new ItemDescription.Modifier(item, FontHelper.Palette.STANDARD_CREATE)
                .andThen(TooltipModifier.mapNull(KineticStats.create(item))));
    }

    public BigPress(IEventBus modEventBus, ModContainer modContainer) {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modEventBus.addListener(this::commonSetup);

        REGISTRATE.registerEventListeners(modEventBus);
        ModBlocks.register();
        ModItems.register();
        ModBlockEntityTypes.register();
        ModRecipes.register(modEventBus);
        ModConfigs.register(modLoadingContext, modContainer);
        NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
    }

    @EventBusSubscriber(modid = MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            ModPartialModel.init();
            PonderIndex.addPlugin(new BigPressPonder());
        }
    }

    public static ResourceLocation modLoc(String path){
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
}
