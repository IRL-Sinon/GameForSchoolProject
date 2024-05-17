package org.example.logic;

public class Enemy extends Entity {
    private int speed;
    private int detectionRangeX;
    private int detectionRangeY;
    private boolean alive;
    private boolean active;

    public Enemy(int x, int y, int width, int height, int detectionRangeX, int detectionRangeY) {
        super(x, y, width, height);
        this.speed = 3;
        this.detectionRangeX = detectionRangeX;
        this.detectionRangeY = detectionRangeY;
        this.alive = true;
        this.active = true;
    }

    public void update(Sonic sonic) {
        if (!alive) return;

        int distanceX = Math.abs(sonic.getCoord().x - coord.x);
        int distanceY = Math.abs(sonic.getCoord().y - coord.y);

        if (distanceX <= detectionRangeX && distanceY <= detectionRangeY) {
            if (sonic.getCoord().x > coord.x) {
                coord.x += speed;
            } else if (sonic.getCoord().x < coord.x) {
                coord.x -= speed;
            }
        }

        if (checkCollision(sonic)) {
            if (sonic.isInBallForm()) {
                die();
            }
        }
    }

    public boolean checkCollision(Sonic sonic) {
        if (!alive) return false;

        return sonic.getCoord().x + sonic.getWidth() >= coord.x &&
                sonic.getCoord().x <= coord.x + width &&
                sonic.getCoord().y + sonic.getHeight() >= coord.y &&
                sonic.getCoord().y <= coord.y + height;
    }

    public void die() {
        alive = false;
        active = false;
    }
}
