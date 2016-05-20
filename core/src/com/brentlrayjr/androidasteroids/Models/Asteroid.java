package com.brentlrayjr.androidasteroids.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;


public class Asteroid extends GameObject {

    public static final float DENSITY = 1;
    public static final float FRICTION = 0;
    public static final float RESTITUTION = 0;

    private Vector2 position;

    public Asteroid(){

        super(new Texture(Gdx.files.internal("asteroids/small/smallR.png")));

        int n = new Random().nextInt(4);

        switch(n){
            case 0:
                position = new Vector2(-10, new Random().nextInt(1024));
                break;
            case 1:
                position = new Vector2(1034, -new Random().nextInt(1024));
                break;
            case 2:
                position = new Vector2(new Random().nextInt(1024), -10);
                break;
            case 3:
                position = new Vector2(-new Random().nextInt(1024), 10);
                break;
        }

        x = position.x;
        y = position.y;





    }

    void attackShip(Ship ship)
    {



        float x = ship.body.getPosition().x -this.body.getPosition().x;
        float y = ship.body.getPosition().y -this.body.getPosition().y;
        x*=60f;
        y*=60f;
        body.setLinearVelocity(x,y);

    }

}
