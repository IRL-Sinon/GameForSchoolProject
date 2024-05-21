package org.example;

import org.example.logic.Sonic;
import org.example.logic.Enemy;
import org.example.logic.Lives;
import org.example.logic.Camera;

import javax.swing.*;
import java.awt.*;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Iterator;
import java.util.List;

class GameGraphics extends JPanel {
    private Sonic sonic;
    private List<Enemy> enemies;
    private Lives lives;
    private Timer timer;
    private boolean wasIdle = true;
    private Camera camera;

    public GameGraphics(Sonic sonic, List<Enemy> enemies, Lives lives) {
        this.sonic = sonic;
        this.enemies = enemies;
        this.lives = lives;
        this.camera = new Camera(1920, 1080);
    }

    public JFrame initializeFrame() {
        JFrame frame = new JFrame("Project Sonic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLocationRelativeTo(null);
        frame.setSize(800,500);
        frame.setUndecorated(false);
        ImageIcon icon = new ImageIcon("src/main/resources/sonic icon.png");
        frame.setIconImage(icon.getImage());
        frame.add(this);
        frame.setVisible(true);
        return frame;
    }

    public void startGame() {
        timer = new Timer(18, e -> {
            if (sonic.isAlive()) {
                sonic.update(enemies);
                camera.update(sonic);

                if (wasIdle && Math.abs(sonic.getCurrentSpeed()) > 0) {
                    sonic.resetIdleGif();
                    wasIdle = false;
                } else if (!wasIdle && Math.abs(sonic.getCurrentSpeed()) == 0) {
                    wasIdle = true;
                }

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
        int offsetX = camera.getX();
        int offsetY = camera.getY();

        if (sonic.isAlive() && sonic.isVisible()) {
            ImageIcon currentGif = sonic.getCurrentGif();
            if (currentGif != null) {
                Image image = currentGif.getImage();
                int drawX = sonic.getCoord().getX() - offsetX;
                int drawY = sonic.getCoord().getY() - offsetY;
                if (!sonic.isFacingRight() && Math.abs(sonic.getCurrentSpeed()) > 0) {
                    AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                    tx.translate(-image.getWidth(null), 0);
                    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
                    image = op.filter(toBufferedImage(image), null);
                }
                g.drawImage(image, drawX, drawY, this);
            } else {
                System.err.println("Current GIF is null");
            }
        }

        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                int drawX = enemy.getCoord().getX() - offsetX;
                int drawY = enemy.getCoord().getY() - offsetY;
                g.setColor(Color.RED);
                g.fillRect(drawX, drawY, enemy.getWidth(), enemy.getHeight());
            }
        }

        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.drawString("Lives: " + lives.getLives(), 20, 50);
    }

    private BufferedImage toBufferedImage(Image img) {
        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bimage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();
        return bimage;
    }
}
