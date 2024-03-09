package com.mikitellurium.turtlecharginstation;

import com.mikitellurium.turtlecharginstation.config.ModConfigs;
import com.mikitellurium.turtlecharginstation.registry.*;
import com.mikitellurium.turtlecharginstation.networking.ModMessages;
import com.mojang.logging.LogUtils;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import org.slf4j.Logger;

@Mod(TurtleChargingStationMod.MOD_ID)
public class TurtleChargingStationMod {

    public static final String MOD_ID = "turtlechargingstation";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TurtleChargingStationMod(IEventBus modEventBus) {
        this.registration(modEventBus);
        modEventBus.addListener(this::commonSetup);
        modEventBus.addListener(this::addCreativeTab);

        //NeoForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }

    public void addCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == ModCreativeTab.TAB_TURTLECHARGINGSTATION.get()) {
            event.accept(ModBlocks.TURTLE_CHARGING_STATION_BLOCK);
            event.accept(ModBlocks.THUNDERCHARGE_DYNAMO_BLOCK);
        }
    }

    private void registration(IEventBus modEventBus) {
        ModConfigs.register();
        ModMessages.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModCreativeTab.register(modEventBus);
        ModMenuTypes.register(modEventBus);
        ModCapabilities.register(modEventBus);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
        }
    }

}
