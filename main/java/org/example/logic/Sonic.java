package org.example.logic;

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

    public Sonic(int x, int y, int width, int height, Lives lives) {
        super(x, y, width, height);
        this.jumpStrength = 15;
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
            width = 10;
            height = 10;
        }
    }

    public void update() {
        if (!isAlive()) return;

        if (immortal) {
            immortalTime++;
            if (immortalTime > immortalDuration) {
                immortal = false;
                immortalTime = 0;
            }
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

        getCoord().x += currentSpeed;

        if (jumping) {
            getCoord().y -= jumpStep;
            jumpStep -= gravity;
            if (getCoord().y >= 200) {
                getCoord().y = 200;
                jumping = false;
                inBallForm = false;
                width = 20;
                height = 20;
                canJump = true;
            }
        } else {
            if (getCoord().y < 200) {
                getCoord().y += gravity;
            } else {
                canJump = true;
            }
        }
    }

    public void takeDamage() {
        if (isInBallForm()) {
            inBallForm = false;
        } else {
            if (!immortal) {
                lives.loseLife();
                if (lives.getLives() <= 0) {
                    alive = false;
                } else {
                    immortal = true;
                }
            }
        }
    }

    public boolean isInBallForm() {
        return inBallForm;
    }

    public boolean isImmortal() {
        return immortal;
    }
}