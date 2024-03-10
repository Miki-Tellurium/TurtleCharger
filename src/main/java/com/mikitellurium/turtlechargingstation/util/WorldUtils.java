package com.mikitellurium.turtlechargingstation.util;

import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.mixin.CreeperEntityAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.World;

public class WorldUtils {

    public static void maybeDoSpawnCreeper(ServerWorld world, BlockPos pos) {
        if (world.random.nextInt(1023) == 0) {
            Direction direction = Direction.random(world.random);
            BlockPos blockPos = getPossibleSpawnPos(world, pos.offset(direction));
            if (blockPos != null) {
                CreeperEntity creeper = EntityType.CREEPER.spawn(world, blockPos, SpawnReason.EVENT);
                if (creeper != null) {
                    chargeCreeper(creeper);
                }
            }
        }
    }

    private static BlockPos getPossibleSpawnPos(World world, BlockPos pos) {
        BlockPos.Mutable mutable$pos = new BlockPos.Mutable(pos.getX(), pos.getY() + 2, pos.getZ());

        for (int i = 0; i < 10 ; i++) {
            if (SpawnHelper.canSpawn(SpawnRestriction.Location.ON_GROUND, world, mutable$pos, EntityType.CREEPER)
                    && world.isSkyVisible(mutable$pos)) {
                return mutable$pos;
            }
            mutable$pos.move(Direction.DOWN);
        }

        return null;
    }

    private static void chargeCreeper(CreeperEntity creeper) {
        TrackedData<Boolean> creeperIsCharged = CreeperEntityAccessor.getCHARGED();
        creeper.getDataTracker().set(creeperIsCharged, true);
    }

}
