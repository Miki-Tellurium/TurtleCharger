package com.mikitellurium.turtlecharginstation.event;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.blockentity.ThunderchargeDynamoBlockEntity;
import com.mikitellurium.turtlecharginstation.registry.ModTags;
import com.mikitellurium.turtlecharginstation.util.LevelUtils;
import com.mikitellurium.turtlecharginstation.util.ModIdConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = TurtleChargingStationMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEvents {

    @SubscribeEvent
    public static void onLightningStrike(EntityJoinLevelEvent event) {
        if (event.getLevel().isClientSide) {
            return;
        }
        Entity entity = event.getEntity();
        if (entity.getType().is(ModTags.DYNAMO_ACTIVATORS)) {
            BlockEntity maybeDynamo = getBlockEntityBelowStrike(entity.level(), entity.getOnPos());
            if (maybeDynamo == null) return;
            if (maybeDynamo instanceof ThunderchargeDynamoBlockEntity dynamo) {
                dynamo.recharge();
                if (entity instanceof LightningBolt lightningBolt && lightningBolt.getCause() == null) {
                    LevelUtils.maybeDoSpawnCreeper((ServerLevel) entity.level(), dynamo.getBlockPos());
                }
            }
        }
    }

    private static BlockEntity getBlockEntityBelowStrike(Level level, BlockPos blockPos) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        return blockEntity != null ? blockEntity : level.getBlockEntity(blockPos.below());
    }

}
