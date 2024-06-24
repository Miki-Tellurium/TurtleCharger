package com.mikitellurium.turtlecharginstation.networking;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.networking.payloads.EnergySyncPayload;
import com.mikitellurium.turtlecharginstation.networking.payloads.TurtleFuelSyncPayload;
import com.mikitellurium.turtlecharginstation.util.FastLoc;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@EventBusSubscriber(modid = TurtleChargingStationMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class ModMessages {

    @SubscribeEvent
    public static void registerPayloads(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(FastLoc.modId());
        registrar.playToClient(EnergySyncPayload.TYPE, EnergySyncPayload.CODEC, EnergySyncPayload::handle);
        registrar.playToClient(TurtleFuelSyncPayload.TYPE, TurtleFuelSyncPayload.CODEC, TurtleFuelSyncPayload::handle);
    }

}
