package org.example.logic;

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

    public Sonic(int x, int y, int width, int height, Lives lives) {
        super(x, y, width, height);
        this.jumpStrength = 19;
        this.gravity = 2;
        this.jumpStep = 0;
        this.acceleration = 0.5;
        this.deceleration = 0.1;
        this.maxSpeed = 10;
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
        }
        if (movingLeft) {
            currentSpeed -= acceleration;
            currentSpeed = Math.max(currentSpeed, -maxSpeed);
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

    public boolean checkCollision(Enemy enemy) {
        if (!isAlive()) return false;

        return getCoord().getX() + getWidth() >= enemy.getCoord().getX() &&
                getCoord().getX() <= enemy.getCoord().getX() + enemy.getWidth() &&
                getCoord().getY() + getHeight() >= enemy.getCoord().getY() &&
                getCoord().getY() <= enemy.getCoord().getY() + enemy.getHeight();
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
