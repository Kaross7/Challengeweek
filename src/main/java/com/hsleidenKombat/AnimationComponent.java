package com.hsleidenKombat;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

public class AnimationComponent extends Component {

    private int speed = 0;

    private AnimatedTexture texture;
    private AnimatedTexture texture1;
    private AnimationChannel animIdle, animWalk;
    private AnimationChannel background;

    public AnimationComponent() {
        animIdle = new AnimationChannel(FXGL.getAssetLoader().loadTexture("spritesheet.png").getImage(), 10, 70, 137, Duration.seconds(0.5), 1, 1);
        animWalk = new AnimationChannel(FXGL.getAssetLoader().loadTexture("spritesheet.png").getImage(), 10, 70, 137, Duration.seconds(0.5), 0, 3);
        background = new AnimationChannel(FXGL.getAssetLoader().loadTexture("background1.png").getImage(), 10, 70, 137, Duration.seconds(0.5), 0, 3);


        texture = new AnimatedTexture(animIdle);
        texture1 = new AnimatedTexture(background);
    }

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
        entity.getViewComponent().addChild(texture);
    }

    @Override
    public void onUpdate(double tpf) {
        entity.translateX(speed * tpf);
        if (texture.getAnimationChannel() == animIdle) {
            texture.loopAnimationChannel(animWalk);
        }
        if (speed != 0) {

            speed = (int) (speed * 0.9);

            if (FXGLMath.abs(speed) < 1) {
                speed = 0;
                texture.loopAnimationChannel(animIdle);
            }
        }
    }

    public void moveRight() {
        speed = 150;

        getEntity().setScaleX(1);
    }

    public void moveLeft() {
        speed = -150;

        getEntity().setScaleX(-1);
    }
}

//    public void addBackground(){
//    }