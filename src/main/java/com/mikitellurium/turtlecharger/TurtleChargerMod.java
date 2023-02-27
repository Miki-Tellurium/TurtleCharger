package com.mikitellurium.turtlecharger;

import com.mikitellurium.turtlecharger.block.ModBlocks;
import com.mikitellurium.turtlecharger.blockentity.ModBlockEntities;
import com.mikitellurium.turtlecharger.gui.ModMenuTypes;
import com.mikitellurium.turtlecharger.gui.TurtleChargerGui;
import com.mikitellurium.turtlecharger.item.ModItems;
import com.mikitellurium.turtlecharger.networking.ModMessages;
import com.mojang.logging.LogUtils;
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

@Mod(TurtleChargerMod.MOD_ID)
public class TurtleChargerMod {

    public static final String MOD_ID = "turtlecharger";
    private static final Logger LOGGER = LogUtils.getLogger();

    public TurtleChargerMod() {
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
            MenuScreens.register(ModMenuTypes.TURTLE_CHARGER_GUI.get(), TurtleChargerGui::new);
        }
    }

}
