package com.mikitellurium.turtlechargingstation.registry;

import com.mikitellurium.telluriumforge.registry.RegistryHelper;
import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.gui.TurtleChargingStationScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers {

    private static final RegistryHelper registry = TurtleChargingStationMod.registryHelper();

    public static ScreenHandlerType<TurtleChargingStationScreenHandler> TURTLE_CHARGING_STATION;

    public static void init() {
        TURTLE_CHARGING_STATION = Registry.register(Registries.SCREEN_HANDLER, registry.modIdentifier(registry.modId()), new ExtendedScreenHandlerType<>(TurtleChargingStationScreenHandler::new));
    }

}
