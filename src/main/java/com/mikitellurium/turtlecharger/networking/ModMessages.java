package com.mikitellurium.turtlecharger.networking;

import com.mikitellurium.turtlecharger.TurtleChargerMod;
import com.mikitellurium.turtlecharger.gui.TurtleChargerGui;
import com.mikitellurium.turtlecharger.networking.packets.EnergySyncS2CPacket;
import com.mikitellurium.turtlecharger.networking.packets.TurtleFuelSyncS2CPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class ModMessages {
    private static SimpleChannel INSTANCE;

    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(TurtleChargerMod.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();

        INSTANCE = net;

        net.messageBuilder(EnergySyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EnergySyncS2CPacket::new)
                .encoder(EnergySyncS2CPacket::toBytes)
                .consumerMainThread(EnergySyncS2CPacket::handle)
                .add();
        net.messageBuilder(TurtleFuelSyncS2CPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(TurtleFuelSyncS2CPacket::new)
                .encoder(TurtleFuelSyncS2CPacket::toBytes)
                .consumerMainThread(TurtleFuelSyncS2CPacket::handle)
                .add();
    }

    public static <MSG> void sendToServer(MSG message) {
        INSTANCE.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static <MSG> void sendToClients(MSG message) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), message);
    }

}
