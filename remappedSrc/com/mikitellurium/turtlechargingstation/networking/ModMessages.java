package com.mikitellurium.turtlechargingstation.networking;

import com.mikitellurium.turtlechargingstation.networking.packets.EnergySyncS2CPacket;
import com.mikitellurium.turtlechargingstation.networking.packets.TurtleFuelSyncS2CPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.function.Consumer;

public class ModMessages {

    public static void registerC2SPackets() {

    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(EnergySyncS2CPacket.TYPE, EnergySyncS2CPacket.HANDLER);
        ClientPlayNetworking.registerGlobalReceiver(TurtleFuelSyncS2CPacket.TYPE, TurtleFuelSyncS2CPacket.HANDLER);
    }

    // Send to all player that are tracking this blockPos
    public static void sendToClients(World world, BlockPos pos, Consumer<ServerPlayerEntity> consumer) {
        if (world != null && !world.isClient) {
            for (ServerPlayerEntity player : PlayerLookup.tracking((ServerWorld) world, pos)) {
                consumer.accept(player);
            }
        }
    }

}
