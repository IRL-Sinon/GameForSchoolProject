package org.example;

import org.example.logic.Sonic;
import org.example.logic.Enemy;

import java.util.List;
    // GameLogic takes care of logic for game and updates player annd enemy
public class GameLogic {
    private Sonic sonic; // Represents the Sonic character
    private List<Enemy> enemies; // Stores a list of enemies in the game

    // Constructor initializes the game logic with Sonic and a list of enemies
    public GameLogic(Sonic sonic, List<Enemy> enemies) {
        this.sonic = sonic;
        this.enemies = enemies;
    }

    // Updates the game state by updating Sonic and each enemy
    public void update() {
        if (sonic.isAlive()) {
            // Updates Sonic's state and interactions with enemies
            sonic.update(enemies);
            // Updates each enemy's state and interactions with Sonic
            for (Enemy enemy : enemies) {
                enemy.update(sonic);
            }
        }
    }
}
