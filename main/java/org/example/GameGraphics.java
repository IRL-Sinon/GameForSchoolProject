package org.example;

import org.example.logic.Sonic;
import org.example.logic.Enemy;
import org.example.logic.Lives;

import javax.swing.*;
import java.awt.*;

class GameGraphics extends JPanel {
    private Sonic sonic;
    private Enemy enemy;
    private Lives lives;

    public GameGraphics(Sonic sonic, Enemy enemy, Lives lives) {
        this.sonic = sonic;
        this.enemy = enemy;
        this.lives = lives;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sonic.isAlive()) {
            g.setColor(Color.BLUE);
            g.fillRect(sonic.getCoord().getX(), sonic.getCoord().getY(), sonic.getWidth(), sonic.getHeight());
        }
        if (enemy.isAlive()) {
            g.setColor(Color.RED);
            g.fillRect(enemy.getCoord().getX(), enemy.getCoord().getY(), enemy.getWidth(), enemy.getHeight());
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Lives: " + lives.getLives(), 20, 50);
    }
}