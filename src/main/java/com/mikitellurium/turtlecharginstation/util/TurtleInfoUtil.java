package com.mikitellurium.turtlecharginstation.util;

import com.mikitellurium.turtlecharginstation.blockentity.custom.TurtleChargingStationBlockEntity;
import dan200.computercraft.shared.Registry;
import dan200.computercraft.shared.turtle.blocks.TileTurtle;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.concurrent.atomic.AtomicBoolean;

public class TurtleInfoUtil {

    public static String getAdjacentTurtleName(TurtleChargingStationBlockEntity station, Direction direction) {
        BlockEntity be = station.getLevel().getBlockEntity(station.getBlockPos().relative(direction));
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

        return  "-";
    }

    private static final int white = FastColor.ARGB32.color(255, 255, 255, 255);

    public static int getAdjacentTurtleColor(TurtleChargingStationBlockEntity station, Direction direction) {
        BlockEntity be = station.getLevel().getBlockEntity(station.getBlockPos().relative(direction));
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

        return white;
    }

    public static int getAdjacentTurtleFuel(TurtleChargingStationBlockEntity station, Direction direction) {
        BlockEntity be = station.getLevel().getBlockEntity(station.getBlockPos().relative(direction));
        // Adjacent isn't a block entity
        if (be == null) {
            return -1;
        }
        // Adjacent is a turtle
        if (be.getBlockState().getBlock() == Registry.ModBlocks.TURTLE_NORMAL.get() ||
                be.getBlockState().getBlock() == Registry.ModBlocks.TURTLE_ADVANCED.get()) {
            TileTurtle turtle = ((TileTurtle) be);
            return turtle.getAccess().getFuelLevel();
        }

        return -1;
    }

}
