package com.mikitellurium.turtlechargingstation.registry;

import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.blockentity.ThunderchargeDynamoBlockEntity;
import com.mikitellurium.turtlechargingstation.blockentity.TurtleChargingStationBlockEntity;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import team.reborn.energy.api.EnergyStorage;

public class ModBlockEntities {

    public static BlockEntityType<TurtleChargingStationBlockEntity> TURTLE_CHARGING_STATION = FabricBlockEntityTypeBuilder.create(
            TurtleChargingStationBlockEntity::new, ModBlocks.TURTLE_CHARGING_STATION_BLOCK).build();

    public static BlockEntityType<ThunderchargeDynamoBlockEntity> THUNDERCHARGE_DYNAMO =  FabricBlockEntityTypeBuilder.create(
            ThunderchargeDynamoBlockEntity::new, ModBlocks.THUNDERCHARGE_DYNAMO_BLOCK).build();

    private static <T extends BlockEntity> void registerBlockEntity(String name, BlockEntityType<T> blockEntityType) {
        Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(TurtleChargingStationMod.MOD_ID, name), blockEntityType);
    }

    public static void register() {
        registerBlockEntity("turtle_charging_station", TURTLE_CHARGING_STATION);
        EnergyStorage.SIDED.registerForBlockEntity((blockEntity, direction) -> blockEntity.getEnergyStorage(), TURTLE_CHARGING_STATION);
        registerBlockEntity("thundercharge_dynamo", THUNDERCHARGE_DYNAMO);
    }

}
