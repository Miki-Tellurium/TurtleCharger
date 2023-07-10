package com.mikitellurium.turtlechargingstation.config;

import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.blockentity.ThunderchargeDynamoBlockEntity;
import com.mikitellurium.turtlechargingstation.blockentity.TurtleChargingStationBlockEntity;
import com.mikitellurium.turtlechargingstation.config.api.TelluriumConfig;

public class Configuration {

private static final TelluriumConfig.Builder COMMON_CONFIG_BUILDER = new TelluriumConfig.Builder(TurtleChargingStationMod.MOD_ID);

    public static void register() {
        setupConfig(COMMON_CONFIG_BUILDER);
    }

    public static void setupConfig(TelluriumConfig.Builder CONFIG_BUILDER) {
        CONFIG_BUILDER.comment("Turtle Charging Station Configuration");

        TurtleChargingStationBlockEntity.CAPACITY = CONFIG_BUILDER
                .defineInRange("chargingStationMaxCapacity", 0, (long)Integer.MAX_VALUE, 128000)
                .comment("The maximum amount of FE the charging station can hold");
        TurtleChargingStationBlockEntity.CONVERSION_RATE = CONFIG_BUILDER
                .defineInRange("chargingStationConversionRate", 0, (long)Integer.MAX_VALUE, 300)
                .comment("The amount of FE required to increase the turtle fuel level by 1");
        ThunderchargeDynamoBlockEntity.RECHARGE_AMOUNT = CONFIG_BUILDER
                .defineInRange("dynamoRechargeAmount", 0, (long)Integer.MAX_VALUE, 2400)
                .comment("The amount of time (in ticks) that is added to the thundercharge dynamo charge.\n" +
                         "# 1 minute = 1200 in-game ticks");
        ThunderchargeDynamoBlockEntity.TRANSFER_RATE = CONFIG_BUILDER
                .defineInRange("dynamoTransferRate", 0, (long)Integer.MAX_VALUE, 900)
                .comment("The amount of FE/tick generated by the thundercharge dynamo");

        CONFIG_BUILDER.build();
    }

}
