package com.mikitellurium.turtlechargingstation.gui.element;

import com.mikitellurium.turtlechargingstation.blockentity.TurtleChargingStationBlockEntity;
import dan200.computercraft.shared.turtle.blocks.TurtleBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.util.Util;
import net.minecraft.util.math.Direction;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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
    private final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
    private static final int white = Colors.WHITE;

    public TurtleInfoElement(TurtleChargingStationBlockEntity station, int xPos, int yPos) {
        area = new Rect2i(xPos, yPos, width, height);
        this.station = station;
    }

    public void draw(DrawContext context) {
        this.turtleData.forEach((direction, data) -> data.updateData(this.station, direction));
        int xPos = area.getX();
        int yPos = area.getY();
        Text name = Text.translatable("gui.turtlechargingstation.turtle_charging_station.turtle_name");
        Text fuelLevel = Text.translatable("gui.turtlechargingstation.turtle_charging_station.fuel_level");
        context.drawCenteredTextWithShadow(textRenderer, name, xPos + 95, yPos + 2, white);
        context.drawCenteredTextWithShadow(textRenderer, fuelLevel, xPos + 180, yPos + 2, white);
        int h = yPos + 2;
        for (Direction direction : Direction.values()) {
            TurtleData data = this.turtleData.get(direction);
            h = h + 12;
            String directionName = this.getDirectionName(direction);
            Text turtleName = data.getFormattedTurtleName();
            context.drawTextWithShadow(textRenderer, directionName, this.alignString(directionName, xPos - 8), h, white);
            context.drawCenteredTextWithShadow(textRenderer, turtleName, xPos + 95, h, white);
            context.drawCenteredTextWithShadow(textRenderer, this.getFuelString(data.getTurtleFuel()), xPos + 180, h, white);
        }
    }

    private String getDirectionName(Direction direction) {
        String name = Text.translatable("gui.turtlechargingstation.turtle_charging_station." + direction.getName()).getString();
        String withColon = name + ":";
        int leadingSpace = 7 - withColon.length();
        return " ".repeat(Math.max(leadingSpace, 0)) + withColon;
    }

    private String getFuelString(int fuelLevel) {
        return fuelLevel == -1 ? "-" : String.valueOf(fuelLevel);
    }

    // Align text to the right
    private int alignString(String string, int xPos) {
        int width = textRenderer.getWidth(string);
        return Math.max(xPos + (40 - width), xPos);
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
                this.turtleName = this.getTurtleLabel(turtle);
                this.turtleColor = this.getTurtleColor(turtle);
                this.turtleFuel = this.getTurtleFuel(turtle);
            } else {
                this.turtleName = "-";
                this.turtleColor = white;
                this.turtleFuel = -1;
            }
        }

        private String getTurtleLabel(TurtleBlockEntity turtle) {
            return turtle.hasCustomName() ? turtle.getLabel() : String.valueOf(turtle.getComputerID());
        }

        private int getTurtleColor(TurtleBlockEntity turtle) {
            return turtle.getColour() == -1 ? white : turtle.getColour();
        }

        private int getTurtleFuel(TurtleBlockEntity turtle) {
            return turtle.getAccess().getFuelLevel();
        }

        private Optional<TurtleBlockEntity> getAdjacentTurtle(TurtleChargingStationBlockEntity station, Direction direction) {
            BlockEntity blockEntity = station.getWorld().getBlockEntity(station.getPos().offset(direction));
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

        public Text getFormattedTurtleName() {
            return Text.literal(this.turtleName).setStyle(Style.EMPTY.withColor(this.turtleColor));
        }
    }

}
