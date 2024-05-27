package org.example.levels;

import org.example.logic.Enemy;
import org.example.logic.Lives;
import org.example.logic.Sonic;

import javax.sound.sampled.*;
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

    public LevelThree() {
        initializeLevel();
    }

    private void initializeLevel() {
        platforms = new ArrayList<>();
        enemies = new ArrayList<>();
        lives = new Lives(3);
        levelEndX = 30000; // Much longer length for the hardest level

        //add platforms and delete this comment later


        //add enemies and delete this comment later
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
