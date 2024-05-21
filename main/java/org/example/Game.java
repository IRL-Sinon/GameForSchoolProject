package org.example;

import org.example.logic.Sonic;
import org.example.logic.Enemy;
import org.example.logic.Lives;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class Game {
    public static void main(String[] args) {
        Lives lives = new Lives(3);
        List<Enemy> enemies = new ArrayList<>();
        enemies.add(new Enemy(700, 200, 40, 40, 500, 500));
        Sonic sonic = new Sonic(50, 200, 40, 40, lives);

        GameGraphics gameGraphics = new GameGraphics(sonic, enemies, lives);
        JFrame frame = gameGraphics.initializeFrame();

        frame.addKeyListener(new java.awt.event.KeyAdapter() {
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

        gameGraphics.startGame();
    }
}
