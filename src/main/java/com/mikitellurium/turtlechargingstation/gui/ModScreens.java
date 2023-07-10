package com.mikitellurium.turtlechargingstation.gui;

import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreens {

    public static ScreenHandlerType<TurtleChargingStationScreenHandler> TURTLE_CHARGING_STATION_SCREEN_HANDLER =
            new ExtendedScreenHandlerType<>(TurtleChargingStationScreenHandler::new);

    public static void registerHandlers() {
        Registry.register(Registries.SCREEN_HANDLER, new Identifier(TurtleChargingStationMod.MOD_ID,
                        "turtle_charging_station"), TURTLE_CHARGING_STATION_SCREEN_HANDLER);
    }

    public static void registerScreens() {
        HandledScreens.register(TURTLE_CHARGING_STATION_SCREEN_HANDLER, TurtleChargingStationScreen::new);
    }

}
