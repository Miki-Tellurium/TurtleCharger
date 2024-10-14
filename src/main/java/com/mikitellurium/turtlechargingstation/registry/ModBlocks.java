package com.mikitellurium.turtlechargingstation.registry;

import com.mikitellurium.telluriumforge.registry.InitializedRegistry;
import com.mikitellurium.telluriumforge.registry.RegistryHelper;
import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.block.ThunderchargeDynamoBlock;
import com.mikitellurium.turtlechargingstation.block.TurtleChargingStationBlock;
import net.minecraft.block.Block;

public class ModBlocks implements InitializedRegistry {

    public static Block TURTLE_CHARGING_STATION_BLOCK;
    public static Block THUNDERCHARGE_DYNAMO_BLOCK;

    @Override
    public void init(RegistryHelper helper) {
        TURTLE_CHARGING_STATION_BLOCK = helper.registerBlockWithItem("turtle_charging_station", new TurtleChargingStationBlock());
        THUNDERCHARGE_DYNAMO_BLOCK = helper.registerBlockWithItem("thundercharge_dynamo", new ThunderchargeDynamoBlock());
    }

}
