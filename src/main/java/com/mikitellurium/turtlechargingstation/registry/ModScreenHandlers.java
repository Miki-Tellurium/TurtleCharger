package com.mikitellurium.turtlechargingstation.registry;

import com.mikitellurium.telluriumforge.registry.InitializedRegistry;
import com.mikitellurium.telluriumforge.registry.RegistryHelper;
import com.mikitellurium.turtlechargingstation.gui.TurtleChargingStationScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers implements InitializedRegistry {

    public static ScreenHandlerType<TurtleChargingStationScreenHandler> TURTLE_CHARGING_STATION;

    @Override
    public void init(RegistryHelper helper) {
        TURTLE_CHARGING_STATION = helper.registerScreenHandler("turtle_charging_station", new ExtendedScreenHandlerType<>(TurtleChargingStationScreenHandler::new));
    }

}
