package com.mikitellurium.turtlecharger.gui.element;

import com.mikitellurium.turtlecharger.blockentity.custom.TurtleChargerBlockEntity;
import com.mikitellurium.turtlecharger.util.DebugUtil;
import com.mikitellurium.turtlecharger.util.TurtleInfoUtil;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.core.Direction;
import net.minecraft.util.FastColor;

import java.util.Objects;

public class TurtleInfoElement extends GuiComponent {

    private final TurtleChargerBlockEntity charger;
    private Rect2i area;
//    private final int textureWidth = 34;
//    private final int textureHeight = 84;
    private final int width = 100;
    private final int height = 80;

    public TurtleInfoElement(TurtleChargerBlockEntity charger, int xPos, int yPos) {
        area = new Rect2i(xPos, yPos, width, height);
        this.charger = charger;
    }

    private final Font font = Minecraft.getInstance().font;
    private final int white = FastColor.ARGB32.color(255, 255, 255, 255);

    public void draw(PoseStack poseStack) {
        int x = area.getX();
        int y = area.getY();
        drawCenteredString(poseStack, font, "Name", x + 55, y + 1, white);
        drawString(poseStack, font, "Fuel Level", x + area.getWidth() - 15, y + 1, white);
        int h = y;
        for (Direction direction : Direction.values()) {
            h = h + 12;
            drawString(poseStack, font, getDirectionString(direction), alignString(getDirectionString(direction), x), h, white);
            drawCenteredString(poseStack, font, TurtleInfoUtil.getAdjacentTurtleName(charger, direction), x + 55, h,
                    TurtleInfoUtil.getAdjacentTurtleColor(charger, direction));
            drawCenteredString(poseStack, font, getFuelString(TurtleInfoUtil.getAdjacentTurtleFuel(charger, direction)), x + 110, h, white);
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

}
