package com.mikitellurium.turtlechargingstation.networking.packets;

import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.gui.TurtleChargingStationScreenHandler;
import com.mikitellurium.turtlechargingstation.networking.ModMessages;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TurtleFuelSyncS2CPacket implements FabricPacket {

    public static final PacketType<TurtleFuelSyncS2CPacket> TYPE = PacketType.create(
            new Identifier(TurtleChargingStationMod.MOD_ID, "turtle_fuel_sync"), TurtleFuelSyncS2CPacket::new);

    public static final ClientPlayNetworking.PlayPacketHandler<TurtleFuelSyncS2CPacket> HANDLER = (packet, player, sender) -> {
        // This is run on the Client
        World world = player.getWorld();
        BlockPos pos = packet.getBlockPos();
        int fuel = packet.getFuel();
        // Send to player
        if(world.getBlockEntity(pos) instanceof TurtleBlockEntity turtle) {
            turtle.getAccess().setFuelLevel(fuel);
            // Send to Gui
            if(player.currentScreenHandler instanceof TurtleChargingStationScreenHandler screenHandler &&
                    screenHandler.getBlockEntity().getPos().equals(pos)) {
                turtle.getAccess().setFuelLevel(fuel);
            }
        }
    };

    private final BlockPos blockPos;
    private final int fuel;

    public TurtleFuelSyncS2CPacket(BlockPos blockPos, int fuel) {
        this.blockPos = blockPos;
        this.fuel = fuel;
    }

    public TurtleFuelSyncS2CPacket(PacketByteBuf buf) {
        this(buf.readBlockPos(), buf.readVarInt());
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBlockPos(this.blockPos);
        buf.writeVarInt(this.fuel);
    }

    @Override
    public PacketType<?> getType() {
        return TYPE;
    }

    public BlockPos getBlockPos() {
        return blockPos;
    }

    public int getFuel() {
        return fuel;
    }

}
