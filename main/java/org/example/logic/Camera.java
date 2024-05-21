package org.example.logic;

public class Camera {
    private int x;
    private int y;
    private int screenWidth;
    private int screenHeight;

    public Camera(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth / 2;
        this.screenHeight = screenHeight / 2;
        this.x = 0;
        this.y = 0;
    }

    public void update(Sonic sonic) {
        x = sonic.getCoord().getX() - screenWidth / 2;
        y = sonic.getCoord().getY() - screenHeight / 2;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
