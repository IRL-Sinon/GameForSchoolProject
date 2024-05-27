package org.example.levels;

import org.example.logic.Enemy;
import org.example.logic.Lives;
import org.example.logic.Sonic;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TestLevel extends Level {
    private Sonic sonic;
    private List<Enemy> enemies;
    private Lives lives;
    private List<Rectangle> platforms;
    private int levelEndX;
    private final int playerStartX = 100;

    public TestLevel() {
        initializeLevel();
    }

    private void initializeLevel() {
        // Initialize level components
        platforms = new ArrayList<>();
        enemies = new ArrayList<>();
        lives = new Lives(3);
        levelEndX = 2000; // Extended length for a longer level

        // Add platforms
        platforms.add(new Rectangle(50, 350, 300, 50));
        platforms.add(new Rectangle(400, 300, 300, 50));
        platforms.add(new Rectangle(750, 250, 300, 50));
        platforms.add(new Rectangle(1100, 200, 300, 50));
        platforms.add(new Rectangle(1450, 150, 300, 50));

        // Add enemies
        enemies.add(new Enemy(500, 250, 50, 50, 100, 100));
        enemies.add(new Enemy(850, 200, 50, 50, 100, 100));
        enemies.add(new Enemy(1200, 150, 50, 50, 100, 100));

        // Initialize Sonic at the first platform
        Rectangle firstPlatform = platforms.get(0);
        sonic = new Sonic(firstPlatform.x, firstPlatform.y - 50, 50, 50, lives);
    }

    @Override
    public Sonic getSonic() {
        return sonic;
    }

    @Override
    public List<Enemy> getEnemies() {
        return enemies;
    }

    @Override
    public Lives getLives() {
        return lives;
    }

    @Override
    public List<Rectangle> getPlatforms() {
        return platforms;
    }

    @Override
    public int getLevelEndX() {
        return levelEndX;
    }

    @Override
    public void reset() {
        initializeLevel(); // Reset the level to its initial state
    }

    @Override
    public void spawnPlayer() {
        // Reset Sonic's position to the first platform
        Rectangle firstPlatform = platforms.get(0);
        sonic.setPosition(firstPlatform.x, firstPlatform.y - 50);
        sonic.resetState();
    }
}
