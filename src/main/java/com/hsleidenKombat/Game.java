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
import javafx.stage.Stage;
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
        FactoryComponent factoryComponent = new FactoryComponent();
        player1 = factoryComponent.spawnPlayer1(100, 300);
        getGameWorld().addEntity(player1);
        player2 = factoryComponent.spawnPlayer2(700, 300);
        getGameWorld().addEntity(player2);

        Input input = getInput();

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
