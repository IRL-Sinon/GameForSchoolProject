package org.example;

import org.example.logic.Sonic;
import org.example.logic.Enemy;

import java.util.List;
import java.util.Iterator;

public class GameLogic {
    private Sonic sonic; // Represents the Sonic character
    private List<Enemy> enemies; // Stores a list of enemies in the game
    private GameGraphics gameGraphics; // Manages the graphical elements of the game

    // Constructor initializes the game logic with Sonic and a list of enemies
    public GameLogic(Sonic sonic, List<Enemy> enemies, GameGraphics gameGraphics) {
        this.sonic = sonic;
        this.enemies = enemies;
        this.gameGraphics = gameGraphics;
    }

    // Updates the game state by updating Sonic and each enemy
    public void update() {
        if (sonic.isAlive()) {
            // Updates Sonic's state and interactions with enemies
            sonic.update(enemies, gameGraphics.getPlatforms());
            // Updates each enemy's state and interactions with Sonic and platforms
            for (Iterator<Enemy> iterator = enemies.iterator(); iterator.hasNext();) {
                Enemy enemy = iterator.next();
                if (enemy.isAlive()) {
                    enemy.update(sonic, gameGraphics.getPlatforms());
                } else {
                    iterator.remove(); // Remove dead enemy from the list
                }
            }
        }
    }
}
