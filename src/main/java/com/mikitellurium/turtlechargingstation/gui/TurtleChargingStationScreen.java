package com.mikitellurium.turtlechargingstation.gui;

import com.mikitellurium.turtlechargingstation.TurtleChargingStationMod;
import com.mikitellurium.turtlechargingstation.gui.element.EnergyStorageElement;
import com.mikitellurium.turtlechargingstation.gui.element.TurtleInfoElement;
import com.mikitellurium.turtlechargingstation.util.MouseUtil;
import com.mikitellurium.turtlechargingstation.util.SimpleSprite;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class TurtleChargingStationScreen extends HandledScreen<TurtleChargingStationScreenHandler> {

    private static final Identifier GUI_TEXTURE =
            new Identifier(TurtleChargingStationMod.MOD_ID, "textures/gui/turtle_charging_station_gui.png");
    private static final Identifier ENERGY_STORAGE_TEXTURE =
            new Identifier(TurtleChargingStationMod.MOD_ID, "textures/gui/energy_storage.png");
    private final SimpleSprite energyStorageTexture = new SimpleSprite(ENERGY_STORAGE_TEXTURE, 34, 84);
    private EnergyStorageElement energyStorage;
    private TurtleInfoElement turtleInfo;
    private final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

    public TurtleChargingStationScreen(TurtleChargingStationScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        int xPos = (width - backgroundWidth) / 2;
        int yPos = (height - backgroundHeight) / 2;
        this.titleY = this.titleY - 1;
        energyStorage = new EnergyStorageElement(handler.getBlockEntity().getEnergyStorage(), energyStorageTexture,
                xPos + 8, yPos + 15, 16, 84);
        turtleInfo = new TurtleInfoElement(handler.getBlockEntity(), xPos + 30, yPos + 16);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int xPos = (width - backgroundWidth) / 2;
        int yPos = (height - backgroundHeight) / 2;
        int textureWidth = 176;
        int textureHeight = 110;
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        context.drawTexture(GUI_TEXTURE, xPos, yPos, 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
        energyStorage.draw(context);
        turtleInfo.draw(context);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    // This also remove player inventory title from gui
    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(textRenderer, this.title, this.titleX, this.titleY, 4210752, false);
        int xPos = (width - backgroundWidth) / 2;
        int yPos = (height - backgroundHeight) / 2;
        renderEnergyAreaTooltips(context, mouseX, mouseY, xPos, yPos);
    }

    private void renderEnergyAreaTooltips(DrawContext context, int pMouseX, int pMouseY, int x, int y) {
        Rect2i area = energyStorage.getArea();
        if(MouseUtil.isAboveArea(pMouseX, pMouseY, area.getX(), area.getY(), area.getWidth(), area.getHeight())) {
            context.drawTooltip(textRenderer, energyStorage.getTooltips(), Optional.empty(),
                    pMouseX - x, pMouseY - y);
        }
    }

}
