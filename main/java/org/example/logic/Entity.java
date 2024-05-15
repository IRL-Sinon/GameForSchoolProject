package org.example.logic;

public class Entity {
    public Coordinates coord;
    public int width;
    public int height;

    public Entity(int x, int y) {
        this.coord = new Coordinates(x, y);
        this.width = 0;
        this.height = 0;
    }

    public Entity(int x, int y, int width, int height) {
        this.coord = new Coordinates(x, y);
        this.width = width;
        this.height = height;
    }
}