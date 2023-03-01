package com.mikitellurium.turtlecharginstation.networking.packets;

import com.mikitellurium.turtlecharginstation.blockentity.custom.TurtleChargingStationBlockEntity;
import com.mikitellurium.turtlecharginstation.gui.TurtleChargerMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EnergySyncS2CPacket {
        private final int energy;
        private final BlockPos pos;

        public EnergySyncS2CPacket(int energy, BlockPos pos) {
            this.energy = energy;
            this.pos = pos;
        }

        public EnergySyncS2CPacket(FriendlyByteBuf buf) {
            this.energy = buf.readInt();
            this.pos = buf.readBlockPos();
        }

        public void toBytes(FriendlyByteBuf buf) {
            buf.writeInt(energy);
            buf.writeBlockPos(pos);
        }

        public boolean handle(Supplier<NetworkEvent.Context> supplier) {
            NetworkEvent.Context context = supplier.get();
            context.enqueueWork(() -> {
                // Client
                if(Minecraft.getInstance().level.getBlockEntity(pos) instanceof TurtleChargingStationBlockEntity blockEntity) {
                    blockEntity.setClientEnergy(energy);

                    if(Minecraft.getInstance().player.containerMenu instanceof TurtleChargerMenu menu &&
                            menu.getBlockEntity().getBlockPos().equals(pos)) {
                        blockEntity.setClientEnergy(energy);
                    }
                }
            });

            return true;
        }

}
