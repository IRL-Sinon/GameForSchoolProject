package org.example;

import org.example.logic.Sonic;
import org.example.logic.Enemy;
import org.example.logic.Lives;

import javax.swing.*;
import java.awt.*;

public class Game {
    public static void main(String[] args) {
        Lives lives = new Lives(3);
        Enemy enemy = new Enemy(500, 200, 20, 20, 300, 300);
        Sonic sonic = new Sonic(50, 203, 20, 20, lives);

        JFrame frame = new JFrame("Project Sonic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        GameGraphics gameGraphics = new GameGraphics(sonic, enemy, lives);
        frame.add(gameGraphics);
        frame.setVisible(true);

        Timer timer = new Timer(25, e -> {
            sonic.update();
            enemy.update(sonic);
            gameGraphics.repaint();
        });
        timer.start();

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
    }
}