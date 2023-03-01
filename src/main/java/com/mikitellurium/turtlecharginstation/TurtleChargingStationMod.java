package com.mikitellurium.turtlecharginstation;

import com.mikitellurium.turtlecharginstation.block.ModBlocks;
import com.mikitellurium.turtlecharginstation.blockentity.ModBlockEntities;
import com.mikitellurium.turtlecharginstation.gui.ModMenuTypes;
import com.mikitellurium.turtlecharginstation.gui.TurtleChargerGui;
import com.mikitellurium.turtlecharginstation.item.ModItems;
import com.mikitellurium.turtlecharginstation.networking.ModMessages;
import com.mojang.logging.LogUtils;
import dan200.computercraft.ComputerCraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(TurtleChargingStationMod.MOD_ID)
public class TurtleChargingStationMod {

    public static final String MOD_ID = "turtlechargingstation";
    private static final Logger LOGGER = LogUtils.getLogger();

    public TurtleChargingStationMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        ModMessages.register();
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModMenuTypes.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
       LOGGER.info("STARTING COMMON INIT");
    }


    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            LOGGER.info("STARTING CLIENT INIT");
            MenuScreens.register(ModMenuTypes.TURTLE_CHARGING_STATION_GUI.get(), TurtleChargerGui::new);
        }
    }

}
