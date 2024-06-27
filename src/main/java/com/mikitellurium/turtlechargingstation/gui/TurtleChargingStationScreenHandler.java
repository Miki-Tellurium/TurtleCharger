package com.mikitellurium.turtlechargingstation.gui;

import com.mikitellurium.turtlechargingstation.blockentity.TurtleChargingStationBlockEntity;
import com.mikitellurium.turtlechargingstation.registry.ModScreens;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;

public class TurtleChargingStationScreenHandler extends ScreenHandler {

    private final TurtleChargingStationBlockEntity blockEntity;

    public TurtleChargingStationScreenHandler(int syncId, PlayerInventory playerInventory, TurtleChargingStationBlockEntity.Data data) {
        this(syncId, playerInventory.player.getWorld().getBlockEntity(data.blockPos()));
    }

    public TurtleChargingStationScreenHandler(int syncId, BlockEntity blockEntity) {
        super(ModScreens.TURTLE_CHARGING_STATION_SCREEN_HANDLER, syncId);
        this.blockEntity = (TurtleChargingStationBlockEntity) blockEntity;
    }

    public TurtleChargingStationBlockEntity getBlockEntity() {
        return blockEntity;
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

}
