package com.brentlrayjr.androidasteroids.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;


public class Asteroid extends GameObject {

    public static final float DENSITY = 1;
    public static final float FRICTION = 0;
    public static final float RESTITUTION = 0;
    public static final float SPEED = 200;

    private Vector2 position;
    public Vector2 direction;
    private Vector2 velocity;
    public boolean moving = false;

    public Asteroid(){

        super(new Texture(Gdx.files.internal("asteroids/small/smallR.png")));

        int n = new Random().nextInt(4);

        switch(n){
            case 0: //LEFT
                position = new Vector2(-10, new Random().nextInt(1024));
                break;
            case 1: //RIGHT
                position = new Vector2(1034, -new Random().nextInt(1024));
                break;
            case 2: //BOTTOM
                position = new Vector2(new Random().nextInt(1024), -10);
                break;
            case 3: //TOP
                position = new Vector2(-new Random().nextInt(1024), 1034);
                break;
        }


        x = position.x;
        y = position.y;





    }


    public void attack(Ship ship){


            direction = new Vector2(ship.x, ship.y);
            moving = true;

        float x =0;
        float y = 0;

        if(this.direction.x>this.x){
            x += Asteroid.SPEED * Gdx.graphics.getDeltaTime();
        }else{
            x -= Asteroid.SPEED * Gdx.graphics.getDeltaTime();
        }

        if(this.direction.y>this.y){
            y +=  Asteroid.SPEED * Gdx.graphics.getDeltaTime();
        }else {
            y -=  Asteroid.SPEED * Gdx.graphics.getDeltaTime();
        }

        velocity = new Vector2(x, y);


    }

    public void move(){

            this.x += velocity.x;

            this.y +=  velocity.y;


        sprite.setPosition(this.x, this.y);


    }



    @Override
    public boolean equals(Object o) {

        if(!(o instanceof Asteroid)) {
            return false;

        } else {

            Asteroid asteroid = (Asteroid) o;

            if(this.getId()!= asteroid.getId()){
                return false;
            }

        }



        return true;
    }
}
