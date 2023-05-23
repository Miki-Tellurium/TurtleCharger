package com.mikitellurium.turtlecharginstation.blockentity.custom;

import com.mikitellurium.turtlecharginstation.blockentity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import org.jetbrains.annotations.NotNull;

public class ThunderchargeDynamoBlockEntity extends BlockEntity {

    private int charge = 0;
    public static ForgeConfigSpec.IntValue TRANSFER_RATE;
    public static ForgeConfigSpec.IntValue RECHARGE_AMOUNT;

    public ThunderchargeDynamoBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.THUNDERCHARGE_DYNAMO.get(), blockPos, blockState);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ThunderchargeDynamoBlockEntity dynamo) {
        if (level.isClientSide) {
            return;
        }

        System.out.println(dynamo.charge);
        // Check every direction for turtles
        for (Direction direction : Direction.values()) {
            if (direction == Direction.UP) {
                continue;
            }
            BlockEntity be = level.getBlockEntity(dynamo.worldPosition.relative(direction));
            if (be == null) {
                continue;
            }
            // Check for energy capability
            if (be.getCapability(ForgeCapabilities.ENERGY).isPresent()) {
                be.getCapability(ForgeCapabilities.ENERGY).ifPresent((energyStorage ->
                        energyStorage.receiveEnergy(TRANSFER_RATE.get(), false)));
            }
        }

        if (dynamo.charge > 0) {
            dynamo.charge--;
        }
    }

    public int getCharge() {
        return charge;
    }

    public static void addCharge(ThunderchargeDynamoBlockEntity dynamo) {
        dynamo.charge = Math.min(dynamo.getCharge() + RECHARGE_AMOUNT.get(), Integer.MAX_VALUE);
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
