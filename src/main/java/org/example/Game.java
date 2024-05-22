package org.example;

import org.example.logic.Menu;
import org.example.logic.LevelSelector;
import org.example.logic.Sonic;
import org.example.logic.Enemy;
import org.example.logic.Lives;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private GameGraphics gameGraphics; // Manages the graphical elements of the game
    private GameLogic gameLogic; // Manages the game logic and interactions
    private JPanel menuPanel; // Represents the menu panel in the game
    private JPanel levelSelectorPanel; // Represents the level selector panel in the game
    private Lives lives; // Manages the number of lives Sonic has
    private Sonic sonic; // Represents the Sonic character
    private List<Enemy> enemies; // Stores a list of enemies in the game

    // Starts the game and calls the showMenu method on the Event Dispatch Thread
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Game().showMenu());
    }

    // Initializes the gameGraphics object
    public Game() {
        gameGraphics = new GameGraphics();
    }

    // Creates a new menu panel with start and exit buttons
    public void showMenu() {
        if (menuPanel == null) {
            menuPanel = new Menu(e -> showLevelSelector(), e -> exitGame());
            gameGraphics.getFrame().add(menuPanel);
        }
        menuPanel.setVisible(true);
        if (levelSelectorPanel != null) {
            levelSelectorPanel.setVisible(false);
        }
        gameGraphics.getFrame().revalidate();
        gameGraphics.getFrame().repaint();
    }

    // Shows the level selector panel
    private void showLevelSelector() {
        if (levelSelectorPanel == null) {
            levelSelectorPanel = new LevelSelector(e -> startTestLevel(), e -> showMenu());
            gameGraphics.getFrame().add(levelSelectorPanel);
        }
        levelSelectorPanel.setVisible(true);
        menuPanel.setVisible(false);
        gameGraphics.getFrame().revalidate();
        gameGraphics.getFrame().repaint();
    }

    // Starts the test level
    private void startTestLevel() {
        gameGraphics.getFrame().remove(levelSelectorPanel);

        // Initializes the lives, sonic, and enemies objects
        lives = new Lives(3);
        sonic = new Sonic(50, 0, 10, 10, lives);
        enemies = new ArrayList<>();
        enemies.add(new Enemy(500, 200, 20, 20, 300, 300));

        // Sets up the game components in the gameGraphics object
        gameGraphics.setupGameComponents(sonic, enemies, lives);
        gameLogic = new GameLogic(sonic, enemies, gameGraphics);
        gameGraphics.getFrame().add(gameGraphics);
        gameGraphics.getFrame().revalidate();
        gameGraphics.getFrame().repaint();

        // Starts the game loop
        gameGraphics.startGameLoop(gameLogic);

        // Adds key listeners to handle user input
        gameGraphics.getFrame().addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_RIGHT:
                        sonic.moveRight(true);
                        break;
                    case java.awt.event.KeyEvent.VK_LEFT:
                        sonic.moveLeft(true);
                        break;
                    case java.awt.event.KeyEvent.VK_SPACE:
                        sonic.jump();
                        break;
                }
            }

            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_RIGHT:
                        sonic.moveRight(false);
                        break;
                    case java.awt.event.KeyEvent.VK_LEFT:
                        sonic.moveLeft(false);
                        break;
                }
            }
        });

        // Requests focus for the frame to capture key events
        gameGraphics.getFrame().requestFocus();
    }

    // Exits the application
    private void exitGame() {
        System.exit(0);
    }
}
