package com.mikitellurium.turtlechargingstation;

import com.mikitellurium.turtlechargingstation.block.ModBlocks;
import com.mikitellurium.turtlechargingstation.blockentity.ModBlockEntities;
import com.mikitellurium.turtlechargingstation.config.Configuration;
import com.mikitellurium.turtlechargingstation.event.ModEvents;
import com.mikitellurium.turtlechargingstation.gui.ModScreens;
import com.mikitellurium.turtlechargingstation.item.ModCreativeTab;
import com.mikitellurium.turtlechargingstation.networking.ModMessages;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class TurtleChargingStationMod implements ModInitializer {
	public static final String MOD_ID = "turtlechargingstation";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		init();
	}

	private void init() {
		ModBlocks.register();
		ModBlockEntities.register();
		ModCreativeTab.register();
		ModScreens.registerHandlers();
		ModEvents.register();
		//ModMessages.registerC2SPackets();
		Configuration.register();
	}

}