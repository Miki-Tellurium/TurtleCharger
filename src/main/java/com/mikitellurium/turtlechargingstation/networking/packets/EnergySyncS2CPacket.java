package com.mikitellurium.turtlechargingstation.networking.packets;

import com.mikitellurium.telluriumforge.networking.packet.LongSyncPacket;
import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.blockentity.TurtleChargingStationBlockEntity;
import com.mikitellurium.turtlechargingstation.gui.TurtleChargingStationScreenHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class EnergySyncS2CPacket extends LongSyncPacket {

    public static final PacketType<EnergySyncS2CPacket> TYPE = PacketType.create(
            new Identifier(TurtleChargingStationMod.MOD_ID, "energy_sync"), EnergySyncS2CPacket::new);

    public EnergySyncS2CPacket(BlockPos blockPos, long energy) {
        super(blockPos, energy);
    }

    public EnergySyncS2CPacket(PacketByteBuf buf) {
        super(buf);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

}
