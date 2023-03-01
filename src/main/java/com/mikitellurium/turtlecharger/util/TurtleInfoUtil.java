package com.mikitellurium.turtlecharger.util;

import com.mikitellurium.turtlecharger.blockentity.custom.TurtleChargerBlockEntity;
import dan200.computercraft.shared.Registry;
import dan200.computercraft.shared.turtle.blocks.TileTurtle;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;

import java.util.concurrent.atomic.AtomicBoolean;

public class TurtleInfoUtil {

    public static String getAdjacentTurtleName(TurtleChargerBlockEntity charger, Direction direction) {
        BlockEntity be = charger.getLevel().getBlockEntity(charger.getBlockPos().relative(direction));
        // Adjacent isn't a block entity
        if (be == null) {
            return "-";
        }
        // Adjacent is a turtle
        if (be.getBlockState().getBlock() == Registry.ModBlocks.TURTLE_NORMAL.get() ||
                be.getBlockState().getBlock() == Registry.ModBlocks.TURTLE_ADVANCED.get()) {
            TileTurtle turtle = ((TileTurtle) be);
            if (turtle.hasCustomName()) {
                return turtle.getLabel();
            } else {
                return String.valueOf(turtle.getComputerID());
            }
        }
        // Adjacent can send energy
        if (canSendEnergy(charger)) {
            return  "Energy";
        } else {
            return  "-";
        }
    }

    private static final int white = FastColor.ARGB32.color(255, 255, 255, 255);
    private static final int energyColor = FastColor.ARGB32.color(255, 245, 215, 0);

    public static int getAdjacentTurtleColor(TurtleChargerBlockEntity charger, Direction direction) {
        BlockEntity be = charger.getLevel().getBlockEntity(charger.getBlockPos().relative(direction));
        // Adjacent isn't a block entity
        if (be == null) {
            return white;
        }
        // Adjacent is a turtle
        if (be.getBlockState().getBlock() == Registry.ModBlocks.TURTLE_NORMAL.get() ||
                be.getBlockState().getBlock() == Registry.ModBlocks.TURTLE_ADVANCED.get()) {
            TileTurtle turtle = ((TileTurtle) be);
            if (turtle.getColour() == -1) {
                return white;
            } else {
                return turtle.getColour();
            }
        }
        // Adjacent is energy source
        if (canSendEnergy(charger)) {
            return energyColor;
        }

        return white;
    }

    public static int getAdjacentTurtleFuel(TurtleChargerBlockEntity charger, Direction direction) {
        BlockEntity be = charger.getLevel().getBlockEntity(charger.getBlockPos().relative(direction));
        if (be == null) {
            return -1;
        }
        if (be.getBlockState().getBlock() == Registry.ModBlocks.TURTLE_NORMAL.get() ||
                be.getBlockState().getBlock() == Registry.ModBlocks.TURTLE_ADVANCED.get()) {
            TileTurtle turtle = ((TileTurtle) be);
            return turtle.getAccess().getFuelLevel();
        }

        return -1;
    }

    private static boolean canSendEnergy(BlockEntity be) {
        AtomicBoolean canExtract = new AtomicBoolean(false);
        if (be.getCapability(ForgeCapabilities.ENERGY).isPresent()) {
            be.getCapability(ForgeCapabilities.ENERGY).ifPresent((energyEntity) -> {
                canExtract.set(energyEntity.canExtract());
            });
        }

        return canExtract.get();
    }

}
