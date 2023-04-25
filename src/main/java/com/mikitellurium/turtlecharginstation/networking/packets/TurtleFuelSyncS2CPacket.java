package com.mikitellurium.turtlecharginstation.networking.packets;

import com.mikitellurium.turtlecharginstation.gui.TurtleChargingStationMenu;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TurtleFuelSyncS2CPacket {
        private final int fuelLevel;
        private final BlockPos pos;

        public TurtleFuelSyncS2CPacket(int fuelLevel, BlockPos pos) {
            this.fuelLevel = fuelLevel;
            this.pos = pos;
        }

        public TurtleFuelSyncS2CPacket(FriendlyByteBuf buf) {
            this.fuelLevel = buf.readInt();
            this.pos = buf.readBlockPos();
        }

        public void toBytes(FriendlyByteBuf buf) {
            buf.writeInt(fuelLevel);
            buf.writeBlockPos(pos);
        }

        public boolean handle(Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context context = supplier.get();
            context.enqueueWork(() -> {
                // Client
                if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof TurtleBlockEntity blockEntity) {
                    blockEntity.getAccess().setFuelLevel(fuelLevel);

                    if(Minecraft.getInstance().player.containerMenu instanceof TurtleChargingStationMenu menu &&
                            menu.getBlockEntity().getBlockPos().equals(pos)) {
                        blockEntity.getAccess().setFuelLevel(fuelLevel);
                    }
                }
            });

            return true;
        }

}
