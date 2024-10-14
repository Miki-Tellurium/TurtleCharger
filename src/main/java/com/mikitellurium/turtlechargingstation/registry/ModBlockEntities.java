package com.mikitellurium.turtlechargingstation.registry;

import com.mikitellurium.telluriumforge.registry.InitializedRegistry;
import com.mikitellurium.telluriumforge.registry.RegistryHelper;
import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.blockentity.ThunderchargeDynamoBlockEntity;
import com.mikitellurium.turtlechargingstation.blockentity.TurtleChargingStationBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import team.reborn.energy.api.EnergyStorage;

public class ModBlockEntities implements InitializedRegistry {

    public static BlockEntityType<TurtleChargingStationBlockEntity> TURTLE_CHARGING_STATION;
    public static BlockEntityType<ThunderchargeDynamoBlockEntity> THUNDERCHARGE_DYNAMO;

    @Override
    public void init(RegistryHelper helper) {
        TURTLE_CHARGING_STATION = helper.registerBlockEntity("turtle_charging_station", TurtleChargingStationBlockEntity::new, ModBlocks.TURTLE_CHARGING_STATION_BLOCK);
        THUNDERCHARGE_DYNAMO = helper.registerBlockEntity("thundercharge_dynamo", ThunderchargeDynamoBlockEntity::new, ModBlocks.THUNDERCHARGE_DYNAMO_BLOCK);
        EnergyStorage.SIDED.registerForBlockEntity(TurtleChargingStationBlockEntity::registerEnergyStorage, TURTLE_CHARGING_STATION);
    }

}
