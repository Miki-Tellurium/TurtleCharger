package com.mikitellurium.turtlechargingstation.event;

import com.mikitellurium.turtlechargingstation.blockentity.ThunderchargeDynamoBlockEntity;
import com.mikitellurium.turtlechargingstation.util.WorldUtils;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModEvents {

    public static void register() {
        ServerEntityEvents.ENTITY_LOAD.register(ModEvents::onLightningStrike);
    }

    private static void onLightningStrike(Entity entity, ServerWorld world) {
        if (entity instanceof LightningEntity lightningBolt) {
            BlockEntity blockEntity = getBlockEntityBelow(world, lightningBolt.getSteppingPos());
            if (blockEntity == null) return;
            if (blockEntity instanceof ThunderchargeDynamoBlockEntity dynamo) {
                ThunderchargeDynamoBlockEntity.recharge(dynamo);
                if (lightningBolt.getChanneler() == null) {
                    WorldUtils.maybeDoSpawnCreeper((ServerWorld) lightningBolt.method_48926(), dynamo.getPos());
                }
            }
        }
    }

    private static BlockEntity getBlockEntityBelow(World world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        return blockEntity != null ? blockEntity : world.getBlockEntity(pos.down());
    }

}
