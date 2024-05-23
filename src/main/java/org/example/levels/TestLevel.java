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
    private int levelEndX;
    private int levelEndY;

    public TestLevel() {
        setupLevel();
    }

    private void setupLevel() {
        // Setup platforms
        platforms = new ArrayList<>();
        platforms.add(new Rectangle(0, 400, 5000, 50)); // Ground
        platforms.add(new Rectangle(300, 300, 200, 50)); // Platform 1
        platforms.add(new Rectangle(600, 250, 150, 50)); // Platform 2
        platforms.add(new Rectangle(900, 350, 300, 50)); // Platform 3
        platforms.add(new Rectangle(1300, 300, 200, 50)); // Platform 4
        platforms.add(new Rectangle(1600, 250, 150, 50)); // Platform 5
        platforms.add(new Rectangle(1900, 200, 300, 50)); // Platform 6
        platforms.add(new Rectangle(2300, 150, 200, 50)); // Platform 7
        platforms.add(new Rectangle(2600, 100, 150, 50)); // Platform 8
        platforms.add(new Rectangle(2900, 50, 200, 50));  // Platform 9

        // Setup enemies
        enemies = new ArrayList<>();
        enemies.add(new Enemy(1500, 280, 20, 20, 300, 100));
        enemies.add(new Enemy(1800, 230, 20, 20, 300, 100));
        enemies.add(new Enemy(2100, 180, 20, 20, 300, 100));
        enemies.add(new Enemy(2400, 130, 20, 20, 300, 100));
        enemies.add(new Enemy(2700, 80, 20, 20, 300, 100));
        enemies.add(new Enemy(3000, 30, 20, 20, 300, 100));
        enemies.add(new Enemy(3300, 80, 20, 20, 300, 100));
        enemies.add(new Enemy(3600, 130, 20, 20, 300, 100));
        enemies.add(new Enemy(3900, 180, 20, 20, 300, 100));

        // Setup Sonic and lives
        lives = new Lives(3);
        sonic = new Sonic(50, 380, 20, 20, lives);

        // Define the end of the level
        levelEndX = 5000; // X-coordinate for the end of the level
        levelEndY = 400;
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

    public int getLevelEndX() {
        return levelEndX;
    }
}
