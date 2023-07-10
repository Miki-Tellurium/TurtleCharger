package com.mikitellurium.turtlechargingstation.block;

import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.block.custom.ThunderchargeDynamoBlock;
import com.mikitellurium.turtlechargingstation.block.custom.TurtleChargingStationBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {

    public static final Block TURTLE_CHARGING_STATION_BLOCK = new TurtleChargingStationBlock();
    public static final Block THUNDERCHARGE_DYNAMO_BLOCK = new ThunderchargeDynamoBlock();

    private static void registerBlock(String name, Block block) {
        Identifier id = new Identifier(TurtleChargingStationMod.MOD_ID, name);
        Registry.register(Registries.BLOCK, id, block);
        Registry.register(Registries.ITEM, id, new BlockItem(block, new FabricItemSettings()));
    }

    public static void register() {
        registerBlock("turtle_charging_station", TURTLE_CHARGING_STATION_BLOCK);
        registerBlock("thundercharge_dynamo", THUNDERCHARGE_DYNAMO_BLOCK);
    }

}
