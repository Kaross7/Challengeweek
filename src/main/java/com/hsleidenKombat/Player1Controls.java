package com.hsleidenKombat;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;

public class Player1Controls {
    private Entity player;
    private KeyCode left, right, down, up;
    private boolean isJumping = false;
    private boolean testlol = false;

    public Player1Controls(Entity player, KeyCode left, KeyCode right, KeyCode down, KeyCode up) {
        this.player = player;
        this.left = left;
        this.right = right;
        this.down = down;
        this.up = up;
    }


    public void init(Input input) {
        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                player.translateX(-5);
            }
        }, left);

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                player.translateX(5);
            }
        }, right);

        input.addAction(new UserAction("Duck") {
            @Override
            protected void onAction() {
                if (isJumping) {
                    player.setScaleY(1.0);
                }
                else{
                    player.setScaleY(1.0);
                    player.setY(332);
                }
            }

            @Override
            protected void onActionEnd() {
                player.setScaleY(2.0);
                player.setY(300);
            }
        }, down);

        input.addAction(new UserAction("Jump") {
            private double originalY = player.getY();
            private double timeInAir = 0.0;
            private final double jumpDuration = 0.5; // in seconden
            private final double jumpHeight = 120.0; // in pixels

            @Override
            protected void onActionBegin() {
                if (!isJumping) {
                    isJumping = true;
                    originalY = player.getY();
                }
            }

            @Override
            protected void onAction() {
                if (isJumping) {
                    timeInAir += 0.016; // 1/60e van een seconde
                    double t = timeInAir / jumpDuration;
                    double newY = originalY - jumpHeight * Math.sin(t * Math.PI);
                    player.setY(newY);
                    if (timeInAir >= jumpDuration) {
                        player.setY(originalY);
                    }
                }
            }

            @Override
            protected void onActionEnd() {
                if (isJumping) {
                    isJumping = false;
                    player.setY(originalY);
                    timeInAir = 0.0;
                }
            }
        }, up);



    }


    public Entity getPlayer() {
        return player;
    }
}
