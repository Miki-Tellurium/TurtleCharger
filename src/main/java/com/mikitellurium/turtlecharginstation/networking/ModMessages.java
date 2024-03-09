package com.mikitellurium.turtlecharginstation.networking;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.networking.packets.EnergySyncS2CPacket;
import com.mikitellurium.turtlecharginstation.networking.packets.TurtleFuelSyncS2CPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public class ModMessages {

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ModMessages::registerPackets);
    }

    private static void registerPackets(RegisterPayloadHandlerEvent event) {
        final IPayloadRegistrar registrar = event.registrar(TurtleChargingStationMod.MOD_ID);
        registrar.play(EnergySyncS2CPacket.ID, EnergySyncS2CPacket::new, EnergySyncS2CPacket::handle);
        registrar.play(TurtleFuelSyncS2CPacket.ID, TurtleFuelSyncS2CPacket::new, TurtleFuelSyncS2CPacket::handle);
    }

    public static <P extends CustomPacketPayload> void sendToServer(P message) {
        PacketDistributor.SERVER.noArg().send(message);
    }

    public static <P extends CustomPacketPayload> void sendToPlayer(P message, ServerPlayer player) {
        PacketDistributor.PLAYER.noArg().send(message);
    }

    public static <P extends CustomPacketPayload> void sendToAll(P message) {
        PacketDistributor.ALL.noArg().send(message);
    }

}
