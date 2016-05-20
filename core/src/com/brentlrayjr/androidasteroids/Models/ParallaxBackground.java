package com.brentlrayjr.androidasteroids.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;


public class ParallaxBackground extends GameObject {

    public int position;
    public ParallaxBackground(){

        this.texture = new Texture(Gdx.files.internal("background.png"));
    }

}
