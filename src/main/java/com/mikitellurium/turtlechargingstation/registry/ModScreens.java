package com.mikitellurium.turtlechargingstation.registry;

import com.mikitellurium.telluriumforge.registry.RegistryHelper;
import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.blockentity.TurtleChargingStationBlockEntity;
import com.mikitellurium.turtlechargingstation.gui.TurtleChargingStationScreen;
import com.mikitellurium.turtlechargingstation.gui.TurtleChargingStationScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreens {

    private static final RegistryHelper registry = TurtleChargingStationMod.registryHelper();

    public static ScreenHandlerType<TurtleChargingStationScreenHandler> TURTLE_CHARGING_STATION_SCREEN_HANDLER = registry.registerScreen(
            "turtle_charging_station", new ExtendedScreenHandlerType<>(TurtleChargingStationScreenHandler::new,
                    TurtleChargingStationBlockEntity.Data.PACKET_CODEC), TurtleChargingStationScreen::new);

    public static void init() {}

}
