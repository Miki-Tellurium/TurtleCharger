package com.mikitellurium.turtlechargingstation;

import com.mikitellurium.telluriumforge.registry.RegistryHelper;
import com.mikitellurium.turtlechargingstation.networking.ModMessages;
import com.mikitellurium.turtlechargingstation.registry.ModHandledScreens;
import net.fabricmc.api.ClientModInitializer;

public class TurtleChargingStationClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        RegistryHelper.initRegistry(TurtleChargingStationMod.registryHelper(), new ModHandledScreens());
        ModMessages.registerS2CPackets();
    }

}
