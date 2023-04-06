package com.hsleidenKombat;

import com.almasb.fxgl.entity.component.Component;

public class HealthComponent extends Component {
    private int health = 100;

    public void decrease(int amount) {
        health -= amount;
        if (health < 0) {
            health = 0;
        }
    }

    public void increase(int amount) {
        health += amount;
        if (health > 100) {
            health = 100;
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}