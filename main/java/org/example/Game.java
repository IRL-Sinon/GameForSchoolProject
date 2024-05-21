package org.example;

import org.example.logic.Menu;
import org.example.logic.Sonic;
import org.example.logic.Enemy;
import org.example.logic.Lives;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private GameGraphics gameGraphics;
    private GameLogic gameLogic;
    private JPanel menuPanel;
    private Lives lives;
    private Sonic sonic;
    private List<Enemy> enemies;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Game().showMenu());
    }

    public Game() {
        gameGraphics = new GameGraphics();
    }

    public void showMenu() {
        menuPanel = new Menu(e -> startGame(), e -> exitGame());
        gameGraphics.getFrame().add(menuPanel);
        gameGraphics.getFrame().revalidate();
        gameGraphics.getFrame().repaint();
    }

    private void startGame() {
        gameGraphics.getFrame().remove(menuPanel);
        lives = new Lives(3);
        sonic = new Sonic(50, 203, 10, 10, lives);
        enemies = new ArrayList<>();
        enemies.add(new Enemy(500, 200, 20, 20, 300, 300));
        gameGraphics.setupGameComponents(sonic, enemies, lives);
        gameLogic = new GameLogic(sonic, enemies);
        gameGraphics.getFrame().add(gameGraphics);
        gameGraphics.getFrame().revalidate();
        gameGraphics.getFrame().repaint();
        gameGraphics.startGameLoop(gameLogic);
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

        gameGraphics.getFrame().requestFocus();
    }

    private void exitGame() {
        System.exit(0);
    }
}
