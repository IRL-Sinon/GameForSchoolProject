package org.example;

import org.example.logic.*;
import org.example.levels.Level;
import org.example.levels.LevelOne;
import org.example.levels.LevelTwo;
import org.example.levels.LevelThree;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

public class Game {
    private GameGraphics gameGraphics;
    private GameLogic gameLogic;
    private JPanel menuPanel;
    private JPanel levelSelectorPanel;
    private JPanel winScreenPanel;
    private JPanel diedScreenPanel;
    private Level currentLevel;
    private String currentLevelName;
    private boolean isWinScreenDisplayed = false;
    private boolean isDiedScreenDisplayed = false;

    private Map<String, Level> levels;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Game().showMenu());
    }

    public Game() {
        gameGraphics = new GameGraphics();
        levels = new LinkedHashMap<>();
        levels.put("Level One", new LevelOne());
        levels.put("Level Two", new LevelTwo());
        levels.put("Level Three", new LevelThree());
    }

    public void showMenu() {
        stopLevelMusic();

        if (menuPanel == null) {
            menuPanel = new Menu(e -> showLevelSelector(), e -> exitGame());
            gameGraphics.getFrame().add(menuPanel);
        }
        menuPanel.setVisible(true);
        if (levelSelectorPanel != null) {
            levelSelectorPanel.setVisible(false);
        }
        if (winScreenPanel != null) {
            winScreenPanel.setVisible(false);
            gameGraphics.getFrame().remove(winScreenPanel);
            winScreenPanel = null;
        }
        if (diedScreenPanel != null) {
            diedScreenPanel.setVisible(false);
            gameGraphics.getFrame().remove(diedScreenPanel);
            diedScreenPanel = null;
        }
        gameGraphics.setVisible(false);
        gameGraphics.getFrame().revalidate();
        gameGraphics.getFrame().repaint();
    }

    private void showLevelSelector() {
        if (levelSelectorPanel == null) {
            levelSelectorPanel = new LevelSelector(new LevelSelectionHandler(), e -> showMenu(), levels.keySet());
            gameGraphics.getFrame().add(levelSelectorPanel);
        }
        levelSelectorPanel.setVisible(true);
        if (menuPanel != null) {
            menuPanel.setVisible(false);
        }
        gameGraphics.setVisible(false);
        gameGraphics.getFrame().revalidate();
        gameGraphics.getFrame().repaint();
    }

    private class LevelSelectionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String levelName = e.getActionCommand();
            startLevel(levelName);
        }
    }

    private void startLevel(String levelName) {
        System.out.println("Starting level: " + levelName);

        if (levelSelectorPanel != null) {
            levelSelectorPanel.setVisible(false);
        }
        if (winScreenPanel != null) {
            gameGraphics.getFrame().remove(winScreenPanel);
            winScreenPanel = null;
        }
        if (diedScreenPanel != null) {
            gameGraphics.getFrame().remove(diedScreenPanel);
            diedScreenPanel = null;
        }
        isWinScreenDisplayed = false;
        isDiedScreenDisplayed = false;

        if (!levels.containsKey(levelName)) {
            System.err.println("Level not found: " + levelName);
            return;
        }

        currentLevel = levels.get(levelName);
        currentLevelName = levelName;

        if (currentLevel == null) {
            System.err.println("Current level is null for level name: " + levelName);
            return;
        }

        currentLevel.spawnPlayer();

        int levelWidth = currentLevel.getLevelEndX();
        int levelHeight = gameGraphics.getFrame().getHeight() * 2; // Example: Make the level height twice the frame height

        gameGraphics.setupGameComponents(
                currentLevel.getSonic(),
                currentLevel.getEnemies(),
                currentLevel.getLives(),
                currentLevel.getPlatforms(),
                currentLevel.getBackgroundImage(),
                levelWidth,
                levelHeight
        );
        gameLogic = new GameLogic(
                currentLevel.getSonic(),
                currentLevel.getEnemies(),
                gameGraphics,
                levelWidth,
                levelHeight, // Use the larger level height
                this::showWinScreen,
                this::showDiedScreen
        );
        gameGraphics.getFrame().add(gameGraphics);
        gameGraphics.getFrame().revalidate();
        gameGraphics.getFrame().repaint();
        gameGraphics.setVisible(true);

        gameGraphics.startGameLoop(gameLogic);

        switch (levelName) {
            case "Level One":
                ((LevelOne) currentLevel).startBackgroundMusic("Level-1.wav");
                break;
            case "Level Two":
                ((LevelTwo) currentLevel).startBackgroundMusic("Level-2.wav");
                break;
            case "Level Three":
                ((LevelThree) currentLevel).startBackgroundMusic("Level-3.wav");
                break;
        }

        gameGraphics.getFrame().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_RIGHT:
                        currentLevel.getSonic().moveRight(true);
                        break;
                    case java.awt.event.KeyEvent.VK_LEFT:
                        currentLevel.getSonic().moveLeft(true);
                        break;
                    case java.awt.event.KeyEvent.VK_SPACE:
                        currentLevel.getSonic().jump();
                        break;
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_RIGHT:
                        currentLevel.getSonic().moveRight(false);
                        break;
                    case java.awt.event.KeyEvent.VK_LEFT:
                        currentLevel.getSonic().moveLeft(false);
                        break;
                }
            }
        });

        gameGraphics.getFrame().requestFocus();
    }

    private void showWinScreen() {
        stopLevelMusic();
        if (!isWinScreenDisplayed) {
            winScreenPanel = new WinScreen(e -> {
                resetLevel();
                showMenu();
            });
            gameGraphics.getFrame().add(winScreenPanel);
            isWinScreenDisplayed = true;
        }
        if (winScreenPanel != null) {
            winScreenPanel.setVisible(true);
        }
        gameGraphics.setVisible(false);
        gameGraphics.getFrame().revalidate();
        gameGraphics.getFrame().repaint();
    }

    private void showDiedScreen() {
        stopLevelMusic();
        if (!isDiedScreenDisplayed) {
            diedScreenPanel = new DeadScreen(e -> {
                resetLevel();
                showMenu();
            });
            gameGraphics.getFrame().add(diedScreenPanel);
            isDiedScreenDisplayed = true;
        }
        if (diedScreenPanel != null) {
            diedScreenPanel.setVisible(true);
        }
        gameGraphics.setVisible(false);
        gameGraphics.getFrame().revalidate();
        gameGraphics.getFrame().repaint();
    }

    private void resetLevel() {
        if (currentLevel != null) {
            currentLevel.reset();
        }
    }

    private void exitGame() {
        System.exit(0);
    }

    private void stopLevelMusic() {
        if (currentLevel instanceof LevelOne) {
            ((LevelOne) currentLevel).stopBackgroundMusic();
        } else if (currentLevel instanceof LevelTwo) {
            ((LevelTwo) currentLevel).stopBackgroundMusic();
        } else if (currentLevel instanceof LevelThree) {
            ((LevelThree) currentLevel).stopBackgroundMusic();
        }
    }
}
