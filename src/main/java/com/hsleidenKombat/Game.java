package com.hsleidenKombat;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.asset.AssetLoaderService;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
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
        player1.addComponent(new PlayerComponent());

        player2 = FXGL.entityBuilder()
                .at(700, 300)
                .with(new AnimationComponent())
                .with(new CollidableComponent(true))
                .type(EntityTypes.PLAYER2)
                .buildAndAttach();

        player2.setScaleY(2.0);
        player2.addComponent(new Player2Component());

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
                player2.getComponent(Player2Component.class).left2();
            }
        }, KeyCode.LEFT);

        input.addAction(new UserAction("Move Right 2") {
            @Override
            protected void onAction() {
                player2.getComponent(Player2Component.class).right2();
            }
        }, KeyCode.RIGHT);

        input.addAction(new UserAction("Move Up 2") {
            @Override
            protected void onAction() {
                player2.getComponent(Player2Component.class).jump2();
            }
        }, KeyCode.UP);


        input.addAction(new UserAction("Duck2") {
            @Override
            protected void onAction() {
                player2.getComponent(Player2Component.class).down2();
            }

            protected void onActionEnd() {
                player2.getComponent(Player2Component.class).downReleased();
            }
        }, KeyCode.DOWN);
    }

        protected void initPhysics () {
            FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER1, EntityTypes.PLAYER2) {
                @Override
                protected void onCollision(Entity player1, Entity player2) {
                    player2.removeFromWorld();
                }
            });
        }
        public static void main (String[]args){
            launch(args);
        }
    }
