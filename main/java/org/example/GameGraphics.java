package org.example;

import org.example.logic.Sonic;
import org.example.logic.Enemy;
import org.example.logic.Rings;

import javax.swing.*;
import java.awt.*;

public class GameGraphics extends JPanel {
    private Sonic sonic;
    private Enemy enemy;
    private Rings rings;

    public GameGraphics(Sonic sonic, Enemy enemy, Rings rings) {
        this.sonic = sonic;
        this.enemy = enemy;
        this.rings = rings;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sonic.isAlive()) {
            g.setColor(Color.BLUE);
            g.fillRect(sonic.coord.x, sonic.coord.y, sonic.width, sonic.height);
        }
        if (enemy.isAlive()) {
            g.setColor(Color.RED);
            g.fillRect(enemy.coord.x, enemy.coord.y, enemy.width, enemy.height);
        }
        for (int i = 0; i < rings.getCount(); i++) {
            g.setColor(Color.YELLOW);
            g.fillOval(20 + i * 20, 20, 10, 10);
        }


        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Rings: " + rings.getCount(), 20, 50);
    }
}
