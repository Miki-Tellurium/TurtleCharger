package com.mikitellurium.turtlechargingstation.gui.element;

import com.mikitellurium.telluriumforge.util.SimpleSprite;
import com.mikitellurium.turtlechargingstation.blockentity.TurtleChargingStationBlockEntity;
import com.mikitellurium.turtlechargingstation.util.FastId;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.text.Text;

import java.util.List;

public class EnergyStorageElement {

    private final SimpleSprite sprite = new SimpleSprite(FastId.modId("textures/gui/energy_storage.png"), 34, 84);
    private final TurtleChargingStationBlockEntity station;
    private final Rect2i area;

    public EnergyStorageElement(TurtleChargingStationBlockEntity station, int xPos, int yPos, int width, int height) {
        this.station = station;
        area = new Rect2i(xPos, yPos, width, height);
    }

    public void draw(DrawContext context) {
        RenderSystem.setShaderTexture(0, sprite.texture());
        context.drawTexture(sprite.texture(), area.getX(), area.getY(), 0, 0, area.getWidth(), area.getHeight(),
                sprite.width(), sprite.height());
        drawEnergyLevel(context);
    }

    private void drawEnergyLevel(DrawContext context) {
        context.drawTexture(sprite.texture(), area.getX(), area.getY() + getEnergyLevel(),18, getEnergyLevel(),
                area.getWidth(), area.getHeight() - getEnergyLevel(), sprite.width(), sprite.height());
    }
    // Get the pixel in the texture to start drawing at relative to the amount of stored energy
    private int getEnergyLevel() {
        return sprite.height() - (int)Math.floor((area.getHeight() * (station.getEnergy() / (float) station.getMaxEnergy())));
    }

    public List<Text> getTooltips() {
        return List.of(Text.literal(station.getEnergy() + "/" + station.getMaxEnergy()));
    }

    public Rect2i getArea() {
        return area;
    }

}
