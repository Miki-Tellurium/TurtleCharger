package com.mikitellurium.turtlecharginstation.gui.element;

import com.mikitellurium.turtlecharginstation.util.SimpleSprite;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraftforge.energy.EnergyStorage;

import java.util.List;

public class EnergyStorageElement extends GuiComponent {

    private final SimpleSprite sprite;
    private final EnergyStorage energyStorage;
    private final Rect2i area;

    public EnergyStorageElement(EnergyStorage energyStorage, SimpleSprite texture, int xPos, int yPos, int width, int height) {
        this.sprite = texture;
        this.energyStorage = energyStorage;
        area = new Rect2i(xPos, yPos, width, height);
    }

    public void draw(PoseStack poseStack) {
        RenderSystem.setShaderTexture(0, sprite.getTexture());
        blit(poseStack, area.getX(), area.getY(), 0, 0, area.getWidth(), area.getHeight(),
                sprite.getWidth(), sprite.getHeight());
        drawEnergyLevel(poseStack);
    }

    private void drawEnergyLevel(PoseStack poseStack) {
        blit(poseStack, area.getX(), area.getY() + getEnergyLevel(),18, getEnergyLevel(),
                area.getWidth(), area.getHeight() - getEnergyLevel(), sprite.getWidth(), sprite.getHeight());
    }
    // Get the pixel in the texture to start drawing at relative to the amount of stored energy
    private int getEnergyLevel() {
        return sprite.getHeight() - (int)Math.floor((area.getHeight()*(energyStorage.getEnergyStored()/(float)energyStorage.getMaxEnergyStored())));
    }
    // Energy tooltip
    public List<Component> getTooltips() {
        return List.of(Component.literal(energyStorage.getEnergyStored()+"/"+energyStorage.getMaxEnergyStored()+" FE"));
    }

    public Rect2i getArea() {
        return area;
    }

}
