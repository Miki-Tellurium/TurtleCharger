package com.mikitellurium.turtlecharginstation.blockentity;

import com.mikitellurium.turtlecharginstation.block.ThunderchargeDynamoBlock;
import com.mikitellurium.turtlecharginstation.registry.ModBlockEntities;
import com.mikitellurium.turtlecharginstation.registry.ModTags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.energy.EnergyStorage;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class ThunderchargeDynamoBlockEntity extends BlockEntity {

    private int charge = 0;
    public static ModConfigSpec.IntValue TRANSFER_RATE;
    public static ModConfigSpec.IntValue RECHARGE_AMOUNT;

    public ThunderchargeDynamoBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.THUNDERCHARGE_DYNAMO.get(), blockPos, blockState);
    }

    public void tick(Level level, BlockPos blockPos, BlockState blockState) {
        if (level.isClientSide) {
            return;
        }

        if (this.charge > 0) {
            for (Direction direction : Direction.values()) {
                if (direction == Direction.UP) {
                    continue;
                }
                Set<BlockEntity> blockEntities = this.findConnectedBlockEntities(level, blockPos, direction);
                if (blockEntities.isEmpty()) {
                    continue;
                }
                blockEntities.forEach((blockEntity -> {
                    IEnergyStorage energy = level.getCapability(Capabilities.EnergyStorage.BLOCK, blockEntity.getBlockPos(),
                            blockEntity.getBlockState(), blockEntity, direction);
                    if (energy != null) {
                        energy.receiveEnergy(TRANSFER_RATE.get(), false);
                    }
                }));
            }

            this.charge--;
        }

        level.setBlockAndUpdate(blockPos, blockState.setValue(ThunderchargeDynamoBlock.POWERED, this.charge > 0));
        setChanged(level, blockPos, blockState);
    }

    private Set<BlockEntity> findConnectedBlockEntities(Level level, BlockPos pos, Direction direction) {
        BlockPos relative = pos.relative(direction);
        Set<BlockEntity> blockEntities = new HashSet<>();
        for (Direction dir : Direction.values()) {
            if (dir == direction.getOpposite()) continue; // Don't check the direction we're coming from

            BlockEntity blockEntity = level.getBlockEntity(relative);
            if (blockEntity != null) {
                blockEntities.add(blockEntity);
            } else if (level.getBlockState(relative).is(ModTags.DYNAMO_CONDUCTIVE_BLOCKS)) {
                blockEntities.addAll(findConnectedBlockEntities(level, relative, dir));
            }
        }
        return blockEntities;
    }

    public int getCharge() {
        return this.charge;
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

    public static void registerCapability(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.THUNDERCHARGE_DYNAMO.get(),
                (blockEntity, direction) -> {
                    if (direction != Direction.UP) {
                        return new EnergyStorage(0);
                    }
                    return null;
                });
    }

}
