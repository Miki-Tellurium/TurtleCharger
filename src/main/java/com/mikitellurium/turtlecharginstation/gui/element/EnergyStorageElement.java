package com.mikitellurium.turtlecharginstation.gui.element;

import com.mikitellurium.turtlecharginstation.blockentity.TurtleChargingStationBlockEntity;
import com.mikitellurium.turtlecharginstation.util.SimpleSprite;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.energy.EnergyStorage;

import java.util.List;

public class EnergyStorageElement {

    private final SimpleSprite sprite;
    private final TurtleChargingStationBlockEntity station;
    private final Rect2i area;

    public EnergyStorageElement(TurtleChargingStationBlockEntity station, SimpleSprite texture, int xPos, int yPos, int width, int height) {
        this.sprite = texture;
        this.station = station;
        area = new Rect2i(xPos, yPos, width, height);
    }

    public void draw(GuiGraphics graphics) {
        RenderSystem.setShaderTexture(0, sprite.getTexture());
        graphics.blit(sprite.getTexture(), area.getX(), area.getY(), 0, 0, area.getWidth(), area.getHeight(),
                sprite.getWidth(), sprite.getHeight());
        drawEnergyLevel(graphics);
    }

    private void drawEnergyLevel(GuiGraphics graphics) {
        graphics.blit(sprite.getTexture(), area.getX(), area.getY() + getEnergyLevel(),18, getEnergyLevel(),
                area.getWidth(), area.getHeight() - getEnergyLevel(), sprite.getWidth(), sprite.getHeight());
    }
    // Get the pixel in the texture to start drawing at relative to the amount of stored energy
    private int getEnergyLevel() {
        return sprite.getHeight() - (int)Math.floor((area.getHeight() * (station.getEnergy() / (float)station.getMaxEnergy())));
    }
    // Energy tooltip
    public List<Component> getTooltips() {
        return List.of(Component.literal(station.getEnergy() + "/" + station.getMaxEnergy() + " FE"));
    }

    public Rect2i getArea() {
        return area;
    }

}
