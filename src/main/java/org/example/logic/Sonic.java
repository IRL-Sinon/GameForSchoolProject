package org.example.logic;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;
import java.util.List;
import java.awt.Rectangle;

public class Sonic extends Entity {
    // Movement and state flags
    private boolean movingRight;
    private boolean movingLeft;
    private boolean jumping;
    private boolean canJump;
    private boolean inBallForm;
    private boolean immortal;

    // Timers and durations
    private int immortalTime;
    private int immortalDuration;
    private int knockBackTime;
    private int knockBackDuration;

    // Movement parameters
    private double jumpStrength;
    private double acceleration;
    private double deceleration;
    private double maxSpeed;
    private double currentSpeed;

    // Knockback properties
    private boolean beingKnockedBack;
    private int knockBackSpeed;
    private int knockBackDirection;

    // Direction the character is facing
    private boolean facingRight = true;

    // Life management
    private Lives lives;

    // GIFs for animations
    private ImageIcon idleGif;
    private ImageIcon walkingGif;
    private ImageIcon slowRunGif;
    private ImageIcon fullSpeedGif;
    private ImageIcon ballGif;
    private ImageIcon damageGif;

    // Dimensions for GIFs
    private int gifWidth = 50;
    private int gifHeight = 50;

    // Constructor
    public Sonic(int x, int y, int width, int height, Lives lives) {
        super(x, y, width, height);
        this.jumpStrength = 25;
        this.acceleration = 0.2;
        this.deceleration = 0.1;
        this.maxSpeed = 15;
        this.currentSpeed = 0;
        this.lives = lives;
        this.inBallForm = false;
        this.canJump = false; // Initially set to false
        this.immortal = false;
        this.immortalTime = 0;
        this.immortalDuration = 100;
        this.beingKnockedBack = false;
        this.knockBackSpeed = 5;
        this.knockBackDuration = 20;
        this.knockBackTime = 0;
        this.knockBackDirection = 0;

        // Load GIFs for different states
        this.idleGif = loadAndResizeGif("sonicIdle.gif");
        this.walkingGif = loadAndResizeGif("sonicWalking.gif");
        this.slowRunGif = loadAndResizeGif("sonicSlowRun.gif");
        this.fullSpeedGif = loadAndResizeGif("sonicRun.gif");
        this.ballGif = loadAndResizeGif("ball.gif");
        this.damageGif = loadAndResizeGif("sonicDamage.gif");
    }

    // Loads and resizes GIFs from the specified path
    private ImageIcon loadAndResizeGif(String path) {
        URL imgURL = getClass().getClassLoader().getResource(path);
        if (imgURL != null) {
            ImageIcon originalGif = new ImageIcon(imgURL);
            Image image = originalGif.getImage().getScaledInstance(gifWidth, gifHeight, Image.SCALE_DEFAULT);
            return new ImageIcon(image);
        } else {
            System.err.println("Couldn't find file: " + path);
            return new ImageIcon(new byte[0]);
        }
    }

    // Methods to control movement direction
    public void moveRight(boolean move) {
        movingRight = move;
    }

    public void moveLeft(boolean move) {
        movingLeft = move;
    }

    // Method to handle jumping
    public void jump() {
        if (canJump && !jumping) {
            jumping = true;
            verticalSpeed = -jumpStrength;
            inBallForm = true;
        }
    }

    // Updates Sonic's state and interacts with enemies and platforms
    public void update(List<Enemy> enemies, List<Rectangle> platforms) {
        if (!isAlive()) return;

        if (immortal) {
            immortalTime++;
            if (immortalTime > immortalDuration) {
                immortal = false;
                immortalTime = 0;
            }
        }

        if (beingKnockedBack) {
            knockBackTime++;
            getCoord().setX(getCoord().getX() + knockBackDirection * knockBackSpeed);
            if (knockBackTime > knockBackDuration) {
                beingKnockedBack = false;
                knockBackTime = 0;
                currentSpeed = 0;
            }
            return;
        }

        if (movingRight) {
            currentSpeed += acceleration;
            currentSpeed = Math.min(currentSpeed, maxSpeed);
            if (currentSpeed > 0) {
                facingRight = true;
            }
        }
        if (movingLeft) {
            currentSpeed -= acceleration;
            currentSpeed = Math.max(currentSpeed, -maxSpeed);
            if (currentSpeed < 0) {
                facingRight = false;
            }
        }

        if (!movingLeft && !movingRight) {
            if (currentSpeed > 0) {
                currentSpeed -= deceleration;
                currentSpeed = Math.max(currentSpeed, 0);
            } else if (currentSpeed < 0) {
                currentSpeed += deceleration;
                currentSpeed = Math.min(currentSpeed, 0);
            }
        }

        getCoord().setX(getCoord().getX() + (int) currentSpeed);

        // Apply gravity and vertical movement
        applyGravity();

        // Check for collision with platforms
        boolean onPlatform = handlePlatformCollision(platforms);

        if (onPlatform) {
            canJump = true;
            if (jumping) {
                jumping = false;
                inBallForm = false;
                width = 20;
                height = 20;
            }
        } else {
            canJump = false;
        }

        for (Enemy enemy : enemies) {
            if (enemy.isAlive() && checkCollision(enemy)) {
                if (isInBallForm() && getCoord().getY() + getHeight() <= enemy.getCoord().getY() + enemy.getHeight()) {
                    enemy.die();
                    verticalSpeed = -jumpStrength; // Bounce off the enemy
                    jumping = true;
                } else if (!isInBallForm() && enemy.isAlive()) {
                    int knockBackDirection = getCoord().getX() > enemy.getCoord().getX() ? 1 : -1;
                    takeDamage(knockBackDirection);
                }
            }
        }
    }

    // Returns the current GIF based on Sonic's state
    public ImageIcon getCurrentGif() {
        ImageIcon previousGif = idleGif;
        ImageIcon currentGif;

        if (beingKnockedBack) {
            currentGif = damageGif;
        } else if (isInBallForm()) {
            currentGif = ballGif;
        } else if (Math.abs(currentSpeed) >= 10) {
            currentGif = fullSpeedGif;
        } else if (Math.abs(currentSpeed) >= 6) {
            currentGif = slowRunGif;
        } else if (Math.abs(currentSpeed) >= 1) {
            currentGif = walkingGif;
        } else {
            currentGif = idleGif;
        }

        if (previousGif != currentGif) {
            resetIdleGif();
        }

        return currentGif;
    }

    // Check which direction Sonic is facing
    public boolean isFacingRight() {
        return facingRight;
    }

    // Gets the current speed of Sonic
    public double getCurrentSpeed() {
        return currentSpeed;
    }

    // Resets the idle GIF to the original state
    public void resetIdleGif() {
        this.idleGif = loadAndResizeGif("sonicIdle.gif");
    }

    // Handles the damage and knockback when Sonic takes a hit
    public void takeDamage(int knockBackDirection) {
        if (isInBallForm()) {
            return;
        }

        if (!immortal) {
            lives.loseLife();
            immortal = true;
            if (lives.getLives() <= 0) {
                alive = false;
            } else {
                beingKnockedBack = true;
                this.knockBackDirection = knockBackDirection;
            }
        }
    }

    // Checks for collision with another entity
    public boolean checkCollision(Entity entity) {
        if (!isAlive()) return false;

        return getCoord().getX() + getWidth() >= entity.getCoord().getX() &&
                getCoord().getX() <= entity.getCoord().getX() + entity.getWidth() &&
                getCoord().getY() + getHeight() >= entity.getCoord().getY() &&
                getCoord().getY() <= entity.getCoord().getY() + entity.getHeight();
    }

    // Checks if Sonic is in ball form
    public boolean isInBallForm() {
        return inBallForm;
    }

    // Checks if Sonic is in an immortal state
    public boolean isImmortal() {
        return immortal;
    }

    // Determines if Sonic is visible (blinks when immortal)
    public boolean isVisible() {
        if (immortal) {
            return (immortalTime / 10) % 2 == 0;
        }
        return true;
    }
}
