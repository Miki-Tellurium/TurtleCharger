package com.mikitellurium.turtlecharginstation;

import com.mikitellurium.turtlecharginstation.block.ModBlocks;
import com.mikitellurium.turtlecharginstation.block.blockentity.ModBlockEntities;
import com.mikitellurium.turtlecharginstation.config.Configuration;
import com.mikitellurium.turtlecharginstation.gui.ModMenuTypes;
import com.mikitellurium.turtlecharginstation.gui.TurtleChargingStationGui;
import com.mikitellurium.turtlecharginstation.item.ModItems;
import com.mikitellurium.turtlecharginstation.networking.ModMessages;
import com.mojang.logging.LogUtils;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
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
    public static final CreativeModeTab TAB_TURTLE_CHARGING_STATION = new CreativeModeTab("turtle_charging_station_creative_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get());
        }
    };

    public TurtleChargingStationMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        registration(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
       LOGGER.info("STARTING COMMON INIT");
    }

    private void registration(IEventBus modEventBus) {
        Configuration.registerConfig();
        ModMessages.register();
        ModBlocks.register(modEventBus);
        ModBlockEntities.register(modEventBus);
        ModItems.register(modEventBus);
        ModMenuTypes.register(modEventBus);
    }

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            LOGGER.info("STARTING CLIENT INIT");
            MenuScreens.register(ModMenuTypes.TURTLE_CHARGING_STATION_GUI.get(), TurtleChargingStationGui::new);
        }
    }

}
