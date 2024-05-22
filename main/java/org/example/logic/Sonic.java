package org.example.logic;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.net.URL;
import java.util.List;

public class Sonic extends Entity {
    private boolean movingRight;
    private boolean movingLeft;
    private boolean jumping;
    private boolean canJump;
    private boolean inBallForm;
    private boolean immortal;
    private int immortalTime;
    private int immortalDuration;
    private double jumpStrength;
    private double gravity;
    private double jumpStep;
    private double acceleration;
    private double deceleration;
    private double maxSpeed;
    private double currentSpeed;
    private Lives lives;
    private boolean beingKnockedBack;
    private int knockBackSpeed;
    private int knockBackDuration;
    private int knockBackTime;
    private int knockBackDirection;

    private boolean facingRight = true;

    private ImageIcon idleGif;
    private ImageIcon walkingGif;
    private ImageIcon slowRunGif;
    private ImageIcon fullSpeedGif;
    private ImageIcon ballGif;
    private ImageIcon damageGif;

    private int gifWidth = 50;
    private int gifHeight = 50;

    public Sonic(int x, int y, int width, int height, Lives lives) {
        super(x, y, width, height);
        this.jumpStrength = 15;
        this.gravity = 2;
        this.jumpStep = 0;
        this.acceleration = 0.2;
        this.deceleration = 0.1;
        this.maxSpeed = 15;
        this.currentSpeed = 0;
        this.lives = lives;
        this.inBallForm = false;
        this.canJump = true;
        this.immortal = false;
        this.immortalTime = 0;
        this.immortalDuration = 100;
        this.beingKnockedBack = false;
        this.knockBackSpeed = 5;
        this.knockBackDuration = 20;
        this.knockBackTime = 0;
        this.knockBackDirection = 0;

        this.idleGif = loadAndResizeGif("sonicIdle.gif");
        this.walkingGif = loadAndResizeGif("sonicWalking.gif");
        this.slowRunGif = loadAndResizeGif("sonicSlowRun.gif");
        this.fullSpeedGif = loadAndResizeGif("sonicRun.gif");
        this.ballGif = loadAndResizeGif("ball.gif");
        this.damageGif = loadAndResizeGif("sonicDamage.gif");
    }

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

    public void moveRight(boolean move) {
        movingRight = move;
    }

    public void moveLeft(boolean move) {
        movingLeft = move;
    }

    public void jump() {
        if (canJump && !jumping) {
            jumping = true;
            jumpStep = jumpStrength;
            inBallForm = true;
            width = 20;
            height = 20;
        }
    }

    public void update(List<Enemy> enemies) {
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

        if (jumping) {
            getCoord().setY(getCoord().getY() - (int) jumpStep);
            jumpStep -= gravity;
            if (getCoord().getY() >= 200) {
                getCoord().setY(200);
                jumping = false;
                inBallForm = false;
                width = 20;
                height = 20;
                canJump = true;
            }
        } else {
            if (getCoord().getY() < 200) {
                getCoord().setY(getCoord().getY() + (int) gravity);
            } else {
                canJump = true;
            }
        }

        for (Enemy enemy : enemies) {
            if (checkCollision(enemy)) {
                if (isInBallForm() && getCoord().getY() + getHeight() <= enemy.getCoord().getY() + enemy.getHeight()) {
                    enemy.die();

                    jumping = true;
                    jumpStep = jumpStrength;
                } else if (!isInBallForm() && enemy.isAlive()) {
                    int knockBackDirection = getCoord().getX() > enemy.getCoord().getX() ? 1 : -1;
                    takeDamage(knockBackDirection);
                }
            }
        }
    }

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

    public boolean isFacingRight() {
        return facingRight;
    }

    public double getCurrentSpeed() {
        return currentSpeed;
    }

    public void resetIdleGif() {
        this.idleGif = loadAndResizeGif("sonicIdle.gif");
    }

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

    public boolean checkCollision(Entity entity) {
        if (!isAlive()) return false;

        return getCoord().getX() + getWidth() >= entity.getCoord().getX() &&
                getCoord().getX() <= entity.getCoord().getX() + entity.getWidth() &&
                getCoord().getY() + getHeight() >= entity.getCoord().getY() &&
                getCoord().getY() <= entity.getCoord().getY() + entity.getHeight();
    }

    public boolean isInBallForm() {
        return inBallForm;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public boolean isVisible() {
        if (immortal) {
            return (immortalTime / 10) % 2 == 0;
        }
        return true;
    }
}
