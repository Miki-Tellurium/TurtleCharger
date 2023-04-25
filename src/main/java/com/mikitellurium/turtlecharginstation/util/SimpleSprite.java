package com.mikitellurium.turtlecharginstation.util;

import net.minecraft.resources.ResourceLocation;

public class SimpleSprite {

    private final ResourceLocation texture;
    private final int textureWidth;
    private final int textureHeight;

    public SimpleSprite(ResourceLocation texture, int width, int height) {
        this.texture = texture;
        this.textureWidth = width;
        this.textureHeight = height;
    }

    public ResourceLocation getTexture() {
        return texture;
    }

    public int getWidth() {
        return textureWidth;
    }

    public int getHeight() {
        return textureHeight;
    }

}
