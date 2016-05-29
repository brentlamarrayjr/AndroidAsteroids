package com.brentlrayjr.androidasteroids.Models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.brentlrayjr.androidasteroids.Utils;

import java.util.Random;


public class Asteroid extends GameObject {

    public static final float DEGTORAD =  0.0174532925199432957f;

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

            if(!this.id.equals(asteroid.id)){
                return false;
            }

        }



        return true;
    }



    public Array<Debris> explode(World world){

        Array<Debris> debris = new Array<Debris>();

        float m_blastRadius = 10f;
        float blastPower = 1000f;


        for (int i = 0; i < 3; i++) {
            float angle = (i / (float)3) * 360 * DEGTORAD;
            Vector2 rayDir = new Vector2((float) Math.sin(angle), (float) Math.cos(angle));


            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.DynamicBody;
            bd.fixedRotation = true; // rotation not necessary
            bd.bullet = true; // prevent tunneling at high speed
            bd.linearDamping = 10; // drag due to moving through air
            bd.gravityScale = 0; // ignore gravity
            bd.position.set(this.body.getPosition()); // start at blast center
            bd.linearVelocity.set(Utils.multiply(rayDir, blastPower));
            Body body = world.createBody(bd);


            CircleShape circleShape = new CircleShape();
            circleShape.setRadius(0.05f); // very small

            FixtureDef fd = new FixtureDef();
            fd.shape = circleShape;
            fd.density = 60 / (float)3; // very high - shared across all particles
            fd.friction = 0; // friction not necessary
            fd.restitution = 0.99f; // high restitution to reflect off obstacles
            fd.filter.groupIndex = -1; // particles should not collide with each other
            body.createFixture(fd);

            debris.add(new Debris(body));
        }

        return debris;
    }

}
