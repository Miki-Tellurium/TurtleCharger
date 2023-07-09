package com.mikitellurium.turtlecharginstation.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraftforge.fml.util.ObfuscationReflectionHelper;

import java.lang.reflect.Field;

public class LevelUtils {

    private static final Field dataIsPowered = ObfuscationReflectionHelper.findField(Creeper.class, "f_32274_");

    public static void maybeDoSpawnCreeper(ServerLevel level, BlockPos pos) {
        if (level.random.nextInt(4095) == 0) {
            Direction direction = Direction.getRandom(level.random);
            BlockPos blockPos = getPossibleSpawnPos(level, pos.relative(direction));
            if (blockPos != null) {
                Creeper creeper = EntityType.CREEPER.spawn(level, blockPos, MobSpawnType.EVENT);
                if (creeper != null) {
                    try {
                        EntityDataAccessor<Boolean> creeperIsPowered = (EntityDataAccessor<Boolean>) dataIsPowered.get(creeper);
                        creeper.getEntityData().set(creeperIsPowered, true);
                    } catch (IllegalAccessException e) {
                        // Handle exceptions
                    }
                }
            }
        }
    }

    private static BlockPos getPossibleSpawnPos(Level level, BlockPos pos) {
        BlockPos.MutableBlockPos mutable$pos = new BlockPos.MutableBlockPos(pos.getX(), pos.getY() + 2, pos.getZ());

        for (int i = 0; i < 10 ; i++) {
            System.out.println(level.getBlockState(mutable$pos));
            if (NaturalSpawner.canSpawnAtBody(SpawnPlacements.Type.NO_RESTRICTIONS, level, mutable$pos, EntityType.CREEPER) && level.canSeeSky(mutable$pos)) {
                return mutable$pos;
            }
            mutable$pos.move(Direction.DOWN);
        }

        return null;
    }

}
