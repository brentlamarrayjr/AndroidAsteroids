package com.brentlrayjr.androidasteroids.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.utils.TimeUtils;
import com.brentlrayjr.androidasteroids.Asteroids;
import com.brentlrayjr.androidasteroids.GameCallbacks;
import com.brentlrayjr.androidasteroids.Models.Asteroid;
import com.brentlrayjr.androidasteroids.Models.Debris;
import com.brentlrayjr.androidasteroids.Models.GameInfo;
import com.brentlrayjr.androidasteroids.Models.Ship;

public class ArenaGameScreen extends GameScreen {





    public ArenaGameScreen(final Asteroids game, GameCallbacks gameCallbacks) {

        super(game, gameCallbacks);
    }


    //Render is a loop called repeatedly to update the canvas and such
    @Override
    public void render(float delta) {


        //Clear the screen with a dark blue color.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //Update camera matrices.
        game.getViewport().getCamera().update();

        //Render SpriteBatch coordinate system with the one specified by the camera.
        game.getBatch().setProjectionMatrix(game.getViewport().getCamera().combined);



        game.getBatch().begin();

        //Draw background
        game.getBatch().draw(background.texture,0, 0, 0, background.position, Gdx.graphics.getWidth(),
                Gdx.graphics.getHeight());
        background.position +=1;




        //draw score on canvas
        game.getFont().draw(game.getBatch(), "Score: " + gameInfo.score, 420, 20);
        boolean compassAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Compass);
        if(compassAvail) {
            game.getFont().draw(game.getBatch(), "X: " + ship.x, 400, 420);
            game.getFont().draw(game.getBatch(), "Y: " + ship.y, 400, 440);
            game.getFont().draw(game.getBatch(), "Z: " + (Gdx.input.getAzimuth()), 400, 460);
            game.getFont().draw(game.getBatch(), "Width: " + game.getWidth(), 400, 480);
            game.getFont().draw(game.getBatch(), "Height: " + Gdx.graphics.getHeight(), 400, 500);
        } else {
            game.getFont().draw(game.getBatch(), "Compass not available", 400, 400);
        }



        processInput();

        ship.x -= 250 * Math.sin(Math.PI / 180 * ship.getRotation())* Gdx.graphics.getDeltaTime();
        ship.y += 250 * Math.cos(Math.PI / 180 * ship.getRotation())* Gdx.graphics.getDeltaTime();

        ship.body.getPosition().x = ship.x;
        ship.body.getPosition().y = ship.y;


        ship.sprite.setPosition(ship.x, ship.y);

        wrap(ship);

        ship.sprite.draw(game.getBatch());

        //draw asteroids
        for (Asteroid asteroid: asteroids) {


            asteroid.move();
            asteroid.sprite.setRotation(MathUtils.radiansToDegrees * asteroid.body.getAngle());
            checkBounds(asteroid);
            asteroid.sprite.draw(game.getBatch());

        }



        game.getBatch().end();







        //Spawn asteroids
        if (TimeUtils.nanoTime() - lastAsteroidSpawnTime > (long) 1000000000 * 3){
            spawnAsteroid();

        }



        //Increment time in physics realm
        world.step(1 / 60f, 6, 2);
    }



    private void processInput() {




        boolean compassAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Compass);
        if(compassAvail) {
            if(Gdx.input.getRoll()>10){

                ship.sprite.rotate(-3);
                ship.setRotation(ship.getRotation() - 3);



            }else if(Gdx.input.getRoll()<-10){

                ship.sprite.rotate(3);
                ship.setRotation(ship.getRotation() + 3);


            }


        }


        //Process user input
        if (Gdx.input.isTouched()){

            //X & Y coordinates of touch position
            Vector3 touchPos = new Vector3();
            touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
            game.getViewport().getCamera().unproject(touchPos);

        }

    }

    public void wrap(Ship ship) {


        float x = ship.x;
        float y = ship.y;

        if(y + ship.height > Gdx.graphics.getHeight()) {

            ship.y = Gdx.graphics.getHeight() - ship.height;

        } else if(y < 0) {

            ship.y = 0;
        }

        if(x + ship.width > Gdx.graphics.getWidth()) {

            ship.x = Gdx.graphics.getWidth() - ship.width;

        } else if(x < 0) {

            ship.x = 0;

        }

        ship.sprite.setPosition(ship.x, ship.y);

    }

    public boolean checkBounds(Asteroid asteroid) {

        float x = asteroid.x;
        float y = asteroid.y;
        boolean isOutside = false;

        if(y > Gdx.graphics.getHeight()) {isOutside = true;}
        else if(y < -asteroid.height) {isOutside = true;}

        if(x > Gdx.graphics.getWidth()) {isOutside = true;}
        else if(x < -asteroid.x) {isOutside = true;}

        asteroid.sprite.setPosition(x, y);

        return isOutside;
    }


    @Override
    public void resize(int width, int height) {

        game.getViewport().update(width, height);
        game.getViewport().apply();


    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    @Override
    public void beginContact(Contact contact) {

    }

    @Override
    public void endContact(Contact contact) {

        if(contact.getFixtureA().getBody().getUserData() instanceof Asteroid){

            gameCallbacks.onGameInfoPrepared(gameInfo);

        }else if(contact.getFixtureA().getBody().getUserData() instanceof Debris){

        }else if(contact.getFixtureA().getBody().getUserData() instanceof Ship){

        }

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }


    @Override
    public void onApiReady(GameInfo gameInfo) {

        this.gameInfo = gameInfo;

        gameInfo.ship = spawnShip();

        gameCallbacks.onQuickGameRequested();


    }

    @Override
    public void onApiDisconnected(){



    }

    @Override
    public void onGameInfoReceived(GameInfo gameInfo) {


    }
}
