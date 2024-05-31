package org.example;

import org.example.logic.Sonic;
import org.example.logic.Enemy;
import org.example.logic.Camera;
import org.example.logic.Lives;

import javax.swing.*;
import java.awt.*;
import java.awt.image.AffineTransformOp;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import javax.imageio.ImageIO;

class GameGraphics extends JPanel {
    private Sonic sonic;
    private List<Enemy> enemies;
    private Lives lives;
    private Timer timer;
    private Camera camera;
    private Image backgroundImage;
    private Image platformImage;
    private JFrame frame;
    private List<Rectangle> platforms;
    private int levelWidth;
    private int levelHeight;

    public GameGraphics() {
        initializeFrame();
        loadPlatformImage();
    }

    public void initializeFrame() {
        frame = new JFrame("Project Sonic");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(false);
        frame.setLocationRelativeTo(null);
        frame.setSize(1920, 1080);
        ImageIcon icon = new ImageIcon("sonic icon.png");
        frame.setIconImage(icon.getImage());
        frame.add(this);
        frame.setVisible(true);
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setupGameComponents(Sonic sonic, List<Enemy> enemies, Lives lives, List<Rectangle> platforms, Image backgroundImage, int levelWidth, int levelHeight) {
        this.sonic = sonic;
        this.enemies = enemies;
        this.lives = lives;
        this.platforms = platforms;
        this.backgroundImage = backgroundImage;
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.camera = new Camera(frame.getWidth(), frame.getHeight()); // Initialize camera with screen dimensions
    }

    public void startGameLoop(GameLogic gameLogic) {
        if (timer != null) {
            timer.stop();
        }
        timer = new Timer(25, e -> {
            gameLogic.update();
            if (camera != null) {
                camera.update(sonic);
            }
            repaint();
        });
        timer.start();
    }

    private void loadPlatformImage() {
        try {
            URL imageUrl = getClass().getClassLoader().getResource("Floor.png");
            if (imageUrl != null) {
                platformImage = ImageIO.read(imageUrl);
            } else {
                System.err.println("Platform image not found: Floor.png");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (camera == null) return;

        int offsetX = camera.getX();
        int offsetY = camera.getY();

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        // Calculate the scale factors for the background image
        double scaleX = (double) (levelWidth * 1.5) / backgroundImage.getWidth(null); // Make the background twice as wide as the level
        double scaleY = (double) (levelHeight ) / backgroundImage.getHeight(null); // Make the background twice as high as the level

        // Adjust the vertical and horizontal position of the background image
        int backgroundYOffset = -500; // Move the background image up by 500 pixels
        int backgroundXOffset = -1000; // Move the background image left by 1000 pixels

        // Draw the background image scaled to fit the larger dimensions
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, -offsetX + backgroundXOffset, -offsetY + backgroundYOffset, (int) (backgroundImage.getWidth(null) * scaleX), (int) (backgroundImage.getHeight(null) * scaleY), null);
        } else {
            System.err.println("Background image is null");
        }

        // Draw the platforms with the platform image
        if (platformImage != null) {
            for (Rectangle platform : platforms) {
                for (int x = 0; x < platform.width; x += platformImage.getWidth(null)) {
                    for (int y = 0; y < platform.height; y += platformImage.getHeight(null)) {
                        g2d.drawImage(platformImage, platform.x - offsetX + x, platform.y - offsetY + y, this);
                    }
                }
            }
        } else {
            g2d.setColor(Color.GRAY);
            for (Rectangle platform : platforms) {
                g2d.fillRect(platform.x - offsetX, platform.y - offsetY, platform.width, platform.height);
            }
        }

        // Draw Sonic if he is alive and visible
        if (sonic.isAlive() && sonic.isVisible()) {
            ImageIcon currentGif = sonic.getCurrentGif();
            if (currentGif != null) {
                Image image = currentGif.getImage();
                int drawX = sonic.getCoord().getX() - offsetX;
                int drawY = sonic.getCoord().getY() - offsetY;

                // Adjust Sonic's y-coordinate to be closer to the bottom of the screen
                drawY = Math.min(getHeight() - sonic.getHeight() - 150, drawY);

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

    public List<Rectangle> getPlatforms() {
        return platforms;
    }
}
