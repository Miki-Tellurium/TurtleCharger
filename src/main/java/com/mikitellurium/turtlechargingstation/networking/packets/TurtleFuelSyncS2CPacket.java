package com.mikitellurium.turtlechargingstation.networking.packets;

import com.mikitellurium.telluriumforge.networking.packet.IntSyncPacket;
import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.gui.TurtleChargingStationScreenHandler;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TurtleFuelSyncS2CPacket extends IntSyncPacket {

    public static final PacketType<TurtleFuelSyncS2CPacket> TYPE = PacketType.create(
            new Identifier(TurtleChargingStationMod.MOD_ID, "turtle_fuel_sync"), TurtleFuelSyncS2CPacket::new);

    public static final ClientPlayNetworking.PlayPacketHandler<TurtleFuelSyncS2CPacket> HANDLER = (packet, player, sender) -> {
        // This is run on the Client
        World world = player.getWorld();
        BlockPos pos = packet.getBlockPos();
        int fuel = packet.getValue();
        if(world.getBlockEntity(pos) instanceof TurtleBlockEntity turtle) {
            turtle.getAccess().setFuelLevel(fuel);

            if(player.currentScreenHandler instanceof TurtleChargingStationScreenHandler screenHandler &&
                    screenHandler.getBlockEntity().getPos().equals(pos)) {
                turtle.getAccess().setFuelLevel(fuel);
            }
        }
    };

    public TurtleFuelSyncS2CPacket(BlockPos blockPos, Integer fuel) {
        super(blockPos, fuel);
    }

    public TurtleFuelSyncS2CPacket(PacketByteBuf buf) {
        super(buf);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

}
