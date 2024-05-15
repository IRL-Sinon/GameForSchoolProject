package org.example.logic;

public class Sonic extends Entity {
    private boolean movingRight;
    private boolean movingLeft;
    private boolean jumping;
    private boolean canJump;
    private boolean inBallForm;
    private double jumpStrength;
    private double gravity;
    private double jumpStep;
    private double acceleration;
    private double deceleration;
    private double maxSpeed;
    private double currentSpeed;
    private boolean alive;
    private Enemy enemy;
    private Rings rings;

    public Sonic(int x, int y, Enemy enemy, Rings rings) {
        super(x, y, 20, 20);
        this.jumpStrength = 15;
        this.gravity = 2;
        this.jumpStep = 0;
        this.acceleration = 0.1;
        this.deceleration = 0.1;
        this.maxSpeed = 10;
        this.currentSpeed = 0;
        this.alive = true;
        this.enemy = enemy;
        this.rings = rings; // Přidáme proměnnou rings
        this.inBallForm = false;
        this.canJump = true;
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
        if (!alive) return;

        if (movingRight) {
            currentSpeed += acceleration;
            currentSpeed = Math.min(currentSpeed, maxSpeed);
        }
        if (movingLeft) {
            currentSpeed -= acceleration;
            currentSpeed = Math.max(currentSpeed, -maxSpeed);
        }

        if (!movingLeft && !movingRight && currentSpeed != 0) {
            currentSpeed += (currentSpeed > 0) ? -deceleration : deceleration;
            if (Math.abs(currentSpeed) < deceleration) {
                currentSpeed = 0;
            }
        }

        coord.x += currentSpeed;

        if (jumping) {
            coord.y -= jumpStep;
            jumpStep -= gravity;
            if (coord.y >= 200) {
                coord.y = 200;
                jumping = false;
                inBallForm = false;
                width = 20;
                height = 20;
                canJump = true;
            }
        } else {
            if (coord.y < 200) {
                coord.y += gravity;
            } else {
                canJump = true;
            }
        }

        if (enemy != null && enemy.isAlive() && checkCollision(enemy)) {
            if (inBallForm) {
                enemy.die();
            } else {
                die();
            }
        }


        if (rings.getCount() > 0) {
            rings.loseRings();
        }
    }

    public boolean isJumping() {
        return jumping;
    }

    public boolean checkCollision(Enemy other) {
        return coord.x + width >= other.coord.x &&
                coord.x <= other.coord.x + other.width &&
                coord.y + height >= other.coord.y &&
                coord.y <= other.coord.y + other.height;
    }

    public void die() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isInBallForm() {
        return inBallForm;
    }
}
