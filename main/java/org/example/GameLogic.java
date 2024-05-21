package org.example;

import org.example.logic.Sonic;
import org.example.logic.Enemy;
import org.example.logic.Lives;

import java.util.List;

public class GameLogic {
    private Sonic sonic;
    private List<Enemy> enemies;
    private Lives lives;

    public GameLogic(Sonic sonic, List<Enemy> enemies, Lives lives) {
        this.sonic = sonic;
        this.enemies = enemies;
        this.lives = lives;
    }

    public void update() {
        sonic.update(enemies);
        for (Enemy enemy : enemies) {
            enemy.update(sonic);
        }

        for (Enemy enemy : enemies) {
            if (enemy.checkCollision(sonic)) {
                int knockBackDirection = sonic.getCoord().getX() > enemy.getCoord().getX() ? 1 : -1;
                sonic.takeDamage(knockBackDirection);
                if (!sonic.isAlive()) {

                }
            }
        }
    }
}
