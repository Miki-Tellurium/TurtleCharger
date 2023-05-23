package com.mikitellurium.turtlecharginstation.blockentity.custom;

import com.mikitellurium.turtlecharginstation.block.custom.TurtleChargingStationBlock;
import com.mikitellurium.turtlecharginstation.blockentity.ModBlockEntities;
import com.mikitellurium.turtlecharginstation.energy.ModEnergyStorage;
import com.mikitellurium.turtlecharginstation.gui.TurtleChargingStationMenu;
import com.mikitellurium.turtlecharginstation.networking.ModMessages;
import com.mikitellurium.turtlecharginstation.networking.packets.EnergySyncS2CPacket;
import com.mikitellurium.turtlecharginstation.networking.packets.TurtleFuelSyncS2CPacket;
import dan200.computercraft.shared.ModRegistry;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeaconBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TurtleChargingStationBlockEntity extends BlockEntity implements MenuProvider {

    public static ForgeConfigSpec.IntValue CAPACITY;
    public static ForgeConfigSpec.IntValue CONVERSION_RATE; // Based on Thermal Expansion stirling dynamo production rate using coal
    private final int maxReceive = CONVERSION_RATE.get() * 6; // 6 sides
    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(CAPACITY.get(), maxReceive) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };
    private final LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);

    public TurtleChargingStationBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TURTLE_CHARGING_STATION.get(), pPos, pBlockState);
    }

    // Energy stuff
    private int extractCount = 6; // Track if a turtle was charged this tick
    private int textureTimer = 10;

    public static void tick(Level level, BlockPos pos, BlockState state, TurtleChargingStationBlockEntity chargingStation) {
        if (level.isClientSide) {
            return;
        }

        // Check every direction for turtles
        for (Direction direction : Direction.values()) {
            BlockEntity be = level.getBlockEntity(chargingStation.worldPosition.relative(direction));
            // If no block entity is found return
            if (be == null) {
                chargingStation.extractCount--;
                continue;
            }
            // Check if block entity is a turtle
            if (be.getBlockState().getBlock() == ModRegistry.Blocks.TURTLE_NORMAL.get() ||
                    be.getBlockState().getBlock() == ModRegistry.Blocks.TURTLE_ADVANCED.get()) {

                // If enough energy and no redstone signal
                if (chargingStation.ENERGY_STORAGE.getEnergyStored() >= CONVERSION_RATE.get() &&
                        chargingStation.getBlockState().getValue(TurtleChargingStationBlock.ENABLED)) {
                    TurtleBlockEntity turtle = (TurtleBlockEntity) be;
                    if (turtle.getAccess().getFuelLevel() == turtle.getAccess().getFuelLimit()) {
                        chargingStation.extractCount--;
                    } else {
                        level.setBlock(pos, state.setValue(TurtleChargingStationBlock.CHARGING, true), 2);
                        refuelTurtle(chargingStation, turtle);
                        chargingStation.extractCount++;
                        // Sync with client for gui
                        ModMessages.sendToClients(new TurtleFuelSyncS2CPacket(turtle.getAccess().getFuelLevel(), turtle.getBlockPos()));
                    }
                } else {
                    chargingStation.extractCount--;
                }

            } else {
                chargingStation.extractCount--;
            }
            // End of direction for-loop
        }

        if (chargingStation.extractCount <= 0) {
            if (--chargingStation.textureTimer <= 0) {
                level.setBlock(pos, state.setValue(TurtleChargingStationBlock.CHARGING, false), 2);
                chargingStation.textureTimer = 0;
            }
        } else {
            chargingStation.textureTimer = 10;
        }
        chargingStation.extractCount = 6;

        //debugRecharge(level, pos, state, chargingStation);
    }

    private static void refuelTurtle(TurtleChargingStationBlockEntity chargingStation , TurtleBlockEntity turtle) {
       turtle.getAccess().addFuel(1);
       chargingStation.ENERGY_STORAGE.extractEnergy(CONVERSION_RATE.get(), false);
    }

    public EnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setClientEnergy(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }
    // Used for debug purposes
//    private static void debugRecharge(Level level, BlockPos pos, BlockState state, TurtleChargingStationBlockEntity chargingStation) {
//        BlockEntity blockEntity = level.getBlockEntity(pos.above());
//        if (blockEntity instanceof BeaconBlockEntity) {
//            chargingStation.setClientEnergy(Math.min(chargingStation.ENERGY_STORAGE.getMaxEnergyStored(),
//                    chargingStation.ENERGY_STORAGE.getEnergyStored() + 1200));
//        }
//    }

    // Gui
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new TurtleChargingStationMenu(id, inventory, this);
    }

    @Override
    public Component getDisplayName() {
        return Component.translatable("block.turtlechargingstation.turtle_charging_station");
    }

    // Capabilities
    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ENERGY) {
            return lazyEnergyHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyEnergyHandler.invalidate();
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        super.load(nbt);
        ENERGY_STORAGE.setEnergy(nbt.getInt("turtle_charger.energy"));
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putInt("turtle_charger.energy", ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(nbt);
    }

}
