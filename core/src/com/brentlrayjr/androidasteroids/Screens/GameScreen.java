package com.brentlrayjr.androidasteroids.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.brentlrayjr.androidasteroids.Asteroids;
import com.brentlrayjr.androidasteroids.BodyEditorLoader;
import com.brentlrayjr.androidasteroids.GameCallbacks;
import com.brentlrayjr.androidasteroids.Models.Asteroid;
import com.brentlrayjr.androidasteroids.Models.GameInfo;
import com.brentlrayjr.androidasteroids.Models.Ship;

/**
 * Created by blray on 5/27/2016.
 */
public abstract class GameScreen extends BaseScreen implements ContactListener {

    Asteroids game;
    World world;
    Ship ship;
    Sound explosionSound;
    Sound shootSound;
    Sound thrusterSound;
    Array<Asteroid> asteroids;
    long lastAsteroidSpawnTime;
    BodyEditorLoader loader;
    GameInfo gameInfo;


    public GameScreen(Asteroids game, GameCallbacks gameCallbacks) {

        super(game, gameCallbacks);

        world = new World(new Vector2(0, 0), true);


        asteroids = new Array<Asteroid>();

        loader = new BodyEditorLoader(Gdx.files.internal("bodies.json"));

        ship = spawnShip();

    }

    @Override
    public void render(float delta) {


        //Clear the screen with a dark blue color.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //Update camera matrices.
        game.getViewport().getCamera().update();

        //Render SpriteBatch coordinate system with the one specified by the camera.
        game.getBatch().setProjectionMatrix(game.getViewport().getCamera().combined);
    }


    void spawnAsteroid() {


        Asteroid asteroid = new Asteroid();


        BodyDef bd = new BodyDef();
        bd.position.set(asteroid.x, asteroid.y);
        bd.type = BodyDef.BodyType.DynamicBody;


        FixtureDef fd = new FixtureDef();
        fd.density = Asteroid.DENSITY;
        fd.friction = Asteroid.FRICTION;
        fd.restitution = Asteroid.RESTITUTION;


        asteroid.body = world.createBody(bd);


        loader.attachFixture(asteroid.body, "smallRedAsteroid", fd, asteroid.width);


        asteroids.add(asteroid);

        lastAsteroidSpawnTime = TimeUtils.nanoTime();

        asteroid.attack(ship);

    }

    Ship spawnShip() {

        //Create our ship object
        Ship ship = new Ship(game.getWidth(), game.getHeight());

        //Create a body definition
        BodyDef bodyDef = new BodyDef();
        // We set our body to dynamic. It can move.
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Set physics body's starting position in the world
        bodyDef.position.set(ship.x, ship.y);

        FixtureDef fixtureDefinition = new FixtureDef();
        fixtureDefinition.density = Ship.DENSITY;
        fixtureDefinition.friction = Ship.FRICTION;
        fixtureDefinition.restitution = Ship.RESTITUTION;

        //Create a body from the physics world
        ship.body=world.createBody(bodyDef);

        //Create the body fixture  using the physics body editor loader.
        loader.attachFixture(ship.body, "blueShip", fixtureDefinition, ship.width);

        return ship;
    }


}
