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

    public AnimationComponent() {
        animIdle = new AnimationChannel(FXGL.getAssetLoader().loadTexture("spritesheet.png").getImage(), 10, 70, 137, Duration.seconds(0.5), 1, 1);
        animWalk = new AnimationChannel(FXGL.getAssetLoader().loadTexture("spritesheet.png").getImage(), 10, 70, 137, Duration.seconds(0.5), 0, 3);
        animPunch = new AnimationChannel(FXGL.getAssetLoader().loadTexture("spritesheet.png").getImage(), 10, 70, 137, Duration.seconds(0.5), 4, 6);

        texture = new AnimatedTexture(animIdle);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        if (speed != 0) {
            entity.translateX(speed * tpf);
        }

//        System.out.println("update");
////        System.out.println(texture.getAnimationChannel());
        System.out.println(speed);
//        System.out.println(texture.getAnimationChannel() == animIdle);
//        System.out.println(texture.getAnimationChannel() == animWalk);


        if (speed != 0 && texture.getAnimationChannel() != animWalk) {
            System.out.println("hello");
            texture.loopAnimationChannel(animWalk);
        } else if (speed == 0 && texture.getAnimationChannel() != animIdle) {
            System.out.println("hoi");
            texture.loopAnimationChannel(animIdle);
        }

//        speed = (speed * 0.9);

        if (FXGLMath.abs(speed) < 1) {
            speed = 0;
        }
    }

    public void right() {
        System.out.println("AnimationComponent: right()");
        speed = 150;

        getEntity().setScaleX(1);
    }

    public void left() {
        System.out.println("AnimationComponent: left()");
        speed = -150;

        getEntity().setScaleX(-1);
    }

//    public void punch() {
//        System.out.println("AnimationComponent: punch()");
//        texture.loopAnimationChannel(animPunch);
//    }
}
