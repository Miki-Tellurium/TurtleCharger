package com.mikitellurium.turtlecharginstation.networking.packets;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.blockentity.TurtleChargingStationBlockEntity;
import com.mikitellurium.turtlecharginstation.gui.TurtleChargingStationMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.ISynchronizedWorkHandler;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public record EnergySyncS2CPacket(int energy, BlockPos pos) implements CustomPacketPayload {

    public static final ResourceLocation ID = new ResourceLocation(TurtleChargingStationMod.MOD_ID, "energy_sync");

    public EnergySyncS2CPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readBlockPos());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(energy);
        buf.writeBlockPos(pos);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

    public void handle(PlayPayloadContext context) {
        ISynchronizedWorkHandler handler = context.workHandler();
        handler.submitAsync(() -> {
            // Client
            if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof TurtleChargingStationBlockEntity blockEntity) {
                blockEntity.setClientEnergy(energy);

                if(Minecraft.getInstance().player.containerMenu instanceof TurtleChargingStationMenu menu &&
                        menu.getBlockEntity().getBlockPos().equals(pos)) {
                    blockEntity.setClientEnergy(energy);
                }
            }
        });
    }

}
