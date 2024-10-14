package com.mikitellurium.turtlechargingstation.registry;

import com.mikitellurium.telluriumforge.registry.InitializedRegistry;
import com.mikitellurium.telluriumforge.registry.RegistryHelper;
import com.mikitellurium.turtlechargingstation.gui.TurtleChargingStationScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ModHandledScreens implements InitializedRegistry {

    @Override
    public void init(RegistryHelper helper) {
        helper.registerHandledScreen(ModScreenHandlers.TURTLE_CHARGING_STATION, TurtleChargingStationScreen::new);
    }

}
