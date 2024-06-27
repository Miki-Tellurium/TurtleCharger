package com.mikitellurium.turtlechargingstation.registry;

import com.mikitellurium.telluriumforge.registry.RegistryHelper;
import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.blockentity.ThunderchargeDynamoBlockEntity;
import com.mikitellurium.turtlechargingstation.blockentity.TurtleChargingStationBlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import team.reborn.energy.api.EnergyStorage;

public class ModBlockEntities {

    private static final RegistryHelper registry = TurtleChargingStationMod.registryHelper();

    public static BlockEntityType<TurtleChargingStationBlockEntity> TURTLE_CHARGING_STATION = registry.registerBlockEntity(
            "turtle_charging_station", BlockEntityType.Builder.create(TurtleChargingStationBlockEntity::new,
                    ModBlocks.TURTLE_CHARGING_STATION_BLOCK).build());

    public static BlockEntityType<ThunderchargeDynamoBlockEntity> THUNDERCHARGE_DYNAMO = registry.registerBlockEntity(
            "thundercharge_dynamo", BlockEntityType.Builder.create(ThunderchargeDynamoBlockEntity::new,
                    ModBlocks.THUNDERCHARGE_DYNAMO_BLOCK).build());

    public static void init() {
        EnergyStorage.SIDED.registerForBlockEntity(TurtleChargingStationBlockEntity::registerEnergyStorage, TURTLE_CHARGING_STATION);
    }

}
