package org.example;

import org.example.logic.Sonic;
import org.example.logic.Enemy;
import org.example.logic.Lives;

import java.util.List;

public class GameLogic {
    private Sonic sonic;
    private Enemy enemy;
    private Lives lives;


    public GameLogic(Sonic sonic, Enemy enemy, Lives lives) {
        this.sonic = sonic;
        this.enemy = enemy;
        this.lives = lives;
    }

    public void update() {
        sonic.update((List<Enemy>) enemy);
        enemy.update(sonic);

        if (enemy.checkCollision(sonic)) {
            int knockBackDirection = sonic.getCoord().getX() > enemy.getCoord().getX() ? 1 : -1;
            sonic.takeDamage(knockBackDirection);
            if (!sonic.isAlive()) {
            }
        }
    }
}
