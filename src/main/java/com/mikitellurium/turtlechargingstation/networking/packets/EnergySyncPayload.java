package com.mikitellurium.turtlechargingstation.networking.packets;

import com.mikitellurium.telluriumforge.networking.packet.BlockEntitySyncPayload;
import com.mikitellurium.telluriumforge.networking.packet.LongSyncPayload;
import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.blockentity.TurtleChargingStationBlockEntity;
import com.mikitellurium.turtlechargingstation.gui.TurtleChargingStationScreenHandler;
import com.mikitellurium.turtlechargingstation.util.FastId;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class EnergySyncPayload extends LongSyncPayload {

    public static final CustomPayload.Id<EnergySyncPayload> ID = BlockEntitySyncPayload.makeId(FastId.modId("energy_sync"));
    public static final PacketCodec<PacketByteBuf, EnergySyncPayload> PACKET_CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC,
            EnergySyncPayload::getBlockPos,
            PacketCodecs.VAR_LONG,
            EnergySyncPayload::getValue,
            EnergySyncPayload::new
    );

    public EnergySyncPayload(BlockPos blockPos, Long value) {
        super(blockPos, value);
    }

    @Override
    public void handleClient(ClientPlayNetworking.Context context) {
        PlayerEntity player = context.player();
        context.client().execute(() -> {
            if (player.getWorld().getBlockEntity(this.getBlockPos())
                    instanceof TurtleChargingStationBlockEntity blockEntity) {
                blockEntity.setClientEnergy(this.getValue());

                if (player.currentScreenHandler instanceof TurtleChargingStationScreenHandler menu &&
                        menu.getBlockEntity().getPos().equals(this.getBlockPos())) {
                    blockEntity.setClientEnergy(this.getValue());
                }
            }
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
