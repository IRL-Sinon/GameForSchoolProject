package org.example.logic;

public class Rings {
    private int count;

    public Rings() {
        this.count = 0;
    }

    public void add(int amount) {
        count += amount;
    }

    public void remove(int amount) {
        count -= amount;
        if (count < 0) {
            count = 0;
        }
    }

    public int getCount() {
        return count;
    }

    public void loseRings() {
        if (count >= 50) {
            remove(50);
        } else if (count >= 40) {
            remove(40);
        } else if (count >= 30) {
            remove(30);
        } else if (count >= 20) {
            remove(20);
        } else if (count >= 10) {
            remove(10);
        } else {
            remove(count);
        }
    }
}
