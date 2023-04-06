package com.hsleidenKombat;

import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.*;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ShooterFactory implements EntityFactory{

    @Spawns("punch")
    public Entity newpunch(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityTypes.PUNCH)
                .with(new CollidableComponent(true))
                .with(new HealthComponent())
                .viewWithBBox(new Rectangle(10, 2, Color.BLUE))
                .build();
    }
    @Spawns("punch2")
    public Entity newpunch2(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityTypes.PUNCH2)
                .with(new CollidableComponent(true))
                .with(new HealthComponent())
                .viewWithBBox(new Rectangle(10, 2, Color.BLUE))
                .build();
    }


}

