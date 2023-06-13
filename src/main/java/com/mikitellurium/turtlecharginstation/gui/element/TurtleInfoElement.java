package com.mikitellurium.turtlecharginstation.gui.element;

import com.mikitellurium.turtlecharginstation.blockentity.custom.TurtleChargingStationBlockEntity;
import dan200.computercraft.shared.ModRegistry;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Objects;

public class TurtleInfoElement {

    private final TurtleChargingStationBlockEntity charger;
    private final Rect2i area;
    private final int width = 100;
    private final int height = 80;

    public TurtleInfoElement(TurtleChargingStationBlockEntity charger, int xPos, int yPos) {
        area = new Rect2i(xPos, yPos, width, height);
        this.charger = charger;
    }

    private final Font font = Minecraft.getInstance().font;
    private final int white = FastColor.ARGB32.color(255, 255, 255, 255);

    public void draw(GuiGraphics graphics) {
        int x = area.getX();
        int y = area.getY();
        graphics.drawCenteredString(font, "Name", x + 55, y + 1, white);
        graphics.drawString(font, "Fuel Level", x + area.getWidth() - 14, y + 1, white);
        int h = y;
        for (Direction direction : Direction.values()) {
            h = h + 12;
            graphics.drawString(font, getDirectionString(direction), alignString(getDirectionString(direction), x), h, white);
            graphics.drawCenteredString(font, TurtleData.getAdjacentTurtleName(charger, direction), x + 55, h,
                    TurtleData.getAdjacentTurtleColor(charger, direction));
            graphics.drawCenteredString(font, getFuelString(TurtleData.getAdjacentTurtleFuel(charger, direction)), x + 110, h, white);
        }
    }

    private String getDirectionString(Direction direction) {
        switch (direction.getName()) {
            case "down":  return " Down:";
            case "up":    return "   Up:";
            case "north": return "North:";
            case "south": return "South:";
            case "west":  return " West:";
            case "east":  return " East:";
        }
        return "-";
    }

    private String getFuelString(int fuelLevel) {
        if (fuelLevel == -1) {
            return "-";
        }
        return String.valueOf(fuelLevel);
    }
    // Align to the right
    private int alignString(String string, int xPos) {
        if (Objects.equals(string, "   Up:")) {
            return xPos + 4;
        } else if (Objects.equals(string, " West:") || Objects.equals(string, " East:")) {
            return xPos + 2;
        }
        return xPos;
    }

    public Rect2i getArea() {
        return area;
    }


    public static class TurtleData {

        public static String getAdjacentTurtleName(TurtleChargingStationBlockEntity station, Direction direction) {
            BlockEntity be = station.getLevel().getBlockEntity(station.getBlockPos().relative(direction));
            // Adjacent isn't a block entity
            if (be == null) {
                return "-";
            }
            // Adjacent is a turtle
            if (be.getBlockState().getBlock() == ModRegistry.Blocks.TURTLE_NORMAL.get() ||
                    be.getBlockState().getBlock() == ModRegistry.Blocks.TURTLE_ADVANCED.get()) {
                TurtleBlockEntity turtle = ((TurtleBlockEntity) be);
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
            if (be.getBlockState().getBlock() == ModRegistry.Blocks.TURTLE_NORMAL.get() ||
                    be.getBlockState().getBlock() == ModRegistry.Blocks.TURTLE_ADVANCED.get()) {
                TurtleBlockEntity turtle = ((TurtleBlockEntity) be);
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
            if (be.getBlockState().getBlock() == ModRegistry.Blocks.TURTLE_NORMAL.get() ||
                    be.getBlockState().getBlock() == ModRegistry.Blocks.TURTLE_ADVANCED.get()) {
                TurtleBlockEntity turtle = ((TurtleBlockEntity) be);
                return turtle.getAccess().getFuelLevel();
            }

            return -1;
        }

    }

}
