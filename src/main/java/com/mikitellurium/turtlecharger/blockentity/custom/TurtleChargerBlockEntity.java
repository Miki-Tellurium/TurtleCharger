package com.mikitellurium.turtlecharger.blockentity.custom;

import com.mikitellurium.turtlecharger.blockentity.ModBlockEntities;
import com.mikitellurium.turtlecharger.energy.ModEnergyStorage;
import com.mikitellurium.turtlecharger.gui.TurtleChargerMenu;
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
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TurtleChargerBlockEntity extends BlockEntity implements MenuProvider {

    public TurtleChargerBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.TURTLE_CHARGER.get(), pPos, pBlockState);
    }

    // Energy stuff
    private final int capacity = 100000;
    private final int maxReceive = 256;
    private static final int conversionRate = 128;

    private LazyOptional<IEnergyStorage> lazyEnergyHandler = LazyOptional.empty();

    private final ModEnergyStorage ENERGY_STORAGE = new ModEnergyStorage(capacity, maxReceive) {
        @Override
        public void onEnergyChanged() {
            setChanged();
        }
    };

    @Override
    public Component getDisplayName() {
        return Component.literal("Turtle Charger");
    }

    public static void tick(Level level, BlockPos pos, BlockState state, TurtleChargerBlockEntity entity) {
        if (level.isClientSide) {
            return;
        }

        // If turtle is present then recharge it

    }

    // Gui
    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new TurtleChargerMenu(id, inventory, this);
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
    public void onLoad() {
        super.onLoad();
        lazyEnergyHandler = LazyOptional.of(() -> ENERGY_STORAGE);
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        ENERGY_STORAGE.setEnergy(nbt.getInt("energy"));
    }

    @Override
    protected void saveAdditional(CompoundTag nbt) {
        nbt.putInt("energy", ENERGY_STORAGE.getEnergyStored());
        super.saveAdditional(nbt);
    }



}
