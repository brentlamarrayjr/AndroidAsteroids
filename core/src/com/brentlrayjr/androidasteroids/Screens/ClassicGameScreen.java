package com.brentlrayjr.androidasteroids.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.brentlrayjr.androidasteroids.Asteroids;
import com.brentlrayjr.androidasteroids.Models.Asteroid;
import com.brentlrayjr.androidasteroids.Models.Ship;

import java.util.Iterator;

public class ClassicGameScreen extends GameScreen {



    public ClassicGameScreen(final Asteroids game) {

        super();


        this.game = game;
        camera.setToOrtho(false, game.getWidth(), game.getHeight());

        ship = spawnShip();

        spawnAsteroid();




    }


    //Render is a loop called repeatedly to update the canvas and such
    @Override
    public void render(float delta) {


        //Clear the screen with a dark blue color.
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        //Update camera matrices.
        camera.update();

        //Render SpriteBatch coordinate system with the one specified by the camera.
        game.getBatch().setProjectionMatrix(camera.combined);

        //Spawn a ship
        if (ship == null) {
            ship = spawnShip();
        }

        //Update asteroids with their physics bodies position and rotation
        for (Asteroid asteroid : asteroids) {

            if (asteroid != null) {

                asteroid.attackShip(ship);

                // Update the sprite's position and angle
                asteroid.setX(asteroid.getBody().getPosition().x);
                asteroid.setY(asteroid.getBody().getPosition().y);
                asteroid.getSprite().setPosition(asteroid.getBody().getPosition().x, asteroid.getBody().getPosition().y);

                //Convert our angle from radians to degrees
                asteroid.getSprite().setRotation(MathUtils.radiansToDegrees * asteroid.getBody().getAngle());
            }
        }


        game.getBatch().begin();

        //draw asteroids
        for (Asteroid asteroid: asteroids) {

            asteroid.getSprite().draw(game.getBatch());

        }


        //draw score on canvas
        game.getFont().draw(game.getBatch(), "Score: " + game.getScore(), 420, 20);
        boolean compassAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Compass);
        if(compassAvail) {
            game.getFont().draw(game.getBatch(), "X: " + (Gdx.input.getPitch()), 400, 420);
            game.getFont().draw(game.getBatch(), "Y: " + (Gdx.input.getRoll()), 400, 440);
            game.getFont().draw(game.getBatch(), "Z: " + (Gdx.input.getAzimuth()), 400, 460);
        } else {
            game.getFont().draw(game.getBatch(), "Gyroscope not available", 400, 400);
        }
        ship.getSprite().draw(game.getBatch());
        game.getBatch().end();


        //Process user input
       // if (Gdx.input.isTouched())
            processInput();



        //Make sure the ship stays within the screen bounds
        if (ship.getX() < 0)
            ship.setX(0);
        if (ship.getX() > 480 - 99)
            ship.setX(480 - 99);


        //Spawn objects every three seconds
        if (TimeUtils.nanoTime() - lastAsteroidSpawnTime > (long) 1000000000 * 3) spawnAsteroid();



        //remove any that are beneath the bottom edge of
        // the screen
        Iterator<Asteroid> iterator = asteroids.iterator();
        while (iterator.hasNext()) {
            Asteroid asteroid = iterator.next();
            //if (asteroid.getY() + asteroid.getHeight() < 0 || asteroid.getY() > game.getHeight()) iterator.remove();
            //if (asteroid.getX() + asteroid.getWidth() < 0 || asteroid.getX() > game.getWidth()) iterator.remove();
        }

        //Increment time in physics realm
        world.step(1 / 60f, 6, 2);
    }



    private void processInput() {



        //X & Y coordinates of touch position
        Vector3 touchPos = new Vector3();
        touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);

        //Compares touch position to position on screen horizontally
        int c = Float.compare(touchPos.x, game.getWidth() / 2);




                boolean compassAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Compass);
                if(compassAvail) {
                    if(Gdx.input.getRoll()>20){

                        ship.getSprite().rotate(-1);
                        ship.setRotation(ship.getRotation() - 1);

                    }else if(Gdx.input.getRoll()<-20){

                        ship.getSprite().rotate(1);
                        ship.setRotation(ship.getRotation() + 1);


                    }

                game.getBatch().begin();
                ship.getSprite().draw(game.getBatch());

                game.getBatch().end();
        }

    }

    @Override
    public void resize(int width, int height) {
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

}

