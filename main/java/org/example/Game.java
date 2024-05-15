package org.example;

import org.example.logic.Sonic;
import org.example.logic.Enemy;
import org.example.logic.Rings;

import javax.swing.*;
import java.awt.*;

public class Game {
    public static void main(String[] args) {
        Enemy enemy = new Enemy(500, 200, 20, 20, 300, 300);
        Rings rings = new Rings();
        Sonic sonic = new Sonic(50, 203, enemy, rings);

        GameLogic gameLogic = new GameLogic(sonic, enemy);

        JFrame frame = new JFrame("Project Sonic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setLocationRelativeTo(null);
        GameGraphics gameGraphics = new GameGraphics(sonic, enemy, rings);
        frame.add(gameGraphics);
        frame.setVisible(true);

        Timer timer = new Timer(25, e -> {
            gameLogic.update();
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

            public void keyTyped(java.awt.event.KeyEvent evt) {
                if (evt.getKeyChar() == ' ') {
                    sonic.jump();
                }
            }
        });
    }
}
