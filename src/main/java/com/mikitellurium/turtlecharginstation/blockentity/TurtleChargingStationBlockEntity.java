package com.mikitellurium.turtlecharginstation.blockentity;

import com.mikitellurium.turtlecharginstation.block.TurtleChargingStationBlock;
import com.mikitellurium.turtlecharginstation.registry.ModBlockEntities;
import com.mikitellurium.turtlecharginstation.util.ModEnergyStorage;
import com.mikitellurium.turtlecharginstation.gui.TurtleChargingStationMenu;
import com.mikitellurium.turtlecharginstation.networking.ModMessages;
import com.mikitellurium.turtlecharginstation.networking.packets.EnergySyncS2CPacket;
import com.mikitellurium.turtlecharginstation.networking.packets.TurtleFuelSyncS2CPacket;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import dan200.computercraft.shared.turtle.core.TurtleBrain;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.energy.EnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TurtleChargingStationBlockEntity extends BlockEntity implements MenuProvider {

    public static ModConfigSpec.IntValue CAPACITY;
    public static ModConfigSpec.IntValue CONVERSION_RATE;
    private final int maxReceive = CONVERSION_RATE.get() * 6; // 6 sides
    private final ModEnergyStorage energyStorage = new ModEnergyStorage(CAPACITY.get(), maxReceive) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToAll(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };
    private boolean hasChargedTurtle = false; // Track if a turtle was charged this tick
    private int blockStateTimer = 10; // Delay block state update to avoid texture flicker

    public TurtleChargingStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TURTLE_CHARGING_STATION.get(), pPos, pBlockState);
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide) {
            return;
        }

        for (Direction direction : Direction.values()) {

            BlockEntity blockEntity = level.getBlockEntity(this.worldPosition.relative(direction));
            if (blockEntity == null) {
                continue;
            }

            if (blockEntity instanceof TurtleBlockEntity turtle) {
                TurtleBrain turtleBrain = (TurtleBrain) turtle.getAccess();
                if (this.canRechargeTurtle() && turtleBrain.getFuelLevel() < turtleBrain.getFuelLimit()) {
                    this.refuelTurtle(turtle);
                    this.hasChargedTurtle = true;
                    level.setBlockAndUpdate(pos, state.setValue(TurtleChargingStationBlock.CHARGING, true));
                    ModMessages.sendToAll(new TurtleFuelSyncS2CPacket(turtleBrain.getFuelLevel(), turtle.getBlockPos()));
                }
            }

        }

        if (!this.hasChargedTurtle) { // If no turtle was recharged tick the block state timer
            if (--this.blockStateTimer <= 0) {
                level.setBlockAndUpdate(pos, state.setValue(TurtleChargingStationBlock.CHARGING, false));
                this.blockStateTimer = 0;
            }
        } else {
            this.blockStateTimer = 10;
        }
        this.hasChargedTurtle = false;
    }

    private boolean canRechargeTurtle() {
        return this.energyStorage.getEnergyStored() >= CONVERSION_RATE.get() &&
                this.getBlockState().getValue(TurtleChargingStationBlock.ENABLED);
    }

    private void refuelTurtle(TurtleBlockEntity turtle) {
        this.energyStorage.extractEnergy(CONVERSION_RATE.get(), false);
        turtle.getAccess().addFuel(1);
    }

    public EnergyStorage getEnergyStorage() {
        return this.energyStorage;
    }

    // Used for synchronization, do not call directly
    public void setClientEnergy(int energy) {
        this.energyStorage.setEnergy(energy);
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new TurtleChargingStationMenu(id, inventory, this);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.turtlechargingstation.turtle_charging_station");
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        this.energyStorage.deserializeNBT(nbt.get("stored_energy"));
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.put("stored_energy", this.energyStorage.serializeNBT());
        super.saveAdditional(nbt);
    }

    public static void registerCapability(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, ModBlockEntities.TURTLE_CHARGING_STATION.get(),
                (blockEntity, direction) -> blockEntity.energyStorage);
    }

}
