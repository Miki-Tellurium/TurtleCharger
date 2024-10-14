package com.mikitellurium.turtlechargingstation.blockentity;

import com.mikitellurium.telluriumforge.config.RangedConfigEntry;
import com.mikitellurium.telluriumforge.networking.NetworkingHelper;
import com.mikitellurium.turtlechargingstation.block.TurtleChargingStationBlock;
import com.mikitellurium.turtlechargingstation.gui.TurtleChargingStationScreenHandler;
import com.mikitellurium.turtlechargingstation.networking.packets.EnergySyncS2CPacket;
import com.mikitellurium.turtlechargingstation.networking.packets.TurtleFuelSyncS2CPacket;
import com.mikitellurium.turtlechargingstation.registry.ModBlockEntities;
import com.mikitellurium.turtlechargingstation.util.WrappedEnergyStorage;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import dan200.computercraft.shared.turtle.core.TurtleBrain;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.EnergyStorage;

public class TurtleChargingStationBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {

    public static RangedConfigEntry<Long> CAPACITY;
    public static RangedConfigEntry<Long> CONVERSION_RATE;
    private final long maxInsert = CONVERSION_RATE.getValue() * 6; // 6 sides
    private final WrappedEnergyStorage energyStorage = new WrappedEnergyStorage(CAPACITY.getValue(), maxInsert,
            (storage) -> {
                markDirty();
                if (!world.isClient) {
                    NetworkingHelper.sendToTrackingClients((ServerWorld) world, pos, new EnergySyncS2CPacket(pos, storage.getAmount()));
                }
            });

    private boolean hasChargedTurtle = false; // Track if a turtle was charged this tick
    private int textureTimer = 10; // Delay block state update to avoid texture flicker

    public TurtleChargingStationBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.TURTLE_CHARGING_STATION, pos, blockState);
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient) {
            return;
        }

        for (Direction direction : Direction.values()) {

            BlockEntity blockEntity = world.getBlockEntity(this.pos.offset(direction));
            if (blockEntity == null) {
                continue;
            }

            if (blockEntity instanceof TurtleBlockEntity turtle) {
                TurtleBrain turtleBrain = (TurtleBrain) turtle.getAccess();
                if (this.canRechargeTurtle() && turtleBrain.getFuelLevel() < turtleBrain.getFuelLimit()) {
                    this.refuelTurtle(turtle);
                    this.hasChargedTurtle = true;
                    world.setBlockState(pos, state.with(TurtleChargingStationBlock.CHARGING, true));
                    NetworkingHelper.sendToTrackingClients((ServerWorld) world, turtle.getPos(), new TurtleFuelSyncS2CPacket(turtle.getPos(), turtleBrain.getFuelLevel()));
                }
            }

        }

        if (!this.hasChargedTurtle) { // If no turtle was recharged tick the block state timer
            if (--this.textureTimer <= 0) {
                world.setBlockState(pos, state.with(TurtleChargingStationBlock.CHARGING, false));
                this.textureTimer = 0;
            }
        } else {
            this.textureTimer = 10;
        }
        this.hasChargedTurtle = false;
    }

    private boolean canRechargeTurtle() {
        return this.energyStorage.getEnergy() >= CONVERSION_RATE.getValue() &&
                this.getCachedState().get(TurtleChargingStationBlock.ENABLED);
    }

    private void refuelTurtle(TurtleBlockEntity turtle) {
       turtle.getAccess().addFuel(1);
       this.energyStorage.extract(CONVERSION_RATE.getValue());
    }

    public long getEnergy() {
        return this.energyStorage.getEnergy();
    }

    public long getMaxEnergy() {
        return this.energyStorage.getCapacity();
    }

    // Used for synchronization, do not call directly
    public void setClientEnergy(long energy) {
        this.energyStorage.setEnergy(energy);
    }

    // Gui
    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new TurtleChargingStationScreenHandler(syncId, this);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeLong(energyStorage.getEnergy());
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.turtlechargingstation.turtle_charging_station");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.put("storedEnergy", this.energyStorage.writeNbt());
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.energyStorage.readNBT(nbt.get("storedEnergy"));
    }

    public static EnergyStorage registerEnergyStorage(TurtleChargingStationBlockEntity station, Direction direction) {
        return station.energyStorage.exposeEnergyStorageApi(direction, (storage, dir) -> storage);
    }

}
