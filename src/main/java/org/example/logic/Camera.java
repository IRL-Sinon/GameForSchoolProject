package org.example.logic;

public class Camera {
    private int x;
    private int y;
    private int screenWidth;
    private int screenHeight;

    public Camera(int screenWidth, int screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.x = 0;
        this.y = 0;
    }

    public void update(Sonic sonic) {
        // Always follow Sonic's x-coordinate
        x = sonic.getCoord().getX() - screenWidth / 2;

        // Follow Sonic's y-coordinate but place him lower on the screen
        y = sonic.getCoord().getY() - screenHeight / 2 + 200; // Adjust 200 to position Sonic lower
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
