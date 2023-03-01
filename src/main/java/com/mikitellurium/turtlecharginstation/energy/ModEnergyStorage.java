package com.mikitellurium.turtlecharginstation.energy;

import net.minecraftforge.energy.EnergyStorage;

public abstract class ModEnergyStorage extends EnergyStorage {

    public ModEnergyStorage(int capacity, int maxReceive) {
        super(capacity, maxReceive);
    }

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int receivedEnergy = super.receiveEnergy(maxReceive, simulate);
        if (receivedEnergy != 0) {
            onEnergyChanged();
        }

        return receivedEnergy;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int extractedEnergy = super.extractEnergy(maxExtract, simulate);
        if ( extractedEnergy != 0) {
            onEnergyChanged();
        }

        return extractedEnergy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public abstract void onEnergyChanged();

}
