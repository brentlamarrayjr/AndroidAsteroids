package com.brentlrayjr.androidasteroids.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.brentlrayjr.androidasteroids.Asteroids;
import com.brentlrayjr.androidasteroids.BodyEditorLoader;
import com.brentlrayjr.androidasteroids.Models.Asteroid;
import com.brentlrayjr.androidasteroids.Models.Ship;

public abstract class GameScreen implements Screen {

    Asteroids game;
    World world;
    Ship ship;
    Sound explosionSound;
    Sound shootSound;
    Sound thrusterSound;
    Music themeMusic;
    OrthographicCamera camera;
    Array<Asteroid> asteroids;
    long lastAsteroidSpawnTime;
    BodyEditorLoader loader;

    public GameScreen(){

        world = new World(new Vector2(0, 0), true);

        // create the camera and the SpriteBatch
        camera = new OrthographicCamera();

        asteroids = new Array<Asteroid>();

        loader = new BodyEditorLoader(Gdx.files.internal("bodies.json"));


    }
    void spawnAsteroid() {


        Asteroid asteroid = new Asteroid();


        BodyDef bd = new BodyDef();
        bd.position.set(asteroid.getX(), asteroid.getY());
        bd.type = BodyDef.BodyType.DynamicBody;


        FixtureDef fd = new FixtureDef();
        fd.density = Asteroid.DENSITY;
        fd.friction = Asteroid.FRICTION;
        fd.restitution = Asteroid.RESTITUTION;


        asteroid.setBody(world.createBody(bd));


        loader.attachFixture(asteroid.getBody(), "smallRedAsteroid", fd, asteroid.getWidth());


        asteroids.add(asteroid);
        lastAsteroidSpawnTime = TimeUtils.nanoTime();

    }

     Ship spawnShip() {

        //Create our ship object
        Ship ship = new Ship();

        //Create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic. It can move.
        bodyDef.type = BodyDef.BodyType.StaticBody;

        // Set physics body's starting position in the world
        bodyDef.position.set(ship.getX(), ship.getY());

        FixtureDef fixtureDefinition = new FixtureDef();
        fixtureDefinition.density = Ship.DENSITY;
        fixtureDefinition.friction = Ship.FRICTION;
        fixtureDefinition.restitution = Ship.RESTITUTION;

        //Create a body from the physics world
        ship.setBody(world.createBody(bodyDef));

        //Create the body fixture  using the physics body editor loader.
        loader.attachFixture(ship.getBody(), "blueShip", fixtureDefinition, ship.getWidth());

        return ship;
    }


}
