package com.mikitellurium.turtlecharginstation.event;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.blockentity.custom.ThunderchargeDynamoBlockEntity;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = TurtleChargingStationMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onLightningStrike(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide) {
            return;
        }
        if (event.getEntity() instanceof LightningBolt lightningBolt) {
            BlockEntity be = event.getLevel().getBlockEntity(lightningBolt.getOnPos().below());
            if (be == null) return;
            if (be instanceof ThunderchargeDynamoBlockEntity dynamo) {
                ThunderchargeDynamoBlockEntity.recharge(dynamo);
            }
        }
    }


}
