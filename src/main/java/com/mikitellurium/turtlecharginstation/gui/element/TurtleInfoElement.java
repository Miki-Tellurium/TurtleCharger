package com.mikitellurium.turtlecharginstation.gui.element;

import com.mikitellurium.turtlecharginstation.block.blockentity.custom.TurtleChargingStationBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import dan200.computercraft.shared.Registry;
import dan200.computercraft.shared.turtle.blocks.TileTurtle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Objects;

public class TurtleInfoElement extends GuiComponent {

    private final TurtleChargingStationBlockEntity charger;
    private Rect2i area;
    private final int width = 100;
    private final int height = 80;

    public TurtleInfoElement(TurtleChargingStationBlockEntity charger, int xPos, int yPos) {
        area = new Rect2i(xPos, yPos, width, height);
        this.charger = charger;
    }

    private final Font font = Minecraft.getInstance().font;
    private final int white = FastColor.ARGB32.color(255, 255, 255, 255);

    public void draw(PoseStack poseStack) {
        int x = area.getX();
        int y = area.getY();
        drawCenteredString(poseStack, font, "Name", x + 55, y + 1, white);
        drawString(poseStack, font, "Fuel Level", x + area.getWidth() - 14, y + 1, white);
        int h = y;
        for (Direction direction : Direction.values()) {
            h = h + 12;
            drawString(poseStack, font, getDirectionString(direction), alignString(getDirectionString(direction), x), h, white);
            drawCenteredString(poseStack, font, TurtleData.getAdjacentTurtleName(charger, direction), x + 55, h,
                    TurtleData.getAdjacentTurtleColor(charger, direction));
            drawCenteredString(poseStack, font, getFuelString(TurtleData.getAdjacentTurtleFuel(charger, direction)), x + 110, h, white);
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

}
