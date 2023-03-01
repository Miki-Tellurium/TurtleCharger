package com.mikitellurium.turtlecharginstation.gui;

import com.mikitellurium.turtlecharginstation.TurtleChargingStationMod;
import com.mikitellurium.turtlecharginstation.gui.element.EnergyStorageElement;
import com.mikitellurium.turtlecharginstation.gui.element.TurtleInfoElement;
import com.mikitellurium.turtlecharginstation.util.MouseUtil;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import java.util.Optional;

public class TurtleChargerGui extends AbstractContainerScreen<TurtleChargerMenu> {

    private static final ResourceLocation GUI_TEXTURE =
            new ResourceLocation(TurtleChargingStationMod.MOD_ID, "textures/gui/turtle_charging_station_gui.png");
    private EnergyStorageElement energyStorage;
    private TurtleInfoElement turtleInfo;

    public TurtleChargerGui(TurtleChargerMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        int xPos = (width - imageWidth) / 2;
        int yPos = (height - imageHeight) / 2;
        energyStorage = new EnergyStorageElement(menu.getBlockEntity().getEnergyStorage(), xPos + 8, yPos + 16);
        turtleInfo = new TurtleInfoElement(menu.getBlockEntity(), xPos + 30, yPos + 16);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        int xPos = (width - imageWidth) / 2;
        int yPos = (height - imageHeight) / 2;
        int textureWidth = 176;
        int textureHeight = 110;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
        blit(pPoseStack, xPos, yPos, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
        energyStorage.draw(pPoseStack);
        turtleInfo.draw(pPoseStack);
    }

    @Override
    public void render(PoseStack pPoseStack, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pPoseStack, pMouseX, pMouseY);
    }

    // This also remove player inventory title from gui
    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        this.font.draw(pPoseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, 4210752);
        int xPos = (width - imageWidth) / 2;
        int yPos = (height - imageHeight) / 2;
        renderEnergyAreaTooltips(pPoseStack, pMouseX, pMouseY, xPos, yPos);
    }

    private void renderEnergyAreaTooltips(PoseStack pPoseStack, int pMouseX, int pMouseY, int x, int y) {
        Rect2i area = energyStorage.getArea();
        if(MouseUtil.isAboveArea(pMouseX, pMouseY, area.getX(), area.getY(), area.getWidth(), area.getHeight())) {
            renderTooltip(pPoseStack, energyStorage.getTooltips(),
                    Optional.empty(), pMouseX - x, pMouseY - y);
        }
    }

}
