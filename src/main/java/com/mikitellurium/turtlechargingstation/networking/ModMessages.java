package com.mikitellurium.turtlechargingstation.networking;

import com.mikitellurium.telluriumforge.networking.NetworkingHelper;
import com.mikitellurium.turtlechargingstation.networking.packets.EnergySyncPayload;
import com.mikitellurium.turtlechargingstation.networking.packets.TurtleFuelSyncPayload;

public class ModMessages {

    public static void register() {
        NetworkingHelper.registerPayload(EnergySyncPayload.ID, EnergySyncPayload.PACKET_CODEC, EnergySyncPayload::handleClient);
        NetworkingHelper.registerPayload(TurtleFuelSyncPayload.ID, TurtleFuelSyncPayload.PACKET_CODEC, TurtleFuelSyncPayload::handleClient);
    }

}
