package org.example;

import org.example.logic.Sonic;
import org.example.logic.Enemy;
import org.example.logic.Rings;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLogic {
    private Sonic sonic;
    private Enemy enemy;
    private Rings rings;

    public GameLogic(Sonic sonic, Enemy enemy) {
        this.sonic = sonic;
        this.enemy = enemy;
        this.rings = new Rings();
    }

    public void update() {
        sonic.update();
        for (Enemy enemy : Enemy.getActiveEnemies()) {
            enemy.update(sonic);
        }

        if (!sonic.isAlive()) {
            return;
        }

        for (Enemy enemy : Enemy.getActiveEnemies()) {
            if (enemy.checkCollision(sonic)) {
                if (sonic.isInBallForm() && sonic.isJumping()) {
                    enemy.die();
                } else if (!sonic.isInBallForm()) {
                    sonic.die();
                }
                sonic.loseRings();
            }
        }
    }

    public void collectRing() {
        rings.add(1);
    }

    public int getRingCount() {
        return rings.getCount();
    }

    public Rings getRings() {
        return rings;
    }
}
