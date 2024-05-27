package org.example;

import org.example.logic.*;
import org.example.levels.Level;
import org.example.levels.LevelOne;
import org.example.levels.LevelTwo;
import org.example.levels.LevelThree;
import org.example.logic.DeadScreen;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.Map;

public class Game {
    private GameGraphics gameGraphics; // Manages the graphical elements of the game
    private GameLogic gameLogic; // Manages the game logic and interactions
    private JPanel menuPanel; // Represents the menu panel in the game
    private JPanel levelSelectorPanel; // Represents the level selector panel in the game
    private JPanel winScreenPanel; // Represents the win screen panel in the game
    private JPanel diedScreenPanel; // Represents the died screen panel in the game
    private Level currentLevel; // Represents the current level in the game
    private String currentLevelName; // Keeps track of the current level's name
    private boolean isWinScreenDisplayed = false; // Tracks if the WinScreen is already displayed
    private boolean isDiedScreenDisplayed = false; // Tracks if the DiedScreen is already displayed

    private Map<String, Level> levels; // Stores available levels

    // Starts the game and calls the showMenu method on the Event Dispatch Thread
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Game().showMenu());
    }

    // Initializes the gameGraphics object
    public Game() {
        gameGraphics = new GameGraphics();
        levels = new LinkedHashMap<>(); // Use LinkedHashMap to maintain insertion order
        // Add levels here
        levels.put("Level One", new LevelOne());
        levels.put("Level Two", new LevelTwo());
        levels.put("Level Three", new LevelThree());
    }

    // Creates a new menu panel with start and exit buttons
    public void showMenu() {
        stopLevelMusic(); // Stop the level music when showing the menu

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

    // Shows the level selector panel
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

    // Handles level selection
    private class LevelSelectionHandler implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String levelName = e.getActionCommand();
            startLevel(levelName);
        }
    }

    // Starts the specified level
    private void startLevel(String levelName) {
        System.out.println("Starting level: " + levelName); // Debugging line

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

        // Check if the level exists
        if (!levels.containsKey(levelName)) {
            System.err.println("Level not found: " + levelName);
            return;
        }

        // Setup the specified level
        currentLevel = levels.get(levelName);
        currentLevelName = levelName;

        // Ensure currentLevel is not null
        if (currentLevel == null) {
            System.err.println("Current level is null for level name: " + levelName);
            return;
        }

        // Spawn the player at the starting position
        currentLevel.spawnPlayer();

        // Sets up the game components in the gameGraphics object
        gameGraphics.setupGameComponents(currentLevel.getSonic(), currentLevel.getEnemies(), currentLevel.getLives(), currentLevel.getPlatforms());
        gameLogic = new GameLogic(currentLevel.getSonic(), currentLevel.getEnemies(), gameGraphics, currentLevel.getLevelEndX(), 600, this::showWinScreen, this::showDiedScreen); // Adjust fallYCoordinate as needed
        gameGraphics.getFrame().add(gameGraphics);
        gameGraphics.getFrame().revalidate();
        gameGraphics.getFrame().repaint();
        gameGraphics.setVisible(true);

        // Starts the game loop
        gameGraphics.startGameLoop(gameLogic);

        // Start the background music for the level
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

        // Adds key listeners to handle user input
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

        // Requests focus for the frame to capture key events
        gameGraphics.getFrame().requestFocus();
    }

    // Shows the win screen
    private void showWinScreen() {
        stopLevelMusic(); // Stop the level music when showing the win screen
        if (!isWinScreenDisplayed) { // Check if WinScreen is not already displayed
            winScreenPanel = new WinScreen(e -> {
                resetLevel();
                showMenu();
            });
            gameGraphics.getFrame().add(winScreenPanel);
            isWinScreenDisplayed = true; // Set the flag to indicate that WinScreen is displayed
        }
        if (winScreenPanel != null) {
            winScreenPanel.setVisible(true);
        }
        gameGraphics.setVisible(false);
        gameGraphics.getFrame().revalidate();
        gameGraphics.getFrame().repaint();
    }

    // Shows the died screen
    private void showDiedScreen() {
        stopLevelMusic(); // Stop the level music when showing the died screen
        if (!isDiedScreenDisplayed) { // Check if DiedScreen is not already displayed
            diedScreenPanel = new DeadScreen(e -> {
                resetLevel();
                showMenu();
            });
            gameGraphics.getFrame().add(diedScreenPanel);
            isDiedScreenDisplayed = true; // Set the flag to indicate that DiedScreen is displayed
        }
        if (diedScreenPanel != null) {
            diedScreenPanel.setVisible(true);
        }
        gameGraphics.setVisible(false);
        gameGraphics.getFrame().revalidate();
        gameGraphics.getFrame().repaint();
    }

    // Resets the level to its initial state
    private void resetLevel() {
        if (currentLevel != null) {
            currentLevel.reset();
        }
    }

    // Exits the application
    private void exitGame() {
        System.exit(0);
    }

    // Stops the background music of the current level
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
