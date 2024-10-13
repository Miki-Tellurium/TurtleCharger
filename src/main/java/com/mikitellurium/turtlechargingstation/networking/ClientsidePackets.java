package com.mikitellurium.turtlechargingstation.networking;

import com.mikitellurium.turtlechargingstation.blockentity.TurtleChargingStationBlockEntity;
import com.mikitellurium.turtlechargingstation.gui.TurtleChargingStationScreenHandler;
import com.mikitellurium.turtlechargingstation.networking.packets.EnergySyncS2CPacket;
import com.mikitellurium.turtlechargingstation.networking.packets.TurtleFuelSyncS2CPacket;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ClientsidePackets {

    public static final ClientPlayNetworking.PlayPacketHandler<EnergySyncS2CPacket> ENERGY_SYNC = (packet, player, sender) -> {
        if(player.getWorld().getBlockEntity(packet.getBlockPos())
                instanceof TurtleChargingStationBlockEntity blockEntity) {
            blockEntity.setClientEnergy(packet.getValue());

            if(player.currentScreenHandler instanceof TurtleChargingStationScreenHandler menu &&
                    menu.getBlockEntity().getPos().equals(packet.getBlockPos())) {
                blockEntity.setClientEnergy(packet.getValue());
            }
        }
    };

    public static final ClientPlayNetworking.PlayPacketHandler<TurtleFuelSyncS2CPacket> TURTLE_FUEL_SYNC = (packet, player, sender) -> {
        World world = player.getWorld();
        BlockPos pos = packet.getBlockPos();
        int fuel = packet.getValue();
        if(world.getBlockEntity(pos) instanceof TurtleBlockEntity turtle) {
            turtle.getAccess().setFuelLevel(fuel);
        }
    };

}
