package com.mikitellurium.turtlechargingstation.networking.packets;

import com.mikitellurium.telluriumforge.networking.packet.BlockEntitySyncPayload;
import com.mikitellurium.telluriumforge.networking.packet.IntSyncPayload;
import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.gui.TurtleChargingStationScreenHandler;
import com.mikitellurium.turtlechargingstation.util.FastId;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class TurtleFuelSyncPayload extends IntSyncPayload {

    public static final CustomPayload.Id<TurtleFuelSyncPayload> ID = BlockEntitySyncPayload.makeId(FastId.modId("turtle_fuel_sync"));
    public static final PacketCodec<PacketByteBuf, TurtleFuelSyncPayload> PACKET_CODEC = PacketCodec.tuple(
            BlockPos.PACKET_CODEC,
            TurtleFuelSyncPayload::getBlockPos,
            PacketCodecs.INTEGER,
            TurtleFuelSyncPayload::getValue,
            TurtleFuelSyncPayload::new
    );

    public TurtleFuelSyncPayload(BlockPos blockPos, Integer value) {
        super(blockPos, value);
    }

    @Override
    public void handleClient(ClientPlayNetworking.Context context) {
        PlayerEntity player = context.player();
        int fuel = this.getValue();
        context.client().execute(() -> {
            if(player.getWorld().getBlockEntity(this.getBlockPos()) instanceof TurtleBlockEntity turtle) {
                turtle.getAccess().setFuelLevel(fuel);

                if(player.currentScreenHandler instanceof TurtleChargingStationScreenHandler screenHandler &&
                        screenHandler.getBlockEntity().getPos().equals(this.getBlockPos())) {
                    turtle.getAccess().setFuelLevel(fuel);
                }
            }
        });
    }

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

}
