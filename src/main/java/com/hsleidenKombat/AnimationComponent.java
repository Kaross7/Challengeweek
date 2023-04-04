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
    private AnimationChannel animIdle, animWalk;

    public AnimationComponent() {
        animIdle = new AnimationChannel(FXGL.getAssetLoader().loadTexture("spritesheet.png").getImage(), 10, 70, 137, Duration.seconds(0.5), 1, 1);
        animWalk = new AnimationChannel(FXGL.getAssetLoader().loadTexture("spritesheet-lopen.png").getImage(), 10, 70, 137, Duration.seconds(0.5), 0, 3);

        texture = new AnimatedTexture(animIdle);
    }

        @Override
        public void onAdded() {
            entity.getTransformComponent().setScaleOrigin(new Point2D(16, 21));
            entity.getViewComponent().addChild(texture);
        }

        @Override
        public void onUpdate(double tpf) {
            entity.translateX(speed * tpf);

            if (speed != 0) {
                texture.loopAnimationChannel(animWalk);
            } else {
                texture.loopAnimationChannel(animIdle);
            }

            speed = (int) (speed * 0.9);

            if (FXGLMath.abs(speed) < 1) {
                speed = 0;
            }
        }

        public void right() {
            speed = 150;

            getEntity().setScaleX(1);
        }

        public void left() {
            speed = -150;

            getEntity().setScaleX(-1);
        }
    }