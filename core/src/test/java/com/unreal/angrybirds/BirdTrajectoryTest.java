package com.unreal.angrybirds;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BirdTrajectoryTest {
    Bird TestBird;
    Vector2[] TestTrajectory;

    public void CreateTrajectory(Vector2 StartPos,float Velocity,float Angle,float Gravity,int points){
        this.TestTrajectory = new Vector2[points];
        for(int i = 0;  i<100;i++){
            float t = i * 0.1f;
            float x_t = StartPos.x + Velocity * t * (float) Math.cos(Angle);
            float y_t = StartPos.y + Velocity*t*(float)Math.sin(Angle) + 0.5f * Gravity * t * t;
            TestTrajectory[i] = new Vector2(x_t, y_t);
        }
    }

    @Test
    public void testBirdTrajectory() {
        World testWorld = new World(new Vector2(0, -9.81f), true);
        CreateTrajectory(new Vector2(100, 100), 30, 45, 9.81f, 100);
        TestBird = new Bird("TestBird", 5, 100, 100, 30, 30, testWorld);
        for (int i = 0; i < 100; i++) {
            testWorld.step(1 / 60f, 6, 2);
            assertEquals(TestBird.getBirdBody().getPosition().x, TestTrajectory[i].x, 1f, "X position mismatch at step " + i);
            assertEquals(TestBird.getBirdBody().getPosition().y, TestTrajectory[i].y, 1f, "Y position mismatch at step " + i);
        }
    }
}

