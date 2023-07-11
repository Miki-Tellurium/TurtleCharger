package com.mikitellurium.turtlechargingstation.blockentity;

import com.mikitellurium.turtlechargingstation.block.TurtleChargingStationBlock;
import com.mikitellurium.turtlechargingstation.registry.ModBlockEntities;
import com.mikitellurium.turtlechargingstation.config.api.TelluriumConfig;
import com.mikitellurium.turtlechargingstation.gui.TurtleChargingStationScreenHandler;
import com.mikitellurium.turtlechargingstation.networking.ModMessages;
import com.mikitellurium.turtlechargingstation.networking.packets.EnergySyncS2CPacket;
import com.mikitellurium.turtlechargingstation.networking.packets.TurtleFuelSyncS2CPacket;
import dan200.computercraft.shared.ModRegistry;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import team.reborn.energy.api.base.SimpleEnergyStorage;

public class TurtleChargingStationBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {

    public static TelluriumConfig.RangedConfigEntry<Long> CAPACITY;
    public static TelluriumConfig.RangedConfigEntry<Long> CONVERSION_RATE; // Based on Thermal Expansion stirling dynamo production rate using coal
    private final long maxReceive = CONVERSION_RATE.getValue() * 6; // 6 sides
    private final SimpleEnergyStorage ENERGY_STORAGE = new SimpleEnergyStorage(CAPACITY.getValue(), CAPACITY.getValue(), maxReceive) {
        @Override
        protected void onFinalCommit() {
            markDirty();
            ModMessages.sendToClients(world, getPos(), (player) ->
                    ServerPlayNetworking.send(player, new EnergySyncS2CPacket(getPos(), this.getAmount())));
        }
    };

    public TurtleChargingStationBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.TURTLE_CHARGING_STATION, pos, blockState);
    }

    // Energy stuff
    private int extractCount = 6; // Track if a turtle was charged this tick
    private int textureTimer = 10;

    public static void tick(World world, BlockPos pos, BlockState state, TurtleChargingStationBlockEntity chargingStation) {
        if (world.isClient) {
            return;
        }

        // Check every direction for turtles
        for (Direction direction : Direction.values()) {
            BlockEntity be = world.getBlockEntity(chargingStation.pos.offset(direction));
            // If no block entity is found return
            if (be == null) {
                chargingStation.extractCount--;
                continue;
            }
            // Check if block entity is a turtle
            if (be.getCachedState().getBlock() == ModRegistry.Blocks.TURTLE_NORMAL.get() ||
                    be.getCachedState().getBlock() == ModRegistry.Blocks.TURTLE_ADVANCED.get()) {

                // If enough energy and no redstone signal
                if (chargingStation.ENERGY_STORAGE.getAmount() >= CONVERSION_RATE.getValue() &&
                        chargingStation.getCachedState().get(TurtleChargingStationBlock.ENABLED)) {
                    TurtleBlockEntity turtle = (TurtleBlockEntity) be;
                    if (turtle.getAccess().getFuelLevel() == turtle.getAccess().getFuelLimit()) {
                        chargingStation.extractCount--;
                    } else {
                        world.setBlockState(pos, state.with(TurtleChargingStationBlock.CHARGING, true), 2);
                        refuelTurtle(chargingStation, turtle);
                        chargingStation.extractCount++;
                        // Sync fuel with client for gui
                        ModMessages.sendToClients(world, pos, (player) -> ServerPlayNetworking.send(player,
                                new TurtleFuelSyncS2CPacket(turtle.getPos(), turtle.getAccess().getFuelLevel())));
                    }
                } else {
                    chargingStation.extractCount--;
                }

            } else {
                chargingStation.extractCount--;
            }
            // End of direction for-loop
        }

        if (chargingStation.extractCount <= 0) {
            if (--chargingStation.textureTimer <= 0) {
                world.setBlockState(pos, state.with(TurtleChargingStationBlock.CHARGING, false), 2);
                chargingStation.textureTimer = 0;
            }
        } else {
            chargingStation.textureTimer = 10;
        }
        chargingStation.extractCount = 6;

        //debugRecharge(world, pos, chargingStation);
    }

    private static void refuelTurtle(TurtleChargingStationBlockEntity chargingStation , TurtleBlockEntity turtle) {
       turtle.getAccess().addFuel(1);
        try (Transaction transaction = Transaction.openOuter()) {
            long amountExtracted = chargingStation.ENERGY_STORAGE.extract(CONVERSION_RATE.getValue(), transaction);
            if (amountExtracted == CONVERSION_RATE.getValue()) {
                transaction.commit();
            }
        }
    }

    public SimpleEnergyStorage getEnergyStorage() {
        return ENERGY_STORAGE;
    }

    public void setEnergy(long energy) {
        this.ENERGY_STORAGE.amount = energy;
    }

    // Used for debug purposes
    private static void debugRecharge(World level, BlockPos pos, TurtleChargingStationBlockEntity chargingStation) {
        BlockEntity blockEntity = level.getBlockEntity(pos.up());
        if (blockEntity instanceof BeaconBlockEntity) {
            long toAdd = 1200;
            try (Transaction transaction = Transaction.openOuter()) {
                long amountInserted = chargingStation.ENERGY_STORAGE.insert(toAdd, transaction);
                if (amountInserted == toAdd) {
                    transaction.commit();
                }
            }
        }
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
        buf.writeLong(ENERGY_STORAGE.getAmount());
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("block.turtlechargingstation.turtle_charging_station");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putLong("turtle_charger.energy", ENERGY_STORAGE.getAmount());
        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        ENERGY_STORAGE.amount = Math.min(nbt.getLong("turtle_charger.energy"), CAPACITY.getValue());
    }

}
