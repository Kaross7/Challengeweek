package com.hsleidenKombat;

import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import javafx.geometry.Point2D;
import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class FactoryComponent {

    @Spawns("player1")

    public Entity spawnPlayer1(double x, double y) {

        Entity player1 = entityBuilder()
                .at(x, y)
                .with(new AnimationComponent())
                .with(new CollidableComponent(true))
                .with(new HealthComponent())
                .bbox(new HitBox(BoundingShape.box(65, 135)))
                .type(EntityTypes.PLAYER1)
                .build();

        player1.setScaleY(2.0);
        player1.addComponent(new PlayerComponent());
        return player1;
    }

    @Spawns("player2")
    public Entity spawnPlayer2(double x, double y) {

        Entity player2 = entityBuilder()
                .at(x, y)
                .with(new AnimationComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(65, 135)))
                .type(EntityTypes.PLAYER2)
                .build();

        player2.setScaleY(2.0);
        player2.setScaleX(-1);
        player2.addComponent(new PlayerComponent());
        return player2;
    }
    @Spawns("punch")

    public Entity spawnPunch() {
        Entity punch = entityBuilder()
                .with(new AnimationComponent())
                .with(new CollidableComponent(true))
                .bbox(new HitBox(BoundingShape.box(65, 10)))
                .type(EntityTypes.PUNCH)
                .build();
        return punch;
    }
}