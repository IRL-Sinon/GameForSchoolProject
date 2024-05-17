package org.example;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameTimer implements ActionListener {
    private int tick;
    private Timer timer;

    public GameTimer(int delay, ActionListener listener) {
        timer = new Timer(delay, listener);
        tick = 0;
    }

    public void start() {
        timer.start();
    }

    public void stop() {
        timer.stop();
    }

    public void setDelay(int delay) {
        timer.setDelay(delay);
    }

    public int getTick() {
        return tick;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        tick++;
    }
}