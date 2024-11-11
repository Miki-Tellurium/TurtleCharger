package com.mikitellurium.turtlecharginstation.blockentity;

import com.mikitellurium.turtlecharginstation.block.ThunderchargeDynamoBlock;
import com.mikitellurium.turtlecharginstation.registry.ModBlockEntities;
import com.mikitellurium.turtlecharginstation.registry.ModTags;
import com.mikitellurium.turtlecharginstation.util.ModEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ThunderchargeDynamoBlockEntity extends BlockEntity {

    private int charge = 0;
    public static ForgeConfigSpec.IntValue TRANSFER_RATE;
    public static ForgeConfigSpec.IntValue RECHARGE_AMOUNT;

    public ThunderchargeDynamoBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.THUNDERCHARGE_DYNAMO.get(), blockPos, blockState);
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, ThunderchargeDynamoBlockEntity dynamo) {
        if (level.isClientSide) {
            return;
        }

        if (dynamo.charge > 0) {
            // Check every direction for turtles
            for (Direction direction : Direction.values()) {
                if (direction == Direction.UP) {
                    continue;
                }
                BlockEntity be = maybeFindAdjacentBlockEntity(level, blockPos, direction);
                if (be == null) {
                    continue;
                }
                // Check for energy capability
                if (be.getCapability(ForgeCapabilities.ENERGY).isPresent()) {
                    be.getCapability(ForgeCapabilities.ENERGY).ifPresent((energyStorage ->
                            energyStorage.receiveEnergy(TRANSFER_RATE.get(), false)));
                }
            }

            level.setBlockAndUpdate(blockPos, blockState.setValue(ThunderchargeDynamoBlock.POWERED, true));
            dynamo.charge--;
        } else {
            level.setBlockAndUpdate(blockPos, blockState.setValue(ThunderchargeDynamoBlock.POWERED, false));
        }

        setChanged(level, blockPos, blockState);
    }

    private static BlockEntity maybeFindAdjacentBlockEntity(Level level, BlockPos pos, Direction direction) {
        BlockPos.MutableBlockPos mutable$pos = pos.relative(direction).mutable();
        while (true) {
            if (level.getBlockEntity(mutable$pos) != null) {
                return level.getBlockEntity(mutable$pos);
            } else if (level.getBlockState(mutable$pos).is(ModTags.DYNAMO_CONDUCTIVE_BLOCKS)) {
                mutable$pos.move(direction);
            } else {
                return null;
            }
        }
    }

    public int getCharge() {
        return charge;
    }

    public void recharge() {
        this.charge = Math.min(this.getCharge() + RECHARGE_AMOUNT.get(), Integer.MAX_VALUE);
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        charge = nbt.getInt("charge");
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putInt("charge", charge);
        super.saveAdditional(nbt);
    }

}
