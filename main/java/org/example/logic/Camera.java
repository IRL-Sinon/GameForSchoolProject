package org.example.logic;

public class Camera {
    private int x; // X-coordinate of the camera's position
    private int y; // Y-coordinate of the camera's position
    private double screenWidth; // Half the width of the screen
    private double screenHeight; // Half the height of the screen


     // Constructor to initialize the camera with the given screen dimensions.
    public Camera(int screenWidth, int screenHeight) {
        // Initialize screen dimensions to half the actual screen size for centering
        this.screenWidth = screenWidth / 2;
        this.screenHeight = screenHeight / 2;
        this.x = 0;
        this.y = 0;
    }


     //Updates the camera's position based on Sonic's position and follows him.
    public void update(Sonic sonic) {
        // Update the camera position to follow Sonic
        x = (int) (sonic.getCoord().getX() - screenWidth / 1.5);
        y = (int) (sonic.getCoord().getY() - screenHeight / 1.5);
    }


    //Gets the current X-coordinate of the camera and returns it.
    public int getX() {
        return x;
    }


     //Gets the current Y-coordinate of the camera and returns it.
    public int getY() {
        return y;
    }
}
