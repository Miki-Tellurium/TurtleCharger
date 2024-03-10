package com.mikitellurium.turtlechargingstation.testapi;

import net.minecraft.util.Identifier;

public class SimpleSprite {

    private final Identifier texture;
    private final int textureWidth;
    private final int textureHeight;

    public SimpleSprite(Identifier texture, int width, int height) {
        this.texture = texture;
        this.textureWidth = width;
        this.textureHeight = height;
    }

    public Identifier getTexture() {
        return texture;
    }

    public int getWidth() {
        return textureWidth;
    }

    public int getHeight() {
        return textureHeight;
    }

}
