package com.mikitellurium.turtlechargingstation;

import com.mikitellurium.telluriumforge.registry.RegistryHelper;
import com.mikitellurium.turtlechargingstation.networking.ModMessages;
import com.mikitellurium.turtlechargingstation.registry.ModBlocks;
import com.mikitellurium.turtlechargingstation.registry.ModBlockEntities;
import com.mikitellurium.turtlechargingstation.config.ModConfig;
import com.mikitellurium.turtlechargingstation.event.ModEvents;
import com.mikitellurium.turtlechargingstation.gui.ModScreens;
import com.mikitellurium.turtlechargingstation.registry.ModCreativeTab;
import com.mikitellurium.turtlechargingstation.registry.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TurtleChargingStationMod implements ModInitializer {
	public static final String MOD_ID = "turtlechargingstation";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	private static final RegistryHelper REGISTRY_HELPER = new RegistryHelper(MOD_ID);

	@Override
	public void onInitialize() {
		init();
	}

	private void init() {
		ModItems.init();
		ModBlocks.init();
		ModBlockEntities.init();
		ModCreativeTab.init();
		ModScreens.init();
		ModEvents.register();
		ModMessages.registerC2SPackets();
		ModConfig.register();
	}

	public static RegistryHelper registryHelper() {
		return REGISTRY_HELPER;
	}

}