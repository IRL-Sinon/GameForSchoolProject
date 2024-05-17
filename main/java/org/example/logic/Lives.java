package org.example.logic;

public class Lives {
    private int livesCount;

    public Lives(int initialLives) {
        this.livesCount = initialLives;
    }

    public int getLives() {
        return livesCount;
    }

    public void loseLife() {
        if (livesCount > 0) {
            livesCount--;
        }
    }

    public void gainLife() {
        livesCount++;
    }
}