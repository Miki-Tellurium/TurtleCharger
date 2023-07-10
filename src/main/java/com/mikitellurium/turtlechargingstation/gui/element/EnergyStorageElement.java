package com.mikitellurium.turtlechargingstation.gui.element;

import com.mikitellurium.turtlechargingstation.util.SimpleSprite;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.text.Text;
import team.reborn.energy.api.base.SimpleEnergyStorage;

import java.util.List;

public class EnergyStorageElement {

    private final SimpleSprite sprite;
    private final SimpleEnergyStorage energyStorage;
    private final Rect2i area;

    public EnergyStorageElement(SimpleEnergyStorage energyStorage, SimpleSprite texture, int xPos, int yPos, int width, int height) {
        this.sprite = texture;
        this.energyStorage = energyStorage;
        area = new Rect2i(xPos, yPos, width, height);
    }

    public void draw(DrawContext context) {
        RenderSystem.setShaderTexture(0, sprite.getTexture());
        context.drawTexture(sprite.getTexture(), area.getX(), area.getY(), 0, 0, area.getWidth(), area.getHeight(),
                sprite.getWidth(), sprite.getHeight());
        drawEnergyLevel(context);
    }

    private void drawEnergyLevel(DrawContext context) {
        context.drawTexture(sprite.getTexture(), area.getX(), area.getY() + getEnergyLevel(),18, getEnergyLevel(),
                area.getWidth(), area.getHeight() - getEnergyLevel(), sprite.getWidth(), sprite.getHeight());
    }
    // Get the pixel in the texture to start drawing at relative to the amount of stored energy
    private int getEnergyLevel() {
        return sprite.getHeight() - (int)Math.floor((area.getHeight()*(energyStorage.getAmount()/(float)energyStorage.getCapacity())));
    }
    // Energy tooltip
    public List<Text> getTooltips() {
        return List.of(Text.literal(energyStorage.getAmount()+"/"+energyStorage.getCapacity()+" FE"));
    }

    public Rect2i getArea() {
        return area;
    }

}
