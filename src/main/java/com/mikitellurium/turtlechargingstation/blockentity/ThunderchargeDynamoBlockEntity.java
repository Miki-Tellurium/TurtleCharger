package com.mikitellurium.turtlechargingstation.blockentity;

import com.mikitellurium.telluriumforge.config.RangedConfigEntry;
import com.mikitellurium.turtlechargingstation.block.ThunderchargeDynamoBlock;
import com.mikitellurium.turtlechargingstation.registry.ModBlockEntities;
import com.mikitellurium.turtlechargingstation.registry.ModTags;
import com.mikitellurium.turtlechargingstation.util.ConductiveBlockContext;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import team.reborn.energy.api.EnergyStorage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ThunderchargeDynamoBlockEntity extends BlockEntity {

    private int charge = 0;
    public static RangedConfigEntry<Long> TRANSFER_RATE;
    public static RangedConfigEntry<Long> RECHARGE_AMOUNT;

    public ThunderchargeDynamoBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.THUNDERCHARGE_DYNAMO, blockPos, blockState);
    }

    public void tick(World world, BlockPos blockPos, BlockState blockState) {
        if (world.isClient) {
            return;
        }

        if (this.charge > 0) {

            Set<BlockEntity> chargedBlockEntities = new HashSet<>(); // Avoid block entities being charged multiple times per tick
            for (Direction direction : Direction.values()) {
                if (direction == Direction.UP) {
                    continue;
                }
                Map<BlockEntity, Direction> blockEntities = this.findConnectedBlockEntities(world, blockPos, direction, new HashSet<>());
                if (blockEntities.isEmpty()) {
                    continue;
                }
                blockEntities.entrySet().stream()
                        .filter((entry) -> !chargedBlockEntities.contains(entry.getKey()))
                        .forEach((entry) -> {
                            BlockEntity blockEntity = entry.getKey();
                            EnergyStorage energyStorage = EnergyStorage.SIDED.find(world, blockEntity.getPos(),
                                    blockEntity.getCachedState(), blockEntity, entry.getValue());
                            if (energyStorage != null) {
                                try (Transaction transaction = Transaction.openOuter()) {
                                    long amountInserted = energyStorage.insert(TRANSFER_RATE.getValue(), transaction);
                                    if (amountInserted == TRANSFER_RATE.getValue()) {
                                        transaction.commit();
                                        chargedBlockEntities.add(blockEntity);
                                    }
                                }
                            }
                        });
            }

            this.charge--;
        }

        world.setBlockState(blockPos, blockState.with(ThunderchargeDynamoBlock.POWERED, this.charge > 0));
        markDirty(world, blockPos, blockState);
    }

    /*
     * checkedPosSet: tracks which BlockPos are already checked to avoid loops.
     * If a BlockEntity is found add it to blockEntities, else if a conductive block is found
     * check adjacent blocks using recursion.
     */
    private Map<BlockEntity, Direction> findConnectedBlockEntities(World world, BlockPos startPos, Direction startDirection,
                                                        Set<BlockPos> checkedPosSet) {
        Map<BlockEntity, Direction> blockEntities = new HashMap<>();
        BlockPos relative = startPos.offset(startDirection);
        if (checkedPosSet.contains(relative)) return blockEntities; // Don't check the same BlockPos more than once

        BlockState blockState = world.getBlockState(relative);
        ConductiveBlockContext context = new ConductiveBlockContext(blockState);
        // Check if the previous block is oriented towards the current position
        if (!context.canConductTo(world.getBlockState(startPos), startDirection)) return blockEntities;

        BlockEntity blockEntity = world.getBlockEntity(relative);
        if (blockEntity != null) {
            blockEntities.put(blockEntity, startDirection);
        } else if (blockState.isIn(ModTags.DYNAMO_CONDUCTIVE_BLOCKS)) {
            checkedPosSet.add(relative);
            for (Direction direction : context.getDirections()) {
                if (direction == startDirection.getOpposite()) continue; // Don't check the direction we're coming from
                blockEntities.putAll(findConnectedBlockEntities(world, relative, direction, checkedPosSet));
            }
        }
        return blockEntities;
    }

    public int getCharge() {
        return charge;
    }

    public void recharge() {
        this.charge = (int)Math.min(this.getCharge() + RECHARGE_AMOUNT.getValue(), Integer.MAX_VALUE);
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
