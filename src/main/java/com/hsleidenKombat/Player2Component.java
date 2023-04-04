package com.hsleidenKombat;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.TransformComponent;
import com.almasb.fxgl.input.Input;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class Player2Component extends Component {
    private TransformComponent position;
    private double speed = 0;
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
                if(!isDucking) {
                    isJumping = false;
                    position.setY(300);
                }
            }
        }
        
    }

    public void jump2() {
        if (!isJumping && !isDucking) {
            jumpSpeed = 600;
            isJumping = true;
        }
    }
    public void down2() {
        if (!isJumping && !isDucking) {
            isDucking = true;
            position.setScaleY(1.0);
            position.setY(333);
        }
    }

    public void downReleased() {
        if (isDucking) {
            isDucking= false;
            position.setScaleY(2.0);
            position.setY(300);
        }
    }


    public void left2(){
        position.translateX(-5 * speed);
    }
    public void right2(){
        position.translateX(5 * speed);
    }
}
