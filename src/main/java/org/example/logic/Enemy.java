package org.example.logic;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;

public class Enemy extends Entity {
    private int speed;
    private int detectionRangeX;
    private int detectionRangeY;

    private ImageIcon idleGif;
    private ImageIcon walkingGif;

    private boolean moving;

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
        this.moving = false;
    }   // moving animation is setted to false so enemy starts in standing state

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


     //Updates the enemy's state based on the position of the Sonic character.
    public void update(Sonic sonic) {
        if (!alive) return;

        int distanceX = Math.abs(sonic.getCoord().getX() - coord.getX());
        int distanceY = Math.abs(sonic.getCoord().getY() - coord.getY());

        // Check if Sonic is within detection range
        if (distanceX <= detectionRangeX && distanceY <= detectionRangeY) {
            moving = true;
            // Move towards Sonic
            if (sonic.getCoord().getX() > coord.getX()) {
                coord.setX(coord.getX() + speed);
            } else if (sonic.getCoord().getX() < coord.getX()) {
                coord.setX(coord.getX() - speed);
            }
        } else {
            moving = false;
        }

        // Check for collision with Sonic
        if (checkCollision(sonic)) {
            if (sonic.isInBallForm() && sonic.getCoord().getY() + sonic.getHeight() <= coord.getY() + height) {
                die(); // Enemy dies if Sonic hits it while in ball form
            } else if (!sonic.isInBallForm()) {
                int knockBackDirection = sonic.getCoord().getX() > coord.getX() ? 1 : -1;
                sonic.takeDamage(knockBackDirection); // Sonic takes damage if not in ball form
            }
        }
    }


     // Returns the current GIF image to display based on the enemy's state.
    public ImageIcon getCurrentGif() {
        return moving ? walkingGif : idleGif;
    }


     // Checks if there is a collision between the enemy and Sonic.
    public boolean checkCollision(Sonic sonic) {
        if (!alive) return false;

        return sonic.getCoord().getX() + sonic.getWidth() >= coord.getX() &&
                sonic.getCoord().getX() <= coord.getX() + width &&
                sonic.getCoord().getY() + sonic.getHeight() >= coord.getY() &&
                sonic.getCoord().getY() <= coord.getY() + height;
    }


     //Kills the enemy.
    public void die() {
        alive = false;
    }


     //Checks if the enemy is still alive.
    public boolean isAlive() {
        return alive;
    }
}