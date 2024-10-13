package com.mikitellurium.turtlechargingstation.networking;

import com.mikitellurium.turtlechargingstation.blockentity.TurtleChargingStationBlockEntity;
import com.mikitellurium.turtlechargingstation.gui.TurtleChargingStationScreenHandler;
import com.mikitellurium.turtlechargingstation.networking.packets.EnergySyncS2CPacket;
import com.mikitellurium.turtlechargingstation.networking.packets.TurtleFuelSyncS2CPacket;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ModMessages {

    public static void registerC2SPackets() {

    }

    public static void registerS2CPackets() {
        ClientPlayNetworking.registerGlobalReceiver(EnergySyncS2CPacket.TYPE, ClientsidePackets.ENERGY_SYNC);
        ClientPlayNetworking.registerGlobalReceiver(TurtleFuelSyncS2CPacket.TYPE, ClientsidePackets.TURTLE_FUEL_SYNC);
    }

}
