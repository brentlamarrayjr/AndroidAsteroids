package com.brentlrayjr.androidasteroids.Models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.brentlrayjr.androidasteroids.Utils;


public class GameObject {

    int id;
    Texture texture;
    Body body;
    Sprite sprite;

    float x, y, width, height;

    public GameObject(){

        this.id = Utils.generateId();


    }

    public GameObject(Texture texture){

        this.texture = texture;
        sprite = new Sprite(texture);
        width=texture.getWidth();
        height = texture.getHeight();


    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }
}
