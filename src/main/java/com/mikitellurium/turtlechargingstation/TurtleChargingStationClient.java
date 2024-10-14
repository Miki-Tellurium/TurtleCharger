package com.mikitellurium.turtlechargingstation;

import com.mikitellurium.turtlechargingstation.networking.ModMessages;
import com.mikitellurium.turtlechargingstation.registry.ModHandledScreens;
import net.fabricmc.api.ClientModInitializer;

public class TurtleChargingStationClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModHandledScreens.init();
        ModMessages.registerS2CPackets();
    }

}
