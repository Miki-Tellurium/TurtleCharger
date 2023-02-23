package com.mikitellurium.turtlecharger.gui;

import com.mikitellurium.turtlecharger.block.ModBlocks;
import com.mikitellurium.turtlecharger.blockentity.custom.TurtleChargerBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TurtleChargerMenu extends AbstractContainerMenu {

    public final TurtleChargerBlockEntity blockEntity;
    private final Level level;

    protected TurtleChargerMenu(int id, Inventory inventory, FriendlyByteBuf data) {
        this(id, inventory, inventory.player.level.getBlockEntity(data.readBlockPos()));
    }

    public TurtleChargerMenu(int id, Inventory inventory, BlockEntity entity) {
        super(ModMenuTypes.TURTLE_CHARGER_GUI.get(), id);
        blockEntity = (TurtleChargerBlockEntity) entity;
        this.level = inventory.player.level;


    }

    public BlockState getAdjacentTurtles() {
        // Return adjacent turtles
        return null;
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

}
