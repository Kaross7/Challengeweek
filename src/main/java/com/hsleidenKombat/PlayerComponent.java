package com.hsleidenKombat;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.TransformComponent;
import com.almasb.fxgl.input.Input;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class PlayerComponent extends Component {
    private TransformComponent position;
    private double speed = 100;
    private double jumpSpeed = 0;
    private boolean isJumping = false;
    private boolean isDucking = false;
    private double gravity = 2500;

    @Override
    public void onUpdate(double tpf) {
        speed = tpf * 60;
        if (isJumping) {
            position.translateY(-jumpSpeed * tpf);
            jumpSpeed -= gravity * tpf;
            if (position.getY() >= 300) {
                if (!isDucking) {
                    isJumping = false;
                    position.setY(300);
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
            position.setY(370);
        }
    }

    public void downReleased() {
        if (isDucking) {
            isDucking = false;
            position.setScaleY(2.0);
            position.setY(300);
        }
    }


    public void left() {
        System.out.println("AnimationComponent: left()");
        position.translateX(-5 * speed);
    }

    public void right() {
        System.out.println("AnimationComponent: right()");
        position.translateX(5 * speed);
    }
}
