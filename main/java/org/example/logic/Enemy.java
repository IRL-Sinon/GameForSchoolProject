package org.example.logic;

import java.util.ArrayList;
import java.util.List;

public class Enemy extends Entity {
    private int speed;
    private int detectionRangeX;
    private int detectionRangeY;
    private boolean alive;
    private boolean active;

    private static List<Enemy> enemies = new ArrayList<>();

    public Enemy(int x, int y, int width, int height, int detectionRangeX, int detectionRangeY) {
        super(x, y, width, height);
        this.speed = 3;
        this.detectionRangeX = detectionRangeX;
        this.detectionRangeY = detectionRangeY;
        this.alive = true;
        this.active = true;
        enemies.add(this);
    }

    public void update(Sonic sonic) {
        if (!alive) return;

        int distanceX = Math.abs(sonic.coord.x - coord.x);
        int distanceY = Math.abs(sonic.coord.y - coord.y);

        if (distanceX <= detectionRangeX && distanceY <= detectionRangeY) {
            if (sonic.coord.x > coord.x) {
                coord.x += speed;
            } else if (sonic.coord.x < coord.x) {
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

        return sonic.coord.x + sonic.width >= coord.x &&
                sonic.coord.x <= coord.x + width &&
                sonic.coord.y + sonic.height >= coord.y &&
                sonic.coord.y <= coord.y + height;
    }

    public void die() {
        alive = false;
        active = false;
        enemies.remove(this);
    }

    public static List<Enemy> getActiveEnemies() {
        List<Enemy> activeEnemies = new ArrayList<>();
        for (Enemy enemy : enemies) {
            if (enemy.active) {
                activeEnemies.add(enemy);
            }
        }
        return activeEnemies;
    }

    public boolean isAlive() {
        return alive;
    }
}
