package com.mikitellurium.turtlecharginstation.gui;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.gui.element.EnergyStorageElement;
import com.mikitellurium.turtlecharginstation.gui.element.TurtleInfoElement;
import com.mikitellurium.turtlecharginstation.util.MouseUtil;
import com.mikitellurium.turtlecharginstation.util.SimpleSprite;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class TurtleChargingStationGui extends AbstractContainerScreen<TurtleChargingStationMenu> {

    private static final ResourceLocation GUI_TEXTURE =
            new ResourceLocation(TurtleChargingStationMod.MOD_ID, "textures/gui/turtle_charging_station_gui.png");
    private static final ResourceLocation ENERGY_STORAGE_TEXTURE =
            new ResourceLocation(TurtleChargingStationMod.MOD_ID, "textures/gui/energy_storage.png");
    private final SimpleSprite energyStorageTexture = new SimpleSprite(ENERGY_STORAGE_TEXTURE, 34, 84);
    private EnergyStorageElement energyStorage;
    private TurtleInfoElement turtleInfo;

    public TurtleChargingStationGui(TurtleChargingStationMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        int xPos = (width - imageWidth) / 2;
        int yPos = (height - imageHeight) / 2;
        this.titleLabelY = this.titleLabelY - 1;
        energyStorage = new EnergyStorageElement(menu.getBlockEntity().getEnergyStorage(), energyStorageTexture,
                xPos + 8, yPos + 15, 16, 84);
        turtleInfo = new TurtleInfoElement(menu.getBlockEntity(), xPos + 30, yPos + 16);
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int xPos = (width - imageWidth) / 2;
        int yPos = (height - imageHeight) / 2;
        int textureWidth = 176;
        int textureHeight = 110;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        graphics.blit(GUI_TEXTURE, xPos, yPos, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
        energyStorage.draw(graphics);
        turtleInfo.draw(graphics);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics, mouseX, mouseY, partialTick);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }

    // This also remove player inventory title from gui
    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        graphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, 4210752, false);
        int xPos = (width - imageWidth) / 2;
        int yPos = (height - imageHeight) / 2;
        renderEnergyAreaTooltips(graphics, mouseX, mouseY, xPos, yPos);
    }

    private void renderEnergyAreaTooltips(GuiGraphics graphics, int mouseX, int mouseY, int xPos, int yPos) {
        Rect2i area = energyStorage.getArea();
        if(MouseUtil.isAboveArea(mouseX, mouseY, area.getX(), area.getY(), area.getWidth(), area.getHeight())) {
            graphics.renderTooltip(this.font, energyStorage.getTooltips(),
                    Optional.empty(), mouseX - xPos, mouseY - yPos);
        }
    }

}
