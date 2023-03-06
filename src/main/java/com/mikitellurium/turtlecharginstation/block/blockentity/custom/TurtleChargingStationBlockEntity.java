package com.mikitellurium.turtlecharginstation.block.blockentity.custom;

import com.mikitellurium.turtlecharginstation.block.blockentity.ModBlockEntities;
import com.mikitellurium.turtlecharginstation.block.custom.TurtleChargingStationBlock;
import com.mikitellurium.turtlecharginstation.energy.ModEnergyStorage;
import com.mikitellurium.turtlecharginstation.gui.TurtleChargingStationMenu;
import com.mikitellurium.turtlecharginstation.networking.ModMessages;
import com.mikitellurium.turtlecharginstation.networking.packets.EnergySyncS2CPacket;
import com.mikitellurium.turtlecharginstation.networking.packets.TurtleFuelSyncS2CPacket;
import com.mikitellurium.turtlecharginstation.util.DebugUtil;
import dan200.computercraft.shared.Registry;
import dan200.computercraft.shared.turtle.blocks.TileTurtle;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TurtleChargingStationBlockEntity extends BlockEntity implements MenuProvider {

    private final int capacity = 128000;
    private static final int conversionRate = 300; // Based on Thermal Expansion stirling dynamo production rate using coal
    private final int maxReceive = conversionRate * 6; //  6 sides
    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(capacity, maxReceive) {
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
    public static void tick(Level level, BlockPos pos, BlockState state, TurtleChargingStationBlockEntity charger) {
        if (level.isClientSide) {
            return;
        }
        // If turtle is present then recharge it;
        for (Direction direction : Direction.values()) {
            BlockEntity be = level.getBlockEntity(charger.worldPosition.relative(direction));
            if (be == null) {
                continue;
            }

            if (be.getBlockState().getBlock() == Registry.ModBlocks.TURTLE_NORMAL.get() ||
                    be.getBlockState().getBlock() == Registry.ModBlocks.TURTLE_ADVANCED.get()) {
                if (charger.ENERGY_STORAGE.getEnergyStored() >= conversionRate &&
                        charger.getBlockState().getValue(TurtleChargingStationBlock.ENABLED)) {
                    TileTurtle turtle = (TileTurtle) be;
                    level.setBlock(pos, state.setValue(TurtleChargingStationBlock.CHARGING, true), 2);
                    refuelTurtle(charger, turtle);
                    // Sync with client for gui
                    ModMessages.sendToClients(new TurtleFuelSyncS2CPacket(turtle.getAccess().getFuelLevel(), turtle.getBlockPos()));
                } else {
                    level.setBlock(pos, state.setValue(TurtleChargingStationBlock.CHARGING, false), 2);
                }
            } else {
                level.setBlock(pos, state.setValue(TurtleChargingStationBlock.CHARGING, false), 2);
            }
        }
        DebugUtil.info(state.getValue(TurtleChargingStationBlock.CHARGING));
    }

    private static void refuelTurtle(TurtleChargingStationBlockEntity charger , TileTurtle turtle) {
       turtle.getAccess().addFuel(1);
       charger.ENERGY_STORAGE.extractEnergy(conversionRate, false);
    }

    public EnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setClientEnergy(int energy) {
        this.ENERGY_STORAGE.setEnergy(energy);
    }

    // Gui
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new TurtleChargingStationMenu(id, inventory, this);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Turtle Charging Station");
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
