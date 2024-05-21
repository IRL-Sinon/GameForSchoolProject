package org.example.logic;

public class Entity {
    protected Coordinates coord;
    protected int width;
    protected int height;
    protected boolean alive;

    public Entity(int x, int y) {
        this.coord = new Coordinates(x, y);
        this.width = 0;
        this.height = 0;
        this.alive = true;
    }

    public Entity(int x, int y, int width, int height) {
        this.coord = new Coordinates(x, y);
        this.width = width;
        this.height = height;
        this.alive = true;
    }

    public Coordinates getCoord() {
        return coord;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean isAlive() {
        return alive;
    }
}
