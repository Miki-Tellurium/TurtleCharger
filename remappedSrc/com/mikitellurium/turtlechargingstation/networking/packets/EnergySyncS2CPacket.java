package com.mikitellurium.turtlechargingstation.networking.packets;

import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.blockentity.TurtleChargingStationBlockEntity;
import com.mikitellurium.turtlechargingstation.gui.TurtleChargingStationScreenHandler;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class EnergySyncS2CPacket implements FabricPacket {

    public static final PacketType<EnergySyncS2CPacket> TYPE = PacketType.create(
            new Identifier(TurtleChargingStationMod.MOD_ID, "energy_sync"), EnergySyncS2CPacket::new);

    public static final ClientPlayNetworking.PlayPacketHandler<EnergySyncS2CPacket> HANDLER = (packet, player, responseSender) -> {
        // This is run on the Client
        // Send to player
        if(player.method_48926().getBlockEntity(packet.getBlockPos())
                instanceof TurtleChargingStationBlockEntity blockEntity) {
            blockEntity.setEnergy(packet.getEnergy());
            // Send to Gui
            if(player.currentScreenHandler instanceof TurtleChargingStationScreenHandler menu &&
                    menu.getBlockEntity().getPos().equals(packet.blockPos)) {
                blockEntity.setEnergy(packet.getEnergy());
            }
        }
    };

    private final BlockPos blockPos;
    private final long energy;

    public EnergySyncS2CPacket(BlockPos blockPos, long energy) {
        this.blockPos = blockPos;
        this.energy = energy;
    }

    public EnergySyncS2CPacket(PacketByteBuf buf) {
        this(buf.readBlockPos(), buf.readVarLong());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
        buf.writeVarLong(this.energy);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public long getEnergy() {
        return energy;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

}
