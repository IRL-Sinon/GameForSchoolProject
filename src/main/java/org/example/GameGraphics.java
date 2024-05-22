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

class GameGraphics extends JPanel {
    private Sonic sonic; // Represents the Sonic character
    private List<Enemy> enemies; // Stores a list of enemies in the game
    private Lives lives; // Manages the number of lives Sonic has
    private Timer timer; // Timer for the game loop
    private boolean wasIdle = true; // Tracks if Sonic was idle
    private Camera camera; // Manages the camera view
    private Image backgroundImage; // Background image of the game
    private JFrame frame; // Main frame of the game window
    private List<Rectangle> platforms; // List of platforms for Sonic and enemies to walk on

    // Constructor initializes the game frame
    public GameGraphics() {
        initializeFrame();
    }

    // Initializes the game frame settings
    public void initializeFrame() {
        frame = new JFrame("Project Sonic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(800, 600);
        ImageIcon icon = new ImageIcon("src/main/resources/sonic icon.png");
        frame.setIconImage(icon.getImage());
        frame.add(this);
        frame.setVisible(true);
    }

    // Returns the main frame of the game
    public JFrame getFrame() {
        return frame;
    }

    // Sets up the game components
    public void setupGameComponents(Sonic sonic, List<Enemy> enemies, Lives lives, List<Rectangle> platforms) {
        this.sonic = sonic;
        this.enemies = enemies;
        this.lives = lives;
        this.platforms = platforms;
        this.camera = new Camera(1920, 1080);
    }

    // Starts the game loop with a timer
    public void startGameLoop(GameLogic gameLogic) {
        timer = new Timer(2, e -> {
            gameLogic.update();
            if (camera != null) {
                camera.update(sonic);
            }
            repaint();
        });
        timer.start();
    }

    // Paints the game components on the screen
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (camera == null) return;

        int offsetX = camera.getX();
        int offsetY = camera.getY();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Draw the background image
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, -offsetX, -offsetY, this);
        }

        // Draw the platforms
        g2d.setColor(Color.GRAY);
        for (Rectangle platform : platforms) {
            g2d.fillRect(platform.x - offsetX, platform.y - offsetY, platform.width, platform.height);
        }

        // Draw Sonic if he is alive and visible
        if (sonic.isAlive() && sonic.isVisible()) {
            ImageIcon currentGif = sonic.getCurrentGif();
            if (currentGif != null) {
                Image image = currentGif.getImage();
                int drawX = sonic.getCoord().getX() - offsetX;
                int drawY = sonic.getCoord().getY() - offsetY;
                if (!sonic.isFacingRight()) {
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

        // Draw enemies if they are alive
        for (Enemy enemy : enemies) {
            if (enemy.isAlive()) {
                ImageIcon currentGif = enemy.getCurrentGif();
                if (currentGif != null) {
                    Image image = currentGif.getImage();
                    int drawX = enemy.getCoord().getX() - offsetX;
                    int drawY = enemy.getCoord().getY() - offsetY;
                    g2d.drawImage(image, drawX, drawY, this);
                } else {
                    g2d.setColor(Color.RED);
                    g2d.fillRect(enemy.getCoord().getX() - offsetX, enemy.getCoord().getY() - offsetY, enemy.getWidth(), enemy.getHeight());
                }
            }
        }

        // Draw the number of lives Sonic has
        g2d.setColor(Color.BLACK);
        g2d.setFont(new Font("Arial", Font.BOLD, 16));
        g2d.drawString("Lives: " + lives.getLives(), 20, 50);
    }

    // Converts an Image to a BufferedImage
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

    // Returns the list of platforms for collision detection
    public List<Rectangle> getPlatforms() {
        return platforms;
    }
}
