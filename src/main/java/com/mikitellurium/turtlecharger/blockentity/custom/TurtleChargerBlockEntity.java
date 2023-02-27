package com.mikitellurium.turtlecharger.blockentity.custom;

import com.mikitellurium.turtlecharger.block.custom.TurtleChargerBlock;
import com.mikitellurium.turtlecharger.blockentity.ModBlockEntities;
import com.mikitellurium.turtlecharger.energy.ModEnergyStorage;
import com.mikitellurium.turtlecharger.gui.TurtleChargerMenu;
import com.mikitellurium.turtlecharger.networking.ModMessages;
import com.mikitellurium.turtlecharger.networking.packets.EnergySyncS2CPacket;
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

public class TurtleChargerBlockEntity extends BlockEntity implements MenuProvider {

    private final int capacity = 128000;
    private final int maxReceive = 1024;
    private static final int conversionRate = 300; // Based on Thermal Expansion stirling dynamo coal rate
    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(capacity, maxReceive) {
        @Override
        public void onEnergyChanged() {
            setChanged();
            ModMessages.sendToClients(new EnergySyncS2CPacket(this.energy, getBlockPos()));
        }
    };
    private final LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);

    public TurtleChargerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TURTLE_CHARGER.get(), pPos, pBlockState);
    }

    // Energy stuff
    public static void tick(Level level, BlockPos pos, BlockState state, TurtleChargerBlockEntity charger) {
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
                        charger.getBlockState().getValue(TurtleChargerBlock.ENABLED)) {
                    refuelTurtle(charger, (TileTurtle) be);
                }
            }
        }

    }

    private static void refuelTurtle(TurtleChargerBlockEntity charger ,TileTurtle turtle) {
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
        return new TurtleChargerMenu(id, inventory, this);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Turtle Charger");
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
