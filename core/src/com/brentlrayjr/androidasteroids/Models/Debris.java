package com.brentlrayjr.androidasteroids.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.utils.Array;
import com.brentlrayjr.androidasteroids.Utils;


public class Debris extends GameObject {



    public Debris(Body body) {

        super(new Texture(Gdx.files.internal("asteroids/debris/debrisR.png")));


        this.body = body;
        this.x = body.getPosition().x;
        this.y = body.getPosition().y;
    }

    public void update(){


        x = this.body.getPosition().x;
        y = this.body.getPosition().y;
        sprite.setRotation(this.body.getAngle());



    }




    @Override
    public boolean equals(Object o) {

        if(!(o instanceof Debris)) {
            return false;

        } else {

            Debris debris = (Debris) o;

            if(!this.id.equals(debris.id)){
                return false;
            }

        }



        return true;
    }


}


