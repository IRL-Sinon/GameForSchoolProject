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

public class LevelTwo extends Level {
    private Sonic sonic;
    private List<Enemy> enemies;
    private Lives lives;
    private List<Rectangle> platforms;
    private int levelEndX;
    private Clip backgroundClip;
    private Image backgroundImage;

    public LevelTwo() {
        initializeLevel();
    }

    private void initializeLevel() {
        platforms = new ArrayList<>();
        enemies = new ArrayList<>();
        lives = new Lives(3);
        levelEndX = 7000; // Length adjusted to match a typical Sonic level

        // Define platform positions and sizes based on the provided image
        // Top area platforms
        platforms.add(new Rectangle(0, 350, 300, 50));    // Starting platform
        platforms.add(new Rectangle(350, 300, 200, 50));  // Platform 1
        platforms.add(new Rectangle(600, 250, 150, 50));  // Platform 2
        platforms.add(new Rectangle(850, 200, 200, 50));  // Platform 3
        platforms.add(new Rectangle(1100, 150, 250, 50)); // Platform 4

        // Middle area platforms
        platforms.add(new Rectangle(1400, 250, 200, 50)); // Platform 5
        platforms.add(new Rectangle(1600, 300, 150, 50)); // Platform 6
        platforms.add(new Rectangle(1800, 350, 200, 50)); // Platform 7
        platforms.add(new Rectangle(2000, 250, 250, 50)); // Platform 8
        platforms.add(new Rectangle(2300, 200, 300, 50)); // Platform 9

        // Bottom area platforms (cave area)
        platforms.add(new Rectangle(2600, 450, 200, 50)); // Platform 10
        platforms.add(new Rectangle(2800, 400, 250, 50)); // Platform 11
        platforms.add(new Rectangle(3050, 350, 300, 50)); // Platform 12
        platforms.add(new Rectangle(3350, 300, 200, 50)); // Platform 13
        platforms.add(new Rectangle(3550, 250, 250, 50)); // Platform 14

        // Additional platforms to ensure player can reach the end
        platforms.add(new Rectangle(1700, 200, 150, 50)); // Platform 15
        platforms.add(new Rectangle(2500, 300, 200, 50)); // Platform 16
        platforms.add(new Rectangle(3700, 150, 200, 50)); // Platform 17
        platforms.add(new Rectangle(4300, 250, 250, 50)); // Platform 18
        platforms.add(new Rectangle(4600, 200, 200, 50)); // Platform 19
        platforms.add(new Rectangle(4900, 150, 150, 50)); // Platform 20

        // End area platforms
        platforms.add(new Rectangle(5200, 100, 300, 50)); // Platform 21
        platforms.add(new Rectangle(5550, 200, 250, 50)); // Platform 22
        platforms.add(new Rectangle(5800, 150, 200, 50)); // Platform 23
        platforms.add(new Rectangle(6050, 100, 2000, 50)); // End platform

        // Initialize Sonic at the starting position
        sonic = new Sonic(150, 300, 50, 50, lives);  // Position Sonic on the first platform

        // Add enemies with specific positions, sizes, and detection ranges
        enemies.add(new Enemy(500, 250, 50, 50, 200, 150));  // Enemy 1
        enemies.add(new Enemy(1200, 100, 50, 50, 150, 100)); // Enemy 2
        enemies.add(new Enemy(1900, 300, 50, 50, 250, 150)); // Enemy 3
        enemies.add(new Enemy(2400, 150, 50, 50, 200, 100)); // Enemy 4
        enemies.add(new Enemy(2700, 400, 50, 50, 150, 100)); // Enemy 5
        enemies.add(new Enemy(3200, 250, 50, 50, 250, 150)); // Enemy 6
        enemies.add(new Enemy(3750, 150, 50, 50, 200, 100)); // Enemy 7
        enemies.add(new Enemy(4250, 100, 50, 50, 150, 100)); // Enemy 8
        enemies.add(new Enemy(4500, 200, 50, 50, 200, 150)); // Enemy 9
        enemies.add(new Enemy(4750, 150, 50, 50, 250, 100)); // Enemy 10
        enemies.add(new Enemy(5100, 100, 50, 50, 150, 100)); // Enemy 11

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
