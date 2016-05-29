package com.brentlrayjr.androidasteroids.Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.brentlrayjr.androidasteroids.Utils;


public abstract class GameObject {

    public String id;
    public Texture texture;
    public Body body;
    public Sprite sprite;

    public float x, y, width, height;

    public GameObject(){

        this.id = Utils.generateId();


    }

    public GameObject(Texture texture){

        this.texture = texture;
        sprite = new Sprite(texture);
        width=texture.getWidth();
        height = texture.getHeight();


    }








}
