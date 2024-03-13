package com.mikitellurium.turtlechargingstation.gui;

import com.mikitellurium.telluriumforge.util.MouseUtils;
import com.mikitellurium.turtlechargingstation.gui.element.EnergyStorageElement;
import com.mikitellurium.turtlechargingstation.gui.element.TurtleInfoElement;
import com.mikitellurium.turtlechargingstation.util.FastId;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.Rect2i;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.Optional;
import java.util.function.Supplier;

public class TurtleChargingStationScreen extends HandledScreen<TurtleChargingStationScreenHandler> {

    private static final Identifier GUI_TEXTURE = FastId.modId("textures/gui/turtle_charging_station_gui.png");
    private EnergyStorageElement energyStorage;
    private TurtleInfoElement turtleInfo;
    private final TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
    private final Supplier<Integer> xPos = () -> (this.width - this.backgroundWidth) / 2 - 60;
    private final Supplier<Integer> yPos = () -> (this.height - this.backgroundHeight) / 2;

    public TurtleChargingStationScreen(TurtleChargingStationScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        this.titleY = 5;
        this.energyStorage = new EnergyStorageElement(handler.getBlockEntity(), xPos.get() + 8,
                yPos.get() + 18, 16, 84);
        this.turtleInfo = new TurtleInfoElement(handler.getBlockEntity(), xPos.get() + 30, yPos.get() + 16);
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int textureWidth = 256;
        int textureHeight = 256;
        context.drawTexture(GUI_TEXTURE, xPos.get(), yPos.get(), 0, 0, textureWidth, textureHeight, textureWidth, textureHeight);
        energyStorage.draw(context);
        turtleInfo.draw(context);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context, mouseX, mouseY, delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    // This also remove player inventory title from gui
    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(textRenderer, this.title, this.titleX, this.titleY, 4210752, false);
        renderEnergyAreaTooltips(context, mouseX, mouseY);
    }

    private void renderEnergyAreaTooltips(DrawContext context, int mouseX, int mouseY) {
        Rect2i area = energyStorage.getArea();
        if(MouseUtils.isAboveArea(mouseX, mouseY, area.getX(), area.getY(), area.getWidth() - 2, area.getHeight() - 2)) {
            context.drawTooltip(this.textRenderer, energyStorage.getTooltips(), Optional.empty(),
                    mouseX - area.getX() - 55, mouseY - area.getY() + 15);
        }
    }

}
