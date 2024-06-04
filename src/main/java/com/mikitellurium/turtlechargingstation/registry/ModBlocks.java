package com.mikitellurium.turtlechargingstation.registry;

import com.mikitellurium.telluriumforge.registry.RegistryHelper;
import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.block.ThunderchargeDynamoBlock;
import com.mikitellurium.turtlechargingstation.block.TurtleChargingStationBlock;
import net.minecraft.block.Block;

public class ModBlocks {

    private static final RegistryHelper registry = TurtleChargingStationMod.registryHelper();

    public static final Block TURTLE_CHARGING_STATION_BLOCK = registry.registerBlockWithItem(
            "turtle_charging_station", new TurtleChargingStationBlock());
    public static final Block THUNDERCHARGE_DYNAMO_BLOCK = registry.registerBlockWithItem(
            "thundercharge_dynamo", new ThunderchargeDynamoBlock());

    public static void init() {}

}
