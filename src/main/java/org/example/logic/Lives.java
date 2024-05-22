package org.example.logic;

public class Lives {
    private int livesCount;
//counts how many lives player have
    public Lives(int initialLives) {
        this.livesCount = initialLives;
    }
//returns how manyy lives player have
    public int getLives() {
        return livesCount;
    }
//logic for losing lives
    public void loseLife() {
        if (livesCount > 0) {
            livesCount--;
        }
    }
//logic for gaining lives if needed
    public void gainLife() {
        livesCount++;
    }
}
