package com.mikitellurium.turtlecharginstation.util;

public class MouseUtil {

    public static boolean isAboveArea(double mouseX, double mouseY, int xPos, int yPos, int sizeX, int sizeY) {
        return (mouseX >= xPos && mouseX <= xPos + sizeX) && (mouseY >= yPos && mouseY <= yPos + sizeY);
    }

}
