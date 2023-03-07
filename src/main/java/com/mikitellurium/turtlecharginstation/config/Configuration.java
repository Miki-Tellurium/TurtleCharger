package com.mikitellurium.turtlecharginstation.config;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.block.blockentity.custom.TurtleChargingStationBlockEntity;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;

public class Configuration {

    public static void registerClientConfig() {
        ForgeConfigSpec.Builder CLIENT_CONFIG_BUILDER = new ForgeConfigSpec.Builder();
        setupConfig(CLIENT_CONFIG_BUILDER);
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_CONFIG_BUILDER.build());
    }

    public static void setupConfig(ForgeConfigSpec.Builder CLIENT_CONFIG_BUILDER) {
        CLIENT_CONFIG_BUILDER.comment("Turtle Charging Station Configuration").push(TurtleChargingStationMod.MOD_ID);

        TurtleChargingStationBlockEntity.CAPACITY = CLIENT_CONFIG_BUILDER
                .comment("The maximum amount of FE the charging station can hold")
                .defineInRange("maxCapacity", 128000, 0, Integer.MAX_VALUE);
        TurtleChargingStationBlockEntity.CONVERSION_RATE = CLIENT_CONFIG_BUILDER
                .comment("The amount of FE required to increase the turtle fuel level by 1")
                .defineInRange("conversionRate", 300, 0, Integer.MAX_VALUE);

        CLIENT_CONFIG_BUILDER.pop();
    }

}
