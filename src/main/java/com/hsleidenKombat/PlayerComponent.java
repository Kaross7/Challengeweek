package com.hsleidenKombat;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.TransformComponent;
import com.almasb.fxgl.input.Input;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class PlayerComponent extends Component {
    private TransformComponent position;
    private double speed = 0.8;
    private double jumpSpeed = 1;
    private boolean isJumping = false;
    private boolean isDucking = false;
    private double gravity = 2500;
    private boolean collidedRight = false;
    private boolean collidedLeft = false;

    private void applyGravity(double tpf) {
        position.translateY(-jumpSpeed * tpf);
        jumpSpeed -= gravity * tpf;
    }
    private void endJump() {
        if (!isDucking) {
            isJumping = false;
            position.setY(260);
        }
    }

    @Override
    public void onUpdate(double tpf) {
        if (isJumping) {
            position.translateY(-jumpSpeed * tpf);
            jumpSpeed -= gravity * tpf;
            if (position.getY() >= 260) {
                if (!isDucking) {
                    isJumping = false;
                    position.setY(260);
                }
            }
        }
    }

    public void jump() {
        if (!isJumping && !isDucking) {
            jumpSpeed = 600;
            isJumping = true;
        }
    }

    public void down() {
        if (!isJumping && !isDucking) {
            isDucking = true;
            position.setScaleY(1.0);
            position.setY(360);
        }
    }

    public void downReleased() {
        if (isDucking) {
            isDucking = false;
            position.setScaleY(2.0);
            position.setY(260);
        }
    }




    public void left() {
//        System.out.println("AnimationComponent: left()");
        if (!collidedLeft) {
            collidedRight = false;
            if (position.getX() > 1) {
            position.translateX(-5 * speed);
            }
        }

    }

    public void right() {
//        System.out.println("AnimationComponent: right()");
        if (!collidedRight) {
            collidedLeft = false;
            if (position.getX() < 735) {
                position.translateX(5 * speed);
            }
        }
    }



    public void setcollidedRight(boolean b) {
        collidedRight = b;
    }
    public void setcollidedLeft(boolean b) {
        collidedLeft = b;
    }
}


