package org.example.logic;

public class Entity {
    protected Coordinates coord; // Coordinates object to store the position of the entity
    protected int width;         // Width of the entity
    protected int height;        // Height of the entity
    protected boolean alive;     // Status indicating if the entity is alive


     //Constructor to initialize the entity at a specific position with given width and height.
    public Entity(int x, int y, int width, int height) {
        this.coord = new Coordinates(x, y);
        this.width = width;
        this.height = height;
        this.alive = true;
    }


    //Gets the coordinates of the entity and returns it.

    public Coordinates getCoord() {
        return coord;
    }


     //Gets the width of the entity and returns it.
    public int getWidth() {
        return width;
    }


    //Gets the height of the entity and returns it.
    public int getHeight() {
        return height;
    }

    /**
     * Checks if the entity is alive.
     * true if the entity is alive, false otherwise.
     */
    public boolean isAlive() {
        return alive;
    }
}
