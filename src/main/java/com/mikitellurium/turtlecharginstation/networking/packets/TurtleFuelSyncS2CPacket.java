package com.mikitellurium.turtlecharginstation.networking.packets;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.gui.TurtleChargingStationMenu;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.ISynchronizedWorkHandler;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record TurtleFuelSyncS2CPacket(int fuel, BlockPos pos) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(TurtleChargingStationMod.MOD_ID, "turtle_fuel_sync");

    public TurtleFuelSyncS2CPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readBlockPos());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(fuel);
        buf.writeBlockPos(pos);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public boolean handle(PlayPayloadContext context) {
        ISynchronizedWorkHandler handler = context.workHandler();
        handler.submitAsync(() -> {
            // Client
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof TurtleBlockEntity blockEntity) {
                blockEntity.getAccess().setFuelLevel(fuel);

                if(Minecraft.getInstance().player.containerMenu instanceof TurtleChargingStationMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    blockEntity.getAccess().setFuelLevel(fuel);
                }
            }
        });

        return true;
    }

}
