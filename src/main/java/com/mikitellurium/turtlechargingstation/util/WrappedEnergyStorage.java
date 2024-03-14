package com.mikitellurium.turtlechargingstation.util;

import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtLong;
import net.minecraft.util.math.Direction;
import team.reborn.energy.api.EnergyStorage;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.function.BiFunction;
import java.util.function.Consumer;

/**
 * A simple wrapper around the SimpleEnergyStorage class that handles transactions on
 * insertion/extraction.
 */
public class WrappedEnergyStorage {

    private final SimpleEnergyStorage energyStorage;

    /**
     * Constructs a WrappedEnergyStorage with specified capacity, maximum insertion, and extraction limits,
     * along with a callback for final commit.
     *
     * @param capacity       The maximum energy capacity of the storage.
     * @param maxInsert      The maximum amount of energy that can be inserted per operation
     * @param maxExtract     The maximum amount of energy that can be extracted per operation
     * @param onFinalCommit  A Consumer callback invoked when a transaction is finally committed
     */
    public WrappedEnergyStorage(long capacity, long maxInsert, long maxExtract, Consumer<EnergyStorage> onFinalCommit) {
        this.energyStorage = new SimpleEnergyStorage(capacity, maxInsert, maxExtract) {
            @Override
            protected void onFinalCommit() {
                onFinalCommit.accept(this);
            }
        };
    }

    /**
     * Constructs a WrappedEnergyStorage with specified capacity, maximum insertion,
     * and a callback for final commit, assuming equal insertion and extraction limits.
     *
     * @param capacity       The maximum energy capacity of the storage
     * @param maxInsert      The maximum amount of energy that can be inserted or extracted per operation
     * @param onFinalCommit  A Consumer callback invoked when a transaction is finally committed
     */
    public WrappedEnergyStorage(long capacity, long maxInsert, Consumer<EnergyStorage> onFinalCommit) {
        this(capacity, maxInsert, maxInsert, onFinalCommit);
    }

    /**
     * Constructs a WrappedEnergyStorage with specified capacity, assuming equal insertion and extraction limits,
     * and a callback for final commit.
     *
     * @param capacity       The maximum energy capacity of the storage
     * @param onFinalCommit  A Consumer callback invoked when a transaction is finally committed
     */
    public WrappedEnergyStorage(long capacity, Consumer<EnergyStorage> onFinalCommit) {
        this(capacity, capacity, capacity, onFinalCommit);
    }

    /**
     * Exposes an EnergyStorage API based on the specified direction using the provided expose function.
     *
     * @param direction      The direction indicating the context or side for which the EnergyStorage API is exposed
     * @param exposeFunction A function used to customize the exposure of the EnergyStorage API
     * @return An implementation of the EnergyStorage interface specific to the given direction.
     */
    public EnergyStorage exposeEnergyStorageApi(Direction direction, BiFunction<EnergyStorage, Direction, EnergyStorage> exposeFunction) {
        return exposeFunction.apply(this.energyStorage, direction);
    }

    /**
     * Inserts a specified amount of energy into the storage within a transaction.
     *
     * @param amount The amount of energy to insert
     * @return The actual amount of energy inserted, or 0 if the insertion failed
     */
    public long insert(long amount) {
        try (Transaction transaction = Transaction.openOuter()) {
            long amountInserted = energyStorage.insert(amount, transaction);
            if (amountInserted == amount) {
                transaction.commit();
                return amountInserted;
            }
        }
        return 0;
    }

    /**
     * Extracts a specified amount of energy from the storage within a transaction.
     *
     * @param amount The amount of energy to extract
     * @return The actual amount of energy extracted, or 0 if the extraction failed
     */
    public long extract(long amount) {
        try (Transaction transaction = Transaction.openOuter()) {
            long amountExtracted = this.energyStorage.extract(amount, transaction);
            if (amountExtracted == amount) {
                transaction.commit();
                return amountExtracted;
            }
        }
        return 0;
    }


    public long getEnergy() {
        return this.energyStorage.getAmount();
    }

    public long getCapacity() {
        return this.energyStorage.getCapacity();
    }

    /**
     * Sets the energy amount in the storage directly.
     *
     * @param energy The new energy amount to set
     */
    public void setEnergy(long energy) {
        this.energyStorage.amount = Math.min(energy, this.getCapacity());
    }

    public NbtElement writeNbt() {
        return NbtLong.of(this.getEnergy());
    }

    public void readNBT(NbtElement nbt) {
        if (!(nbt instanceof NbtLong nbtLong))
            throw new IllegalArgumentException("Cannot read an Nbt that isn't a long value");
        this.setEnergy(nbtLong.longValue());
    }

}
