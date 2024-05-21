package org.example;

import org.example.logic.Sonic;
import org.example.logic.Enemy;

import java.util.List;

public class GameLogic {
    private Sonic sonic;
    private List<Enemy> enemies;

    public GameLogic(Sonic sonic, List<Enemy> enemies) {
        this.sonic = sonic;
        this.enemies = enemies;
    }

    public void update() {
        if (sonic.isAlive()) {
            sonic.update(enemies);
            for (Enemy enemy : enemies) {
                enemy.update(sonic);
            }
        }
    }
}
