package com.brentlrayjr.androidasteroids.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Ship extends GameObject {



    public static final float DENSITY = 1;
    public static final float FRICTION = 0;
    public static final float RESTITUTION = 0;

    public int rotation = 0;

    public Ship(float x, float y) {

        super(new Texture(Gdx.files.internal("ships/classic/shipB.png")));

        this.width = texture.getWidth();
        this.height = texture.getHeight();

        this.x = x/2-this.width/2;
        this.y = y/2-this.height/2;


    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;

        if(rotation ==360)
            rotation=0;

        if(rotation == -360)
            rotation=0;

    }



}
