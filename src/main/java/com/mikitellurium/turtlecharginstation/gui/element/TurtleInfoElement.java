package com.mikitellurium.turtlecharginstation.gui.element;

import com.mikitellurium.turtlecharginstation.blockentity.TurtleChargingStationBlockEntity;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class TurtleInfoElement {

    private final TurtleChargingStationBlockEntity station;
    private final Rect2i area;
    private final int width = 100;
    private final int height = 80;
    private final Map<Direction, TurtleData> turtleData = Util.make(new HashMap<>(), (map) -> {
        for(Direction direction : Direction.values()) {
            map.put(direction, new TurtleData());
        }
    });
    private final Font font = Minecraft.getInstance().font;
    private static final int white = FastColor.ARGB32.color(255, 255, 255, 255);

    public TurtleInfoElement(TurtleChargingStationBlockEntity station, int xPos, int yPos) {
        area = new Rect2i(xPos, yPos, width, height);
        this.station = station;
    }

    public void draw(GuiGraphics graphics) {
        this.turtleData.forEach((direction, data) -> data.updateData(this.station, direction));
        int x = area.getX();
        int y = area.getY();
        graphics.drawCenteredString(font, "Name", x + 55, y + 1, white);
        graphics.drawString(font, "Fuel Level", x + area.getWidth() - 14, y + 1, white);
        int h = y;
        for (Direction direction : Direction.values()) {
            TurtleData data = this.turtleData.get(direction);
            h = h + 12;
            String text = this.getDirectionString(direction);
            graphics.drawString(font, text, this.alignString(text, x), h, white);
            graphics.drawCenteredString(font, data.getTurtleName(), x + 55, h, data.getTurtleColor());
            graphics.drawCenteredString(font, this.getFuelString(data.getTurtleFuel()), x + 110, h, white);
        }
    }

    private String getDirectionString(Direction direction) {
        String name = direction.getName();
        String uppercase = Character.toUpperCase(name.charAt(0)) + name.substring(1) + ":";
        int leadingSpace = 6 - uppercase.length();
        return " ".repeat(leadingSpace) + uppercase;
    }

    private String getFuelString(int fuelLevel) {
        return fuelLevel == -1 ? "-" : String.valueOf(fuelLevel);
    }

    // Align text to the right
    private int alignString(String string, int xPos) {
        int length = string.trim().length();
        return xPos + (6 - length);
    }

    public Rect2i getArea() {
        return area;
    }

    private static class TurtleData {

        private String turtleName = "-";
        private int turtleColor = white;
        private int turtleFuel = -1;

        private void updateData(TurtleChargingStationBlockEntity station, Direction direction) {
            Optional<TurtleBlockEntity> optional = this.getAdjacentTurtle(station, direction);
            if (optional.isPresent()) {
                TurtleBlockEntity turtle = optional.get();
                this.turtleName = this.getTurtleName(turtle);
                this.turtleColor = this.getTurtleColor(turtle);
                this.turtleFuel = this.getTurtleFuel(turtle);
            } else {
                this.turtleName = "-";
                this.turtleColor = white;
                this.turtleFuel = -1;
            }
        }

        private String getTurtleName(TurtleBlockEntity turtle) {
            return turtle.hasCustomName() ? turtle.getLabel() : String.valueOf(turtle.getComputerID());
        }

        private int getTurtleColor(TurtleBlockEntity turtle) {
            return turtle.getColour() == -1 ? white : turtle.getColour();
        }

        private int getTurtleFuel(TurtleBlockEntity turtle) {
            return turtle.getAccess().getFuelLevel();
        }

        private Optional<TurtleBlockEntity> getAdjacentTurtle(TurtleChargingStationBlockEntity station, Direction direction) {
            BlockEntity blockEntity = station.getLevel().getBlockEntity(station.getBlockPos().relative(direction));
            return blockEntity instanceof TurtleBlockEntity turtle ? Optional.of(turtle) : Optional.empty();
        }

        public String getTurtleName() {
            return turtleName;
        }

        public int getTurtleColor() {
            return turtleColor;
        }

        public int getTurtleFuel() {
            return turtleFuel;
        }
    }

}
