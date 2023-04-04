import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.Input;
import com.almasb.fxgl.input.UserAction;
import javafx.scene.input.KeyCode;

public class Game extends GameApplication {

    private Entity player1, player2;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("Simple Fighting Game");
        settings.setWidth(800);
        settings.setHeight(600);
    }

    @Override
    protected void initGame() {
        player1 = FXGL.entityBuilder()
                .at(100, 300)
                .viewWithBBox("player1.png")
                .buildAndAttach();

        player1.setScaleY(2.0);

        player2 = FXGL.entityBuilder()
                .at(700, 300)
                .viewWithBBox("player2.png")
                .buildAndAttach();

        player2.setScaleY(2.0);

    }

    @Override
    protected void initInput() {
        Input input = FXGL.getInput();

        input.addAction(new UserAction("Player 1 Move Left") {
            @Override
            protected void onAction() {
                player1.translateX(-5);
            }
        }, KeyCode.A);

        input.addAction(new UserAction("Player 1 Move Right") {
            @Override
            protected void onAction() {
                player1.translateX(5);
            }
        }, KeyCode.D);

        input.addAction(new UserAction("Player 1 Duck") {
            @Override
            protected void onActionBegin() {
                player1.setScaleY(1.0);
                player1.translateY(player1.getHeight() / 2.0);
            }

            @Override
            protected void onActionEnd() {
                player1.setScaleY(2.0);
                player1.translateY(-player1.getHeight() / 2.0);
            }
        }, KeyCode.S);



        input.addAction(new UserAction("Player 2 Move Left") {
            @Override
            protected void onAction() {
                player2.translateX(-5);
            }
        }, KeyCode.LEFT);

        input.addAction(new UserAction("Player 2 Move Right") {
            @Override
            protected void onAction() {
                player2.translateX(5);
            }
        }, KeyCode.RIGHT);

        input.addAction(new UserAction("Player 2 Duck") {
            @Override
            protected void onActionBegin() {
                player2.setScaleY(1.0);
                player2.translateY(player2.getHeight() / 2.0);
            }

            @Override
            protected void onActionEnd() {
                player2.setScaleY(2.0);
                player2.translateY(-player2.getHeight() / 2.0);
            }
        }, KeyCode.DOWN);


    }



    public static void main(String[] args) {
        launch(args);
    }
}