package com.mikitellurium.turtlechargingstation.registry;

import com.mikitellurium.turtlechargingstation.gui.TurtleChargingStationScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ModHandledScreens {

    public static void init() {
        HandledScreens.register(ModScreenHandlers.TURTLE_CHARGING_STATION, TurtleChargingStationScreen::new);
    }

}
