package com.brentlrayjr.androidasteroids.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Ship extends GameObject {



    public static final float DENSITY = 1;
    public static final float FRICTION = 0;
    public static final float RESTITUTION = 0;

    public int rotation = 0;

    public Ship() {

        super(new Texture(Gdx.files.internal("ships/classic/shipB.png")));

        this.width = texture.getWidth();
        this.height = texture.getHeight();

        this.x = 1024/2-this.width/2;
        this.y = 1024/2-this.height/2;


    }

    public int getRotation() {
        return rotation;
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }

    public void rotateLeft(){}

    public void rotateRight(){}


}
