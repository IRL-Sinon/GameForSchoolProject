package org.example.levels;

import org.example.logic.Sonic;
import org.example.logic.Enemy;
import org.example.logic.Lives;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class TestLevel {
    private List<Rectangle> platforms;
    private List<Enemy> enemies;
    private Sonic sonic;
    private Lives lives;

    public TestLevel() {
        setupLevel();
    }

    private void setupLevel() {
        // Setup platforms
        platforms = new ArrayList<>();
        platforms.add(new Rectangle(0, 400, 1920, 50)); // Platform 1
        platforms.add(new Rectangle(300, 300, 200, 50)); // Platform 2
        platforms.add(new Rectangle(600, 250, 150, 50)); // Platform 3
        platforms.add(new Rectangle(900, 350, 300, 50)); // Platform 4

        // Setup enemies
        enemies = new ArrayList<>();
        enemies.add(new Enemy(500, 200, 20, 20, 300, 300));
        enemies.add(new Enemy(750, 200, 20, 20, 300, 300));
        enemies.add(new Enemy(1000, 200, 20, 20, 300, 300));

        // Setup Sonic and lives
        lives = new Lives(3);
        sonic = new Sonic(50, 0, 10, 10, lives);
    }

    public List<Rectangle> getPlatforms() {
        return platforms;
    }

    public List<Enemy> getEnemies() {
        return enemies;
    }

    public Sonic getSonic() {
        return sonic;
    }

    public Lives getLives() {
        return lives;
    }
}
