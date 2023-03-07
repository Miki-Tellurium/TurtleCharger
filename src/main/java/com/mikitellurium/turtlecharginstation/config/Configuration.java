package com.mikitellurium.turtlecharginstation.config;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.block.blockentity.custom.TurtleChargingStationBlockEntity;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Configuration {

    public static void registerConfig() {
        ForgeConfigSpec.Builder CONFIG_BUILDER = new ForgeConfigSpec.Builder();
        setupConfig(CONFIG_BUILDER);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, CONFIG_BUILDER.build());
    }

    public static void setupConfig(ForgeConfigSpec.Builder CONFIG_BUILDER) {
        CONFIG_BUILDER.comment("Turtle Charging Station Configuration").push(TurtleChargingStationMod.MOD_ID);

        TurtleChargingStationBlockEntity.CAPACITY = CONFIG_BUILDER
                .comment("The maximum amount of FE the charging station can hold")
                .defineInRange("maxCapacity", 128000, 0, Integer.MAX_VALUE);
        TurtleChargingStationBlockEntity.CONVERSION_RATE = CONFIG_BUILDER
                .comment("The amount of FE required to increase the turtle fuel level by 1")
                .defineInRange("conversionRate", 300, 0, Integer.MAX_VALUE);

        CONFIG_BUILDER.pop();
    }

}
