package com.mikitellurium.turtlechargingstation.networking;

import com.mikitellurium.telluriumforge.networking.NetworkingHelper;
import com.mikitellurium.turtlechargingstation.networking.packets.EnergySyncS2CPacket;
import com.mikitellurium.turtlechargingstation.networking.packets.TurtleFuelSyncS2CPacket;

public class ModMessages {

    public static void registerC2SPackets() {

    }

    public static void registerS2CPackets() {
        NetworkingHelper.registerS2CPacket(EnergySyncS2CPacket.TYPE, EnergySyncS2CPacket.HANDLER);
        NetworkingHelper.registerS2CPacket(TurtleFuelSyncS2CPacket.TYPE, TurtleFuelSyncS2CPacket.HANDLER);
    }

}
