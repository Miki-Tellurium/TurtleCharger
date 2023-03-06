package com.mikitellurium.turtlecharginstation.block.blockentity;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.block.ModBlocks;
import com.mikitellurium.turtlecharginstation.block.blockentity.custom.TurtleChargingStationBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TurtleChargingStationMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<TurtleChargingStationBlockEntity>> TURTLE_CHARGING_STATION =
            BLOCK_ENTITIES.register("turtle_charging_station", () -> BlockEntityType.Builder.of(TurtleChargingStationBlockEntity::new,
                    ModBlocks.TURTLE_CHARGING_STATION_BLOCK.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
