package com.mikitellurium.turtlechargingstation;

import com.mikitellurium.turtlechargingstation.gui.ModScreens;
import com.mikitellurium.turtlechargingstation.networking.ModMessages;
import net.fabricmc.api.ClientModInitializer;

public class TurtleChargingStationClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        init();
    }

    private void init() {
        ModMessages.registerS2CPackets();
    }

}
