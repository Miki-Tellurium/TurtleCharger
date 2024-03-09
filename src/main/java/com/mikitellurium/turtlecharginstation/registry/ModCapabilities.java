package com.mikitellurium.turtlecharginstation.registry;

import com.mikitellurium.turtlecharginstation.blockentity.ThunderchargeDynamoBlockEntity;
import com.mikitellurium.turtlecharginstation.blockentity.TurtleChargingStationBlockEntity;
import net.neoforged.bus.api.IEventBus;

public class ModCapabilities {

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(TurtleChargingStationBlockEntity::registerCapability);
        modEventBus.addListener(ThunderchargeDynamoBlockEntity::registerCapability);
    }

}
