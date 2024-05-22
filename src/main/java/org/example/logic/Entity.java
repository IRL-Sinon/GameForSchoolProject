package org.example.logic;

import java.awt.Rectangle;
import java.util.List;

public class Entity {
    protected Coordinates coord; // Coordinates object to store the position of the entity
    protected int width;         // Width of the entity
    protected int height;        // Height of the entity
    protected boolean alive;     // Status indicating if the entity is alive
    protected double gravity;
    protected double verticalSpeed;

    // Constructor to initialize the entity at a specific position with given width and height.
    public Entity(int x, int y, int width, int height) {
        this.coord = new Coordinates(x, y);
        this.width = width;
        this.height = height;
        this.alive = true;
        this.gravity = 2;
        this.verticalSpeed = 0;
    }

    // Gets the coordinates of the entity and returns it.
    public Coordinates getCoord() {
        return coord;
    }

    // Gets the width of the entity and returns it.
    public int getWidth() {
        return width;
    }

    // Gets the height of the entity and returns it.
    public int getHeight() {
        return height;
    }

    // Checks if the entity is alive.
    public boolean isAlive() {
        return alive;
    }

    // Kills the entity.
    public void die() {
        alive = false;
    }

    // Applies gravity to the entity.
    public void applyGravity() {
        verticalSpeed += gravity;
        coord.setY(coord.getY() + (int) verticalSpeed);
    }

    // Handles collision with platforms.
    public boolean handlePlatformCollision(List<Rectangle> platforms) {
        for (Rectangle platform : platforms) {
            if (coord.getX() + getWidth() > platform.x &&
                    coord.getX() < platform.x + platform.width) {
                if (coord.getY() + getHeight() > platform.y &&
                        coord.getY() + getHeight() <= platform.y + platform.height) {
                    coord.setY(platform.y - getHeight());
                    verticalSpeed = 0;
                    return true;
                } else if (coord.getY() > platform.y &&
                        coord.getY() < platform.y + platform.height &&
                        coord.getY() + getHeight() > platform.y + platform.height) {
                    coord.setY(platform.y + platform.height);
                }
            }
        }
        return false;
    }

    // Checks for collision with another entity
    public boolean checkCollision(Entity entity) {
        if (!isAlive() || !entity.isAlive()) return false;

        return getCoord().getX() + getWidth() >= entity.getCoord().getX() &&
                getCoord().getX() <= entity.getCoord().getX() + entity.getWidth() &&
                getCoord().getY() + getHeight() >= entity.getCoord().getY() &&
                getCoord().getY() <= entity.getCoord().getY() + entity.getHeight();
    }
}
