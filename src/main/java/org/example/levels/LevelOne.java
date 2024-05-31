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

public class LevelOne extends Level {
    private Sonic sonic;
    private List<Enemy> enemies;
    private Lives lives;
    private List<Rectangle> platforms;
    private int levelEndX;
    private Clip backgroundClip;
    private Image backgroundImage;

    public LevelOne() {
        initializeLevel();
    }

    private void initializeLevel() {
        platforms = new ArrayList<>();
        enemies = new ArrayList<>();
        lives = new Lives(3);
        levelEndX = 10000;

        // Add platforms
        platforms.add(new Rectangle(100, 500, 200, 50));  // Starting platform
        platforms.add(new Rectangle(400, 450, 200, 50));
        platforms.add(new Rectangle(700, 400, 200, 50));
        platforms.add(new Rectangle(1000, 350, 200, 50));
        platforms.add(new Rectangle(1300, 300, 200, 50));
        platforms.add(new Rectangle(1600, 350, 200, 50));
        platforms.add(new Rectangle(1900, 400, 200, 50));
        platforms.add(new Rectangle(2200, 450, 200, 50));
        platforms.add(new Rectangle(2500, 350, 200, 200)); // Intermediate platform
        platforms.add(new Rectangle(2800, 450, 200, 50));
        platforms.add(new Rectangle(3100, 400, 200, 50));
        platforms.add(new Rectangle(3400, 350, 200, 50));
        platforms.add(new Rectangle(3700, 300, 200, 50));
        platforms.add(new Rectangle(4000, 250, 200, 50));
        platforms.add(new Rectangle(4300, 300, 200, 50));
        platforms.add(new Rectangle(4600, 350, 200, 200));
        platforms.add(new Rectangle(4900, 400, 200, 50));
        platforms.add(new Rectangle(5200, 450, 200, 50));
        platforms.add(new Rectangle(5500, 500, 200, 50));
        platforms.add(new Rectangle(5800, 450, 200, 50));
        platforms.add(new Rectangle(6100, 400, 200, 50));
        platforms.add(new Rectangle(6400, 350, 200, 200));
        platforms.add(new Rectangle(6700, 300, 200, 50));
        platforms.add(new Rectangle(7000, 250, 200, 50));
        platforms.add(new Rectangle(7300, 300, 200, 50));
        platforms.add(new Rectangle(7600, 350, 200, 200));
        platforms.add(new Rectangle(7900, 400, 200, 50));
        platforms.add(new Rectangle(8200, 450, 200, 50));
        platforms.add(new Rectangle(8500, 500, 200, 50)); // Ending platform

        // Add ground platform to prevent falling death
        platforms.add(new Rectangle(0, 550, 10000, 50));  // Ground platform

        // Add enemies
        enemies.add(new Enemy(500, 400, 50 ,50, 100 , 100));
        enemies.add(new Enemy(800, 350, 50 ,50, 100 , 100));
        enemies.add(new Enemy(1100, 300, 50 ,50, 100 , 100));
        enemies.add(new Enemy(1400, 250, 50 ,50, 100 , 100));
        enemies.add(new Enemy(1700, 300, 50 ,50, 100 , 100));
        enemies.add(new Enemy(2000, 350, 50 ,50, 100 , 100));
        enemies.add(new Enemy(2300, 400, 50 ,50, 100 , 100));
        enemies.add(new Enemy(2600, 450, 50 ,50, 100 , 100));
        enemies.add(new Enemy(2900, 400, 50 ,50, 100 , 100));
        enemies.add(new Enemy(3200, 350, 50 ,50, 100 , 100));
        enemies.add(new Enemy(3500, 300, 50 ,50, 100 , 100));
        enemies.add(new Enemy(3800, 250, 50 ,50, 100 , 100));
        enemies.add(new Enemy(4100, 300, 50 ,50, 100 , 100));
        enemies.add(new Enemy(4400, 350, 50 ,50, 100 , 100));
        enemies.add(new Enemy(4700, 400, 50 ,50, 100 , 100));
        enemies.add(new Enemy(5000, 450, 50 ,50, 100 , 100));
        enemies.add(new Enemy(5300, 500, 50 ,50, 100 , 100));
        enemies.add(new Enemy(5600, 450, 50 ,50, 100 , 100));
        enemies.add(new Enemy(5900, 400, 50 ,50, 100 , 100));
        enemies.add(new Enemy(6200, 350, 50 ,50, 100 , 100));
        enemies.add(new Enemy(6500, 300, 50 ,50, 100 , 100));
        enemies.add(new Enemy(6800, 250, 50 ,50, 100 , 100));
        enemies.add(new Enemy(7100, 300, 50 ,50, 100 , 100));
        enemies.add(new Enemy(7400, 350, 50 ,50, 100 , 100));
        enemies.add(new Enemy(7700, 400, 50 ,50, 100 , 100));
        enemies.add(new Enemy(8000, 450, 50 ,50, 100 , 100));
        enemies.add(new Enemy(8300, 500, 50 ,50, 100 , 100));

        // Initialize Sonic at the starting position
        sonic = new Sonic(150, 450, 50, 50, lives);  // Position Sonic on the first platform

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
