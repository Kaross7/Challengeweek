package com.hsleidenKombat;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class AnimationComponent extends Component {

    private double speed = 1;

    private AnimatedTexture texture;
    private AnimationChannel animIdle, animWalk, animPunch;

    private boolean moving = false;
    private boolean punching = false;


    public AnimationComponent() {
        animIdle = new AnimationChannel(FXGL.getAssetLoader().loadTexture("spritesheet.png").getImage(), 10, 70, 137, Duration.seconds(1), 1, 9);
        animWalk = new AnimationChannel(FXGL.getAssetLoader().loadTexture("spritesheet-lopen.png").getImage(), 10, 87, 137, Duration.seconds(0.5), 0, 3);
        animPunch = new AnimationChannel(FXGL.getAssetLoader().loadTexture("punch.png").getImage(), 10, 120, 137, Duration.seconds(0.3), 1, 3);


        texture = new AnimatedTexture(animIdle);
        texture.loopAnimationChannel(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }

    public void startMoving() {
        moving = true;
    }

    public void stopMoving() {
        moving = false;
    }


    @Override
    public void onUpdate(double tpf) {
        if (moving) {
            if (texture.getAnimationChannel() != animWalk) {
                texture.loopAnimationChannel(animWalk);
            }
        } else if (punching) {
            if (texture.getAnimationChannel() != animPunch) {
                texture.loopAnimationChannel(animPunch);
            }
        } else {
            if (texture.getAnimationChannel() != animIdle) {
                texture.loopAnimationChannel(animIdle);
            }
        }

//        if (punching) {
//            if (texture.getAnimationChannel() != animPunch) {
//                System.out.println("start punch");
//                texture.loopAnimationChannel(animPunch);
//            }
//        } else {
//            System.out.println("stopped with punch");
//            if (texture.getAnimationChannel() == animPunch) {
//                System.out.println("set on idle");
//                texture.loopAnimationChannel(animIdle);
//            }
//        }
    }


    public void right() {
        speed = 150;

        getEntity().setScaleX(1);
    }

    public void left() {
        speed = -150;

        getEntity().setScaleX(-1);
    }

    public void startPunch() {
        punching = true;
//        System.out.println("AnimationComponent: start punch()");
//        texture.loopAnimationChannel(animPunch);
    }

    public void finishPunch() {
        punching = false;
        System.out.println("AnimationComponent: finish punch()");
        System.out.println(punching);
//        texture.loopAnimationChannel(animIdle);
    }
}
