package org.example.logic;

public class Enemy extends Entity {
    private int speed;
    private int detectionRangeX;
    private int detectionRangeY;

    public Enemy(int x, int y, int width, int height, int detectionRangeX, int detectionRangeY) {
        super(x, y, width, height);
        this.speed = 3;
        this.detectionRangeX = detectionRangeX;
        this.detectionRangeY = detectionRangeY;
    }

    public void update(Sonic sonic) {
        if (!alive) return;

        int distanceX = Math.abs(sonic.getCoord().getX() - coord.getX());
        int distanceY = Math.abs(sonic.getCoord().getY() - coord.getY());

        if (distanceX <= detectionRangeX && distanceY <= detectionRangeY) {
            if (sonic.getCoord().getX() > coord.getX()) {
                coord.setX(coord.getX() + speed);
            } else if (sonic.getCoord().getX() < coord.getX()) {
                coord.setX(coord.getX() - speed);
            }
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
