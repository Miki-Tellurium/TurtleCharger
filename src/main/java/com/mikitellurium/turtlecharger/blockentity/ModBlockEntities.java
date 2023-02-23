package com.mikitellurium.turtlecharger.blockentity;

import com.mikitellurium.turtlecharger.TurtleChargerMod;
import com.mikitellurium.turtlecharger.block.ModBlocks;
import com.mikitellurium.turtlecharger.blockentity.custom.TurtleChargerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, TurtleChargerMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<TurtleChargerBlockEntity>> TURTLE_CHARGER =
            BLOCK_ENTITIES.register("turtle_charger", () -> BlockEntityType.Builder.of(TurtleChargerBlockEntity::new,
                    ModBlocks.TURTLE_CHARGER_BLOCK.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

}
