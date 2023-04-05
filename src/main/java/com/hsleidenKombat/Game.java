package com.hsleidenKombat;


import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class Game extends GameApplication {

    private Entity player1, player2, punch;

    private TextField player1NameField, player2NameField;
    private Button startButton;

    private AnimationComponent animationComponent;

    private Text title;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Hsleiden Kombat");
//        settings.setWidth(1000);
//        settings.setHeight(1000);
        settings.setFullScreenFromStart(true);
        settings.setFullScreenAllowed(true);
        settings.setDeveloperMenuEnabled(true);
    }


    private void createMenu() {
        GridPane menuBox = new GridPane();
        menuBox.setHgap(10);
        menuBox.setVgap(10);
        menuBox.setPadding(new Insets(10, 10, 10, 10));
        ColumnConstraints column = new ColumnConstraints();
        column.setHalignment(HPos.CENTER);
        menuBox.getColumnConstraints().add(column);

        StackPane stackPane = new StackPane();
        stackPane.setPrefSize(getAppWidth(), getAppHeight());

        title = new Text("Hsleiden Kombat");
        title.setFont(Font.font("Verdana", 60));
        title.setFill(Color.WHITE);
        title.setText(title.getText().toUpperCase());

        StackPane titlePane = new StackPane(); // Nieuwe StackPane voor alleen de titel
        titlePane.getChildren().add(title);
        stackPane.getChildren().add(titlePane);

        getGameScene().addUINode(stackPane);

        menuBox.setTranslateY(getAppHeight() / 2 - 100);

        player1NameField = new TextField();
        player1NameField.setPromptText("Speler 1 naam");
        player1NameField.setMaxWidth(200);

        player2NameField = new TextField();
        player2NameField.setPromptText("Speler 2 naam");
        player2NameField.setMaxWidth(200);

        startButton = new Button("Beginnen");
        startButton.setMaxWidth(200);

        StackPane menuContainer = new StackPane(); // Nieuwe StackPane voor het menu
        menuContainer.getChildren().add(menuBox);
        menuContainer.setTranslateY(getAppHeight() / 2 - 100);

        menuBox.add(player1NameField, 0, 0);
        menuBox.add(player2NameField, 0, 1);
        menuBox.add(startButton, 0, 2);
        getGameScene().addUINode(menuContainer);

        startButton.setOnAction(e -> {
            String player1Name = player1NameField.getText();
            String player2Name = player2NameField.getText();

            if (!player1Name.isEmpty() && !player2Name.isEmpty()) {
                // Doel: start het spel en verberg het menu scherm
                startGame();
            }
        });
    }


    private void startGame() {
        // Verberg het menu scherm
        this.title.setVisible(false);
        player1NameField.setVisible(false);
        player2NameField.setVisible(false);
        startButton.setVisible(false);

        // Initialize and display game elements here
        // Add your game elements initialization code here
    }


    @Override
    protected void initGame() {

        createMenu();

        getGameScene().setBackgroundRepeat("Background7.jpeg");

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
                player1.getComponent(AnimationComponent.class).startMoving();
//                    animationComponent.startMoving();
            }

            @Override
            protected void onActionEnd() {
//                    animationComponent.stopMoving();
                player1.getComponent(AnimationComponent.class).stopMoving();
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Move Right") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).right();
//                    animationComponent.startMoving();
                player1.getComponent(AnimationComponent.class).startMoving();

            }

            @Override
            protected void onActionEnd() {
//                    animationComponent.stopMoving();
                player1.getComponent(AnimationComponent.class).stopMoving();
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
//                    animationComponent.startMoving();
                player2.getComponent(AnimationComponent.class).startMoving();

            }

            @Override
            protected void onActionEnd() {
//                    animationComponent.stopMoving();
                player2.getComponent(AnimationComponent.class).stopMoving();
            }
        }, KeyCode.LEFT);

        input.addAction(new UserAction("Move Right 2") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).right();
//                    animationComponent.startMoving();
                player2.getComponent(AnimationComponent.class).startMoving();

            }

            @Override
            protected void onActionEnd() {
//                    animationComponent.stopMoving();
                player2.getComponent(AnimationComponent.class).stopMoving();
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

        input.addAction(new UserAction("Punch") {
            @Override
            protected void onAction() {
                player1.getComponent(AnimationComponent.class).startPunch();
            }

            @Override
            protected void onActionEnd() {
                player1.getComponent(AnimationComponent.class).finishPunch();
            }
        }, KeyCode.F);

        input.addAction(new UserAction("Punch2") {
            @Override
            protected void onAction() {
                player2.getComponent(AnimationComponent.class).startPunch();
            }

            @Override
            protected void onActionEnd() {
                player2.getComponent(AnimationComponent.class).finishPunch();
            }
        }, KeyCode.L);

    }



    protected void initPhysics () {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER1, EntityTypes.PLAYER2) {
            @Override
            protected void onCollision(Entity player1, Entity player2) {
                player1.getComponent(PlayerComponent.class).setcollidedRight(true);
                player2.getComponent(PlayerComponent.class).setcollidedLeft(true);
            }
        });
    }
        public static void main (String[]args){
            launch(args);
        }
    }
