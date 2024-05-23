package org.example.logic;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;
import java.util.List;
import java.awt.Rectangle;

public class Enemy extends Entity {
    private int speed;
    private int detectionRangeX;
    private int detectionRangeY;

    private ImageIcon idleGif;
    private ImageIcon walkingGif;

    private boolean movingLeft;
    private boolean movingRight;

    private int gifWidth = 50;
    private int gifHeight = 50;

    public Enemy(int x, int y, int width, int height, int detectionRangeX, int detectionRangeY) {
        super(x, y, width, height);
        this.speed = 3;
        this.detectionRangeX = detectionRangeX;
        this.detectionRangeY = detectionRangeY;

        // Load GIFs for idle and walking animations with set path
        this.idleGif = loadAndResizeGif("enemyStanding.gif");
        this.walkingGif = loadAndResizeGif("enemyMoving.gif");
        this.movingLeft = false;
        this.movingRight = false;
    }

    private ImageIcon loadAndResizeGif(String path) {
        URL imgURL = getClass().getClassLoader().getResource(path);
        if (imgURL != null) {
            ImageIcon originalGif = new ImageIcon(imgURL);
            Image image = originalGif.getImage().getScaledInstance(gifWidth, gifHeight, Image.SCALE_DEFAULT);
            return new ImageIcon(image);
        } else {
            System.err.println("Couldn't find file: " + path);
            return new ImageIcon(new byte[0]);
        }
    }

    // Updates the enemy's state based on the position of the Sonic character and platforms
    public void update(Sonic sonic, List<Enemy> enemies, List<Rectangle> platforms) {
        if (!alive) return;

        int distanceX = Math.abs(sonic.getCoord().getX() - coord.getX());
        int distanceY = Math.abs(sonic.getCoord().getY() - coord.getY());

        movingLeft = false;
        movingRight = false;

        if (distanceX <= detectionRangeX && distanceY <= detectionRangeY) {
            if (sonic.getCoord().getX() < coord.getX()) {
                movingLeft = true;
                coord.setX(coord.getX() - speed);
            } else if (sonic.getCoord().getX() > coord.getX()) {
                movingRight = true;
                coord.setX(coord.getX() + speed);
            }
        }

        // Apply gravity
        applyGravity();

        // Check for collision with platforms
        handlePlatformCollision(platforms);
    }

    // Returns the current GIF image to display based on the enemy's state
    public ImageIcon getCurrentGif() {
        return movingLeft || movingRight ? walkingGif : idleGif;
    }
}
