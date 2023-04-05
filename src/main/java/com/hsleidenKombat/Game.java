package com.hsleidenKombat;


import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.asset.AssetLoaderService;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.BoundingBoxComponent;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.sun.javafx.geom.Point2D;
import com.sun.javafx.geom.Rectangle;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class Game extends GameApplication {

    private Entity player1, player2, punch;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Hsleiden Kombat");
//        settings.setWidth(1000);
//        settings.setHeight(1000);
        settings.setFullScreenFromStart(true);
        settings.setFullScreenAllowed(true);
        settings.setDeveloperMenuEnabled(true);
    }

    @Override
    protected void initGame() {
        
        getGameScene().setBackgroundRepeat("Background7.jpeg");


        player1 = FXGL.entityBuilder()
                .at(100, 300)
                .with(new AnimationComponent())
                .with(new CollidableComponent(true))
                .with(new HealthComponent())
                .bbox(new HitBox(BoundingShape.box(65,135)))
                .type(EntityTypes.PLAYER1)
                .buildAndAttach();

        player1.setScaleY(2.0);
        player1.addComponent(new PlayerComponent());

        player2 = FXGL.entityBuilder()
                .at(700, 300)
                .with(new AnimationComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(65,135)))
                .type(EntityTypes.PLAYER2)
                .buildAndAttach();


        player2.setScaleY(2.0);
        player2.setScaleX(-1);
        player2.addComponent(new PlayerComponent());

        Input input = getInput();



// Bewegingskeybinds voor player1
        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).left();
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).right();
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Move Up") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).jump();
            }
        }, KeyCode.W);

        input.addAction(new UserAction("Duck") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).down();
            }

            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).downReleased();
            }
        }, KeyCode.S);


        // Bewegingskeybinds voor player2
        input.addAction(new UserAction("Move Left 2") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).left();
            }
        }, KeyCode.LEFT);

        input.addAction(new UserAction("Move Right 2") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).right();
            }
        }, KeyCode.RIGHT);

        input.addAction(new UserAction("Move Up 2") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).jump();
            }
        }, KeyCode.UP);

        input.addAction(new UserAction("punch") {
            private Entity punch;

            @Override
            protected void onAction() {
                player2.getComponent(AnimationComponent.class).punch();
                punch = FXGL.entityBuilder()
                        .at(player1.getPosition().add(player1.getScaleX() * 40, 0))
                        .bbox(new HitBox(BoundingShape.box(100,20)))
                        .buildAndAttach();
            }

        }, KeyCode.F);


        input.addAction(new UserAction("Duck2") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).down();
            }

            protected void onActionEnd() {
                player2.getComponent(PlayerComponent.class).downReleased();
            }
        }, KeyCode.DOWN);


    }



    protected void initPhysics () {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER1, EntityTypes.PLAYER2) {
            @Override
            protected void onCollision(Entity player1, Entity player2) {
                player2.setScaleY(3.0);
            }
        });
    }
        public static void main (String[]args){
            launch(args);
        }
    }
