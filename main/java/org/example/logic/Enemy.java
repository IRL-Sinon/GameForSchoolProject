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

        this.idleGif = loadAndResizeGif("enemyStanding.gif");
        this.walkingGif = loadAndResizeGif("enemyMoving.gif");
        this.moving = false;
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

    public void update(Sonic sonic) {
        if (!alive) return;

        int distanceX = Math.abs(sonic.getCoord().getX() - coord.getX());
        int distanceY = Math.abs(sonic.getCoord().getY() - coord.getY());

        if (distanceX <= detectionRangeX && distanceY <= detectionRangeY) {
            moving = true;
            if (sonic.getCoord().getX() > coord.getX()) {
                coord.setX(coord.getX() + speed);
            } else if (sonic.getCoord().getX() < coord.getX()) {
                coord.setX(coord.getX() - speed);
            }
        } else {
            moving = false;
        }

        if (checkCollision(sonic)) {
            if (sonic.isInBallForm() && sonic.getCoord().getY() + sonic.getHeight() <= coord.getY() + height) {
                die();
            } else if (!sonic.isInBallForm()) {
                int knockBackDirection = sonic.getCoord().getX() > coord.getX() ? 1 : -1;
                sonic.takeDamage(knockBackDirection);
            }
        }
    }

    public ImageIcon getCurrentGif() {
        return moving ? walkingGif : idleGif;
    }

    public boolean checkCollision(Sonic sonic) {
        if (!alive) return false;

        return sonic.getCoord().getX() + sonic.getWidth() >= coord.getX() &&
                sonic.getCoord().getX() <= coord.getX() + width &&
                sonic.getCoord().getY() + sonic.getHeight() >= coord.getY() &&
                sonic.getCoord().getY() <= coord.getY() + height;
    }

    public void die() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}
