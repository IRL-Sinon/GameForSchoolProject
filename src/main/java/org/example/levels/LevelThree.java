package org.example.levels;

import org.example.logic.Enemy;
import org.example.logic.Lives;
import org.example.logic.Sonic;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LevelThree extends Level {
    private Sonic sonic;
    private List<Enemy> enemies;
    private Lives lives;
    private List<Rectangle> platforms;
    private int levelEndX;
    private Clip backgroundClip;
    private Image backgroundImage;

    public LevelThree() {
        initializeLevel();
    }

    private void initializeLevel() {
        platforms = new ArrayList<>();
        enemies = new ArrayList<>();
        lives = new Lives(3);
        levelEndX = 8100; // Length adjusted for a hard level

        // Adding challenging platforms
        platforms.add(new Rectangle(0, 450, 300, 50));    // Starting platform
        platforms.add(new Rectangle(400, 400, 200, 50));  // Platform 1
        platforms.add(new Rectangle(700, 350, 200, 50));  // Platform 2
        platforms.add(new Rectangle(1100, 300, 300, 50)); // Platform 3
        platforms.add(new Rectangle(1600, 250, 200, 50)); // Platform 4
        platforms.add(new Rectangle(1900, 200, 250, 50)); // Platform 5
        platforms.add(new Rectangle(2200, 150, 200, 50)); // Platform 6
        platforms.add(new Rectangle(2500, 100, 200, 50)); // Platform 7

        // Vertical challenge
        platforms.add(new Rectangle(2800, 150, 200, 50)); // Platform 8
        platforms.add(new Rectangle(2800, 250, 200, 50)); // Platform 9
        platforms.add(new Rectangle(2800, 350, 200, 50)); // Platform 10

        // Horizontal stretch
        platforms.add(new Rectangle(3100, 400, 300, 50)); // Platform 11
        platforms.add(new Rectangle(3500, 350, 250, 50)); // Platform 12
        platforms.add(new Rectangle(3800, 300, 300, 50)); // Platform 13
        platforms.add(new Rectangle(4200, 250, 200, 50)); // Platform 14
        platforms.add(new Rectangle(4500, 200, 300, 50)); // Platform 15
        platforms.add(new Rectangle(4900, 150, 200, 50)); // Platform 16

        // Final stretch
        platforms.add(new Rectangle(5200, 300, 300, 50)); // Platform 17
        platforms.add(new Rectangle(5600, 350, 200, 50)); // Platform 18
        platforms.add(new Rectangle(5900, 400, 300, 50)); // Platform 19
        platforms.add(new Rectangle(6300, 450, 200, 50)); // Platform 20
        platforms.add(new Rectangle(6600, 400, 300, 50)); // Platform 21
        platforms.add(new Rectangle(7000, 350, 200, 50)); // Platform 22
        platforms.add(new Rectangle(7300, 300, 300, 50)); // Platform 23
        platforms.add(new Rectangle(7700, 250, 200, 50)); // Platform 24
        platforms.add(new Rectangle(8000, 200, 300, 50)); // End platform

        // Initialize Sonic at the starting position
        sonic = new Sonic(150, 400, 50, 50, lives);  // Position Sonic on the first platform

        // Adding enemies with specific positions, sizes, and detection ranges
        enemies.add(new Enemy(500, 400 - 50, 50, 50, 200, 100));  // Enemy 1
        enemies.add(new Enemy(800, 350 - 50, 50, 50, 200, 150));  // Enemy 2
        enemies.add(new Enemy(1200, 300 - 50, 50, 50, 200, 150)); // Enemy 3
        enemies.add(new Enemy(1500, 250 - 50, 50, 50, 200, 150)); // Enemy 4
        enemies.add(new Enemy(1800, 200 - 50, 50, 50, 200, 150)); // Enemy 5
        enemies.add(new Enemy(2100, 150 - 50, 50, 50, 200, 150)); // Enemy 6
        enemies.add(new Enemy(2400, 100 - 50, 50, 50, 200, 150)); // Enemy 7
        enemies.add(new Enemy(2700, 350 - 50, 50, 50, 200, 150)); // Enemy 8
        enemies.add(new Enemy(3000, 400 - 50, 50, 50, 200, 150)); // Enemy 9
        enemies.add(new Enemy(3400, 350 - 50, 50, 50, 200, 150)); // Enemy 10
        enemies.add(new Enemy(3700, 300 - 50, 50, 50, 200, 150)); // Enemy 11
        enemies.add(new Enemy(4100, 250 - 50, 50, 50, 200, 150)); // Enemy 12
        enemies.add(new Enemy(4400, 200 - 50, 50, 50, 200, 150)); // Enemy 13
        enemies.add(new Enemy(4700, 150 - 50, 50, 50, 200, 150)); // Enemy 14
        enemies.add(new Enemy(5000, 100 - 50, 50, 50, 200, 150)); // Enemy 15
        enemies.add(new Enemy(5300, 300 - 50, 50, 50, 200, 150)); // Enemy 16
        enemies.add(new Enemy(5700, 350 - 50, 50, 50, 200, 150)); // Enemy 17
        enemies.add(new Enemy(6000, 400 - 50, 50, 50, 200, 150)); // Enemy 18
        enemies.add(new Enemy(6400, 450 - 50, 50, 50, 200, 150)); // Enemy 19
        enemies.add(new Enemy(6700, 400 - 50, 50, 50, 200, 150)); // Enemy 20
        enemies.add(new Enemy(7100, 350 - 50, 50, 50, 200, 150)); // Enemy 21
        enemies.add(new Enemy(7400, 300 - 50, 50, 50, 200, 150)); // Enemy 22
        enemies.add(new Enemy(7800, 250 - 50, 50, 50, 200, 150)); // Enemy 23
        enemies.add(new Enemy(8100, 200 - 50, 50, 50, 200, 150)); // Enemy 24

        // Load background image
        URL imgURL = getClass().getClassLoader().getResource("Background.png");
        if (imgURL != null) {
            backgroundImage = new ImageIcon(imgURL).getImage();
        } else {
            System.err.println("Couldn't find file: Background.png");
        }
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
        stopBackgroundMusic();
        initializeLevel();
    }

    @Override
    public void spawnPlayer() {
        Rectangle firstPlatform = platforms.get(0);
        sonic.setPosition(firstPlatform.x, firstPlatform.y - 50);
        sonic.resetState();
    }

    @Override
    public Image getBackgroundImage() {
        return backgroundImage;
    }

    public void startBackgroundMusic(String filePath) {
        try {
            URL soundURL = getClass().getClassLoader().getResource(filePath);
            if (soundURL != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
                backgroundClip = AudioSystem.getClip();
                backgroundClip.open(audioStream);
                backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
                backgroundClip.start();
            } else {
                System.err.println("Couldn't find file: " + filePath);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundClip != null) {
            backgroundClip.stop();
            backgroundClip.close();
        }
    }
}
