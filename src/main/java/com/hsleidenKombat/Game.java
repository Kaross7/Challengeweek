package com.hsleidenKombat;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.asset.AssetLoaderService;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.scene.input.KeyCode;

import static com.almasb.fxgl.dsl.FXGLForKtKt.getInput;

public class Game extends GameApplication {

    private Entity player1, player2;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Hsleiden Kombat");
        settings.setWidth(800);
        settings.setHeight(600);
    }

    @Override
    protected void initGame() {

         player1 = FXGL.entityBuilder()
                .at(100, 300)
                .with(new AnimationComponent())
                 .with(new CollidableComponent(true))
                 .type(EntityTypes.PLAYER1)
                .buildAndAttach();


        player1.setScaleY(2.0);

        player2 = FXGL.entityBuilder()
                .at(700, 300)
                .with(new AnimationComponent())
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