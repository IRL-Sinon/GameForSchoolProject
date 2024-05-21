package org.example.logic;

import java.awt.geom.Arc2D;

public class Camera {
    private int x;
    private int y;
    private double screenWidth;
    private double screenHeight;

    public Camera(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth / 2;
        this.screenHeight = screenHeight / 2;
        this.x = 0;
        this.y = 0;
    }

    public void update(Sonic sonic) {
        x = (int) (sonic.getCoord().getX() - screenWidth / 1.5);
        y = (int) (sonic.getCoord().getY() - screenHeight / 1.5);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
