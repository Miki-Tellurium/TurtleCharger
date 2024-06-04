package com.mikitellurium.turtlechargingstation.gui;

import com.mikitellurium.telluriumforge.registry.RegistryHelper;
import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreens {

    private static final RegistryHelper registry = TurtleChargingStationMod.registryHelper();

    public static ScreenHandlerType<TurtleChargingStationScreenHandler> TURTLE_CHARGING_STATION_SCREEN_HANDLER = registry.registerScreen(
            "turtle_charging_station", new ExtendedScreenHandlerType<>(TurtleChargingStationScreenHandler::new),
            TurtleChargingStationScreen::new);

    public static void init() {}

}
