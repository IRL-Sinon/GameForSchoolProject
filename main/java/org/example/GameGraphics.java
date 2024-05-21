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
import java.util.List;
import java.util.Iterator;

class GameGraphics extends JPanel {
    private Sonic sonic;
    private List<Enemy> enemies;
    private Lives lives;
    private Timer timer;
    private boolean wasIdle = true;
    private Camera camera;
    private Image backgroundImage;
    private JFrame frame;

    public GameGraphics() {
        initializeFrame();
    }

    public void initializeFrame() {
        frame = new JFrame("Project Sonic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(800,600);
        frame.setLocationRelativeTo(null);
        frame.setUndecorated(false);
        ImageIcon icon = new ImageIcon("src/main/resources/sonic icon.png");
        frame.setIconImage(icon.getImage());
        frame.add(this);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setupGameComponents(Sonic sonic, List<Enemy> enemies, Lives lives) {
        this.sonic = sonic;
        this.enemies = enemies;
        this.lives = lives;
        this.camera = new Camera(1920, 1080);
    }

    public void startGameLoop(GameLogic gameLogic) {
        timer = new Timer(18, e -> {
            gameLogic.update();
            if (camera != null) {
                camera.update(sonic);
            }
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (camera == null) return;

        int offsetX = camera.getX();
        int offsetY = camera.getY();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, -offsetX, -offsetY, this);
        }

        if (sonic.isAlive() && sonic.isVisible()) {
            ImageIcon currentGif = sonic.getCurrentGif();
            if (currentGif != null) {
                Image image = currentGif.getImage();
                int drawX = sonic.getCoord().getX() - offsetX;
                int drawY = sonic.getCoord().getY() - offsetY;
                if (!sonic.isFacingRight() && Math.abs(sonic.getCurrentSpeed()) > 0) {
                    AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
                    tx.translate(-image.getWidth(null), 0);
                    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
                    image = op.filter(toBufferedImage(image), null);
                }
                g2d.drawImage(image, drawX, drawY, this);
            } else {
                System.err.println("Current GIF is null");
            }
        }

        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                int drawX = enemy.getCoord().getX() - offsetX;
                int drawY = enemy.getCoord().getY() - offsetY;
                g2d.setColor(Color.RED);
                g2d.fillRect(drawX, drawY, enemy.getWidth(), enemy.getHeight());
            }
        }

        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Lives: " + lives.getLives(), 20, 50);
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
