package com.hsleidenKombat;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getGameScene;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class Game extends GameApplication {

    private Entity player1, player2;
    private Game background1;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Hsleiden Kombat");
        settings.setWidth(2000);
        settings.setHeight(1000);
        settings.setFullScreenAllowed(true);
        settings.setFullScreenFromStart(true);
    }

    @Override
    protected void initGame() {
//        background1 = new Game(FXGL.getAssetLoader().loadTexture("background1.png"));

        getGameScene().setBackgroundRepeat("background2.png");

         player1 = FXGL.entityBuilder()
                .at(100, 300)
                .viewWithBBox("")
                 .with(new CollidableComponent(true))
                 .type(EntityTypes.PLAYER1)
                .buildAndAttach();

        player1.setScaleY(2.0);

        player2 = FXGL.entityBuilder()
                .at(700, 300)
                .viewWithBBox("")
                .with(new CollidableComponent(true))
                .type(EntityTypes.PLAYER2)
                .buildAndAttach();

        player2.setScaleY(2.0);

        Player1Controls player1Controls = new Player1Controls(player1, KeyCode.A, KeyCode.D, KeyCode.S, KeyCode.W);
        player1Controls.init(getInput());

        Player2Controls player2Controls = new Player2Controls(player2, KeyCode.LEFT, KeyCode.RIGHT, KeyCode.DOWN, KeyCode.UP);
        player2Controls.init(getInput());
    }

    protected void initPhysics(){
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER1, EntityTypes.PLAYER2) {
            @Override
            protected void onCollision(Entity player1, Entity player2) {
                player2.removeFromWorld();
            }
        });
    }
    public static void main(String[] args) {
        launch(args);
    }
}