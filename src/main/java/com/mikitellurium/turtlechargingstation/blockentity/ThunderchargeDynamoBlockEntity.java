package com.mikitellurium.turtlechargingstation.blockentity;

import com.mikitellurium.turtlechargingstation.block.ThunderchargeDynamoBlock;
import com.mikitellurium.turtlechargingstation.registry.ModBlockEntities;
import com.mikitellurium.turtlechargingstation.config.api.TelluriumConfig;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import team.reborn.energy.api.EnergyStorage;

public class ThunderchargeDynamoBlockEntity extends BlockEntity {

    private int charge = 0;
    public static TelluriumConfig.RangedConfigEntry<Long> TRANSFER_RATE;
    public static TelluriumConfig.RangedConfigEntry<Long> RECHARGE_AMOUNT;

    public ThunderchargeDynamoBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.THUNDERCHARGE_DYNAMO, blockPos, blockState);
    }

    public static void tick(World world, BlockPos blockPos, BlockState blockState, ThunderchargeDynamoBlockEntity dynamo) {
        if (world.isClient) {
            return;
        }

        if (dynamo.charge > 0) {
            // Check every direction for turtles
            for (Direction direction : Direction.values()) {
                if (direction == Direction.UP) {
                    continue;
                }
                BlockEntity blockEntity = world.getBlockEntity(dynamo.pos.offset(direction));
                if (blockEntity == null) {
                    continue;
                }
                // Check for energy capability
                EnergyStorage energyStorage = EnergyStorage.SIDED.find(world, blockPos.offset(direction), direction.getOpposite());
                if (energyStorage != null) {
                    System.out.println("Found energy storage");
                    try (Transaction transaction = Transaction.openOuter()) {
                        long amountInserted = energyStorage.insert(TRANSFER_RATE.getValue(), transaction);
                        if (amountInserted == TRANSFER_RATE.getValue()) {
                            transaction.commit();
                        } else {
                            // Abort transaction
                        }
                    }
                } else {
                    System.out.println("No energy storage connected");
                }
            }

            world.setBlockState(blockPos, blockState.with(ThunderchargeDynamoBlock.POWERED, true), 3);
            dynamo.charge--;
        } else {
            world.setBlockState(blockPos, blockState.with(ThunderchargeDynamoBlock.POWERED, false), 3);
        }

        markDirty(world, blockPos, blockState);
    }

    public int getCharge() {
        return charge;
    }

    public static void recharge(ThunderchargeDynamoBlockEntity dynamo) {
        dynamo.charge = (int)Math.min(dynamo.getCharge() + RECHARGE_AMOUNT.getValue(), Integer.MAX_VALUE);
    }


    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        charge = nbt.getInt("dynamoCharge");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putInt("dynamoCharge", charge);
        super.writeNbt(nbt);
    }

}
