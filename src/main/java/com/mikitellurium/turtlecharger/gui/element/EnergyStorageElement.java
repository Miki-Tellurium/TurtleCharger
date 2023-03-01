package com.mikitellurium.turtlecharger.gui.element;

import com.mikitellurium.turtlecharger.TurtleChargerMod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.energy.EnergyStorage;

import java.util.List;

public class EnergyStorageElement extends GuiComponent {

    private static final ResourceLocation ENERGY_STORAGE_TEXTURE =
            new ResourceLocation(TurtleChargerMod.MOD_ID, "textures/gui/energy_storage.png");

    private final EnergyStorage energyStorage;
    private Rect2i area;
    private final int textureWidth = 34;
    private final int textureHeight = 84;
    private final int width = 16;
    private final int height = textureHeight;

    public EnergyStorageElement(EnergyStorage energyStorage, int xPos, int yPos) {
        area = new Rect2i(xPos, yPos, width, height);
        this.energyStorage = energyStorage;
    }

    public void draw(PoseStack poseStack) {
        RenderSystem.setShaderTexture(0, ENERGY_STORAGE_TEXTURE);
        blit(poseStack, area.getX(), area.getY(), 0, 0, this.width, this.height, textureWidth, textureHeight);
        drawEnergyLevel(poseStack);
    }

    private void drawEnergyLevel(PoseStack poseStack) {
        blit(poseStack, area.getX(), area.getY() + getEnergyLevel(),18, getEnergyLevel(), this.width, this.height - getEnergyLevel(), textureWidth, textureHeight);
    }
    // Get the pixel in the texture to start drawing at relative to the amount of stored energy
    private int getEnergyLevel() {
        return this.textureHeight - (int)Math.floor((area.getHeight()*(energyStorage.getEnergyStored()/(float)energyStorage.getMaxEnergyStored())));
    }
    // Energy tooltip
    public List<Component> getTooltips() {
        return List.of(Component.literal(energyStorage.getEnergyStored()+"/"+energyStorage.getMaxEnergyStored()+" FE"));
    }

    public Rect2i getArea() {
        return area;
    }

}
