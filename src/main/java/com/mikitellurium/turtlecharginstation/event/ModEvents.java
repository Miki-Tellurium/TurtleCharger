package com.mikitellurium.turtlecharginstation.event;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.blockentity.ThunderchargeDynamoBlockEntity;
import com.mikitellurium.turtlecharginstation.util.LevelUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
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
            BlockEntity maybeDynamo = getBlockEntityBelowStrike(lightningBolt.level(), lightningBolt.getOnPos());
            if (maybeDynamo == null) return;
            if (maybeDynamo instanceof ThunderchargeDynamoBlockEntity dynamo) {
                ThunderchargeDynamoBlockEntity.recharge(dynamo);
                //if (lightningBolt.getCause() == null) {
                    LevelUtils.maybeDoSpawnCreeper((ServerLevel) lightningBolt.level(), dynamo.getBlockPos());
                //}
            }
        }
    }

    private static BlockEntity getBlockEntityBelowStrike(Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        return blockEntity != null ? blockEntity : level.getBlockEntity(blockPos.below());
    }

}
