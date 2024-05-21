package org.example;

import org.example.logic.Sonic;
import org.example.logic.Enemy;
import org.example.logic.Lives;

import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.List;

class GameGraphics extends JPanel {
    private Sonic sonic;
    private List<Enemy> enemies;
    private Lives lives;
    private Timer timer;

    public GameGraphics(Sonic sonic, List<Enemy> enemies, Lives lives) {
        this.sonic = sonic;
        this.enemies = enemies;
        this.lives = lives;
    }

    public JFrame initializeFrame() {
        JFrame frame = new JFrame("Project Sonic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1920, 1080);
        frame.setLocationRelativeTo(null);
        frame.add(this);
        frame.setVisible(true);
        return frame;
    }

    public void startGame() {
        timer = new Timer(25, e -> {
            if (sonic.isAlive()) {
                // Update Sonic's position and handle collisions
                sonic.update(enemies);

                // Update enemies and remove any that are dead
                Iterator<Enemy> iterator = enemies.iterator();
                while (iterator.hasNext()) {
                    Enemy enemy = iterator.next();
                    if (enemy.isAlive()) {
                        enemy.update(sonic);
                    } else {
                        iterator.remove();
                    }
                }
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (sonic.isAlive() && sonic.isVisible()) {
            ImageIcon currentGif = sonic.getCurrentGif();
            if (currentGif != null) {
                g.drawImage(currentGif.getImage(), sonic.getCoord().getX(), sonic.getCoord().getY(), this);
            } else {
                System.err.println("Current GIF is null");
            }
        }

        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                g.setColor(Color.RED);
                g.fillRect(enemy.getCoord().getX(), enemy.getCoord().getY(), enemy.getWidth(), enemy.getHeight());
            }
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Lives: " + lives.getLives(), 20, 50);
    }
}
