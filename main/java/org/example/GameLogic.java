package org.example;

import org.example.logic.Sonic;
import org.example.logic.Enemy;
import org.example.logic.Lives;

public class GameLogic {
    private Sonic sonic;
    private Enemy enemy;
    private Lives lives;
    private GameTimer gameTimer;

    public GameLogic(Sonic sonic, Enemy enemy, Lives lives, GameTimer gameTimer) {
        this.sonic = sonic;
        this.enemy = enemy;
        this.lives = lives;
        this.gameTimer = gameTimer;
    }

    public void update() {
        sonic.update();
        enemy.update(sonic);

        if (enemy.checkCollision(sonic)) {
            sonic.takeDamage();
            if (!sonic.isAlive()) {
                // Game over logic here
            }
        }
    }
}
