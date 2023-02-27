package com.mikitellurium.turtlecharger.gui;

import com.mikitellurium.turtlecharger.block.ModBlocks;
import com.mikitellurium.turtlecharger.blockentity.custom.TurtleChargerBlockEntity;
import com.mikitellurium.turtlecharger.energy.ModEnergyStorage;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.energy.IEnergyStorage;

public class TurtleChargerMenu extends AbstractContainerMenu {

    private final TurtleChargerBlockEntity blockEntity;
    private final Level level;

    protected TurtleChargerMenu(int id, Inventory inventory, FriendlyByteBuf data) {
        this(id, inventory, inventory.player.level.getBlockEntity(data.readBlockPos()));
    }

    public TurtleChargerMenu(int id, Inventory inventory, BlockEntity entity) {
        super(ModMenuTypes.TURTLE_CHARGER_GUI.get(), id);
        blockEntity = (TurtleChargerBlockEntity) entity;
        this.level = inventory.player.level;
        trackEnergy();
    }

    public BlockState getAdjacentTurtles(Direction direction) {
        // Return adjacent turtles
        return null;
    }
    // Synchronizing server data to client for Gui on world startup
    // Credit to McJty
    private void trackEnergy() {
        // Unfortunatelly on a dedicated server ints are actually truncated to short so we need
        // to split our integer here (split our 32 bit integer into two 16 bit integers)
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return getEnergy() & 0xffff;
            }

            @Override
            public void set(int value) {
                blockEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0xffff0000;
                    ((ModEnergyStorage)h).setEnergy(energyStored + (value & 0xffff));
                });
            }
        });
        addDataSlot(new DataSlot() {
            @Override
            public int get() {
                return (getEnergy() >> 16) & 0xffff;
            }

            @Override
            public void set(int value) {
                blockEntity.getCapability(ForgeCapabilities.ENERGY).ifPresent(h -> {
                    int energyStored = h.getEnergyStored() & 0x0000ffff;
                    ((ModEnergyStorage)h).setEnergy(energyStored | (value << 16));
                });
            }
        });
    }

    public int getEnergy() {
        return blockEntity.getCapability(ForgeCapabilities.ENERGY).map(IEnergyStorage::getEnergyStored).orElse(0);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()), pPlayer,
                ModBlocks.TURTLE_CHARGER_BLOCK.get());
    }

    public TurtleChargerBlockEntity getBlockEntity() {
        return blockEntity;
    }

}
