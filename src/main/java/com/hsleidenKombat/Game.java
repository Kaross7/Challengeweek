package com.hsleidenKombat;


import javafx.scene.control.ProgressBar;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class Game extends GameApplication {

    private Entity player1, player2, punch, punch2;

    private TextField player1NameField, player2NameField;
    private Button startButton;

    private Text title;
    private boolean punchActive = false;
    private boolean punchActive2 = false;

    private ProgressBar healthBar1;
    private ProgressBar healthBar2;
    int player1wins = 0;
    int player2wins = 0;
    private final int MAX_LEVEL = 3;
    private int currentLevel = 1;
    private VBox endBox;



    private void updateHealthBars() {
        int player1Health = player1.getComponent(HealthComponent.class).getHealth();
        int player2Health = player2.getComponent(HealthComponent.class).getHealth();

        healthBar1.setProgress(player1Health / 100.0);
        healthBar2.setProgress(player2Health / 100.0);
    }

    private void createHealthBars() {
        healthBar1 = new ProgressBar();
        healthBar1.setMinWidth(200);
        healthBar1.setMaxWidth(200);
        healthBar1.setTranslateX(50);
        healthBar1.setTranslateY(75);
        healthBar1.setProgress(1);
        healthBar1.setStyle("-fx-accent: red;");

        healthBar2 = new ProgressBar();
        healthBar2.setMinWidth(200);
        healthBar2.setMaxWidth(200);
        healthBar2.setTranslateX(getAppWidth() - 250);
        healthBar2.setTranslateY(75);
        healthBar2.setProgress(1);
        healthBar2.setStyle("-fx-accent: red;");

        getGameScene().addUINode(healthBar1);
        getGameScene().addUINode(healthBar2);
    }


    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Hsleiden Kombat");
        settings.setFullScreenFromStart(true);
        settings.setFullScreenAllowed(true);
        settings.setDeveloperMenuEnabled(true);
    }




    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new ShooterFactory());
        getGameScene().setBackgroundRepeat("login.jpeg");


        createMenu();

        Input input = getInput();


        input.addAction(new UserAction("Move Left") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).left();
                player1.getComponent(AnimationComponent.class).startMoving();
            }

            @Override
            protected void onActionEnd() {
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
                player2.getComponent(AnimationComponent.class).startMoving();

            }

            @Override
            protected void onActionEnd() {
                player2.getComponent(AnimationComponent.class).stopMoving();
            }
        }, KeyCode.LEFT);

        input.addAction(new UserAction("Move Right 2") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).right();
                player2.getComponent(AnimationComponent.class).startMoving();

            }

            @Override
            protected void onActionEnd() {
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
                if (!punchActive) {
                    player1.getComponent(AnimationComponent.class).startPunch();
                    punch = getGameWorld().spawn("punch", player1.getPosition().getX() + 80, player1.getPosition().getY());
                    punchActive = true;
                }
            }

            @Override
            protected void onActionEnd() {
                if (punchActive) {
                    player1.getComponent(AnimationComponent.class).finishPunch();
                    punch.removeFromWorld();
                    punchActive = false;
                }
            }
        }, KeyCode.F);

        input.addAction(new UserAction("Punch2") {
            @Override
            protected void onAction() {
                if (!punchActive2) {
                    player2.getComponent(AnimationComponent.class).startPunch();
                    punch2 = getGameWorld().spawn("punch2", player2.getPosition().getX() - 30, player2.getPosition().getY());
                    punchActive2 = true;
                }
            }

            @Override
            protected void onActionEnd() {
                if (punchActive2) {
                    player2.getComponent(AnimationComponent.class).finishPunch();
                    punch2.removeFromWorld();
                    punchActive2 = false;
                }
            }
        }, KeyCode.L);
    }


        private void createMenu() {
            if (endBox != null) {
                getGameScene().removeUINode(endBox);
            }
            VBox menuBox = new VBox(10);
            StackPane stackPane = new StackPane();
            stackPane.setPrefSize(getAppWidth(), getAppHeight());

        title = new Text("Hsleiden Kombat");
        title.setFont(Font.font("Verdana", 40));
        title.setFill(Color.YELLOWGREEN);
        title.setText(title.getText().toUpperCase());

        stackPane.getChildren().add(title);
        getGameScene().addUINode(stackPane);


        menuBox.setTranslateX(getAppWidth() / 2 - 100);
        menuBox.setTranslateY(getAppHeight() / 2 - 100);


        player1NameField = new TextField();
        player1NameField.setPromptText("Speler 1 naam");
        player1NameField.setMaxWidth(200);

        player2NameField = new TextField();
        player2NameField.setPromptText("Speler 2 naam");
        player2NameField.setMaxWidth(200);

        startButton = new Button("Beginnen");
        startButton.setMaxWidth(200);


        menuBox.getChildren().addAll(title, player1NameField, player2NameField, startButton);
        getGameScene().addUINode(menuBox);

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
        getGameScene().setBackgroundRepeat("login.jpeg");

        FactoryComponent factoryComponent = new FactoryComponent();
        player1 = factoryComponent.spawnPlayer1(100, 300);
        getGameWorld().addEntity(player1);
        player2 = factoryComponent.spawnPlayer2(700, 300);
        getGameWorld().addEntity(player2);

        Text player1NameText = new Text(player1NameField.getText().toUpperCase());
        Text player2NameText = new Text(player2NameField.getText().toUpperCase());

        player1NameText.setFill(Color.YELLOWGREEN);
        player2NameText.setFill(Color.YELLOWGREEN);

        player1NameText.setX(50);
        player1NameText.setY(50);
        player2NameText.setX(getAppWidth() - 150);
        player2NameText.setY(50);

        getGameScene().addUINode(player1NameText);
        getGameScene().addUINode(player2NameText);


        createHealthBars();

        nextLevel();

    }



    private void startlevel1(){
        getGameScene().setBackgroundRepeat("background-1.jpeg");
    }
    private void startlevel2(){
        getGameScene().setBackgroundRepeat("background-2.png");
        player1.getComponent(HealthComponent.class).setHealth(100);
        player2.getComponent(HealthComponent.class).setHealth(100);
        player1.setPosition(100, 300);
        player2.setPosition(700, 300);
        updateHealthBars();
    }

    private void startlevel3(){
        getGameScene().setBackgroundRepeat("background-3.png");
        player1.getComponent(HealthComponent.class).setHealth(100);
        player2.getComponent(HealthComponent.class).setHealth(100);
        player1.setPosition(100, 300);
        player2.setPosition(700, 300);
        updateHealthBars();
    }
    private void createEndScreen() {
        endBox = new VBox(10);
        endBox.setTranslateX(getAppWidth() / 2 - 100);
        endBox.setTranslateY(getAppHeight() / 2 - 100);

        Text endText = new Text();
        endText.setFont(Font.font("Verdana", 30));
        endText.setFill(Color.YELLOWGREEN);

        if(player1wins > player2wins){
            endText.setText(player1NameField.getText() + " wins!");
        } else if(player1wins < player2wins){
            endText.setText(player2NameField.getText() + " wins!");
        }

        Text player1WinsText = new Text(player1NameField.getText() + ": " + player1wins);
        player1WinsText.setFill(Color.YELLOWGREEN);
        player1WinsText.setFont(Font.font("Verdana", 20));

        Text player2WinsText = new Text(player2NameField.getText() + ": " + player2wins);
        player2WinsText.setFill(Color.YELLOWGREEN);
        player2WinsText.setFont(Font.font("Verdana", 20));

        Button restartButton = new Button("Restart");
        restartButton.setMaxWidth(200);
        restartButton.setOnAction(e -> {
            player1.removeFromWorld();
            player2.removeFromWorld();
            // Reset the game
            player1wins = 0;
            player2wins = 0;
            currentLevel = 1;

            // Remove the end screen
            if (endBox != null) {
                getGameScene().removeUINode(endBox);
            }

            // Show the menu
            createMenu();
        });

        endBox.getChildren().addAll(endText, player1WinsText, player2WinsText, restartButton);
        getGameScene().addUINode(endBox);
    }




    protected void initPhysics () {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER1, EntityTypes.PLAYER2) {
            @Override
            protected void onCollision(Entity player1, Entity player2) {
                player1.getComponent(PlayerComponent.class).setcollidedRight(true);
                player2.getComponent(PlayerComponent.class).setcollidedLeft(true);
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER2, EntityTypes.PUNCH) {
            @Override
            protected void onCollision(Entity player2, Entity punch) {
                player2.getComponent(HealthComponent.class).decrease(5);
                player2.setX(player2.getX() + 30);
                System.out.println(player2.getComponent(HealthComponent.class).getHealth());

                updateHealthBars();

                if (player2.getComponent(HealthComponent.class).getHealth() <= 0) {
                    player1wins += 1;
                    nextLevel();
                }
            }
        });

        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(EntityTypes.PLAYER1, EntityTypes.PUNCH2) {
            @Override
            protected void onCollision(Entity player1, Entity punch2) {
                player1.getComponent(HealthComponent.class).decrease(5);
                player1.setX(player1.getX() - 30);
                System.out.println(player1.getComponent(HealthComponent.class).getHealth());

                if (player1.getComponent(HealthComponent.class).getHealth() <= 0) {
                    player2wins += 1;
                    nextLevel();
                }
            }
        });
    }

    public void nextLevel() {
        if (currentLevel > MAX_LEVEL) {
            createEndScreen();
        } else if (currentLevel == 1) {
            startlevel1();
        }else if (currentLevel == 2) {
            startlevel2();
        } else if (currentLevel == 3) {
            startlevel3();
        }

        currentLevel += 1;
    }



    public static void main (String[]args){
            launch(args);
    }
}
