package org.example.levels;

import org.example.logic.Enemy;
import org.example.logic.Lives;
import org.example.logic.Sonic;

import java.awt.*;
import java.util.List;

public abstract class Level {
    public abstract Sonic getSonic();
    public abstract List<Enemy> getEnemies();
    public abstract Lives getLives();
    public abstract List<Rectangle> getPlatforms();
    public abstract int getLevelEndX();
    public abstract void reset();
    public abstract void spawnPlayer();
    public abstract Image getBackgroundImage();
}
