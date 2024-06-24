package com.mikitellurium.turtlecharginstation.networking.payloads;

import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;

public abstract class IntSyncPayload extends BlockEntitySyncPayload<Integer> {

    public IntSyncPayload(BlockPos blockPos, Integer value) {
        super(blockPos, value);
    }

    @Override
    public void write(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(this.getBlockPos());
        buf.writeInt(this.getValue());
    }

}
