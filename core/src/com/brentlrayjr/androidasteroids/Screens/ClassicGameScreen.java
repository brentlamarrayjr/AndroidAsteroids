package com.brentlrayjr.androidasteroids.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.TimeUtils;
import com.brentlrayjr.androidasteroids.Asteroids;
import com.brentlrayjr.androidasteroids.Models.Asteroid;
import com.brentlrayjr.androidasteroids.Models.GameObject;
import com.brentlrayjr.androidasteroids.Models.Ship;

import java.util.Iterator;

public class ClassicGameScreen extends GameScreen {



    public ClassicGameScreen(final Asteroids game) {

        super(game);

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
        game.getViewport().getCamera().update();

        //Render SpriteBatch coordinate system with the one specified by the camera.
        game.getBatch().setProjectionMatrix(game.getViewport().getCamera().combined);

        //Spawn a ship
        if (ship == null) {
            ship = spawnShip();
        }




        game.getBatch().begin();




        //draw score on canvas
        game.getFont().draw(game.getBatch(), "Score: " + game.getScore(), 420, 20);
        boolean compassAvail = Gdx.input.isPeripheralAvailable(Input.Peripheral.Compass);
        if(compassAvail) {
            game.getFont().draw(game.getBatch(), "X: " + ship.x, 400, 420);
            game.getFont().draw(game.getBatch(), "Y: " + ship.y, 400, 440);
            game.getFont().draw(game.getBatch(), "Z: " + (Gdx.input.getAzimuth()), 400, 460);
            game.getFont().draw(game.getBatch(), "Width: " + Gdx.graphics.getWidth(), 400, 480);
            game.getFont().draw(game.getBatch(), "Height: " + Gdx.graphics.getHeight(), 400, 500);
        } else {
            game.getFont().draw(game.getBatch(), "Compass not available", 400, 400);
        }



        processInput();

        ship.x -= 150 * Math.sin(Math.PI / 180 * ship.getRotation())* Gdx.graphics.getDeltaTime();
        ship.y += 150 * Math.cos(Math.PI / 180 * ship.getRotation())* Gdx.graphics.getDeltaTime();

        ship.getBody().getPosition().x = ship.getX();
        ship.getBody().getPosition().y = ship.getY();


        ship.getSprite().setPosition(ship.x, ship.y);

        //wrap(ship);

        ship.getSprite().draw(game.getBatch());

        //draw asteroids
        for (Asteroid asteroid: asteroids) {


            asteroid.move();
            asteroid.getSprite().setRotation(MathUtils.radiansToDegrees * asteroid.getBody().getAngle());
            checkBounds(asteroid);
            asteroid.getSprite().draw(game.getBatch());

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

                        ship.getSprite().rotate(-3);
                        ship.setRotation(ship.getRotation() - 3);



                    }else if(Gdx.input.getRoll()<-10){

                        ship.getSprite().rotate(3);
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

        if(y > Gdx.graphics.getHeight()) {

                y = Gdx.graphics.getHeight() - ship.getHeight();

        } else if(y < 0) {

                y = 0;
        }

        if(x > Gdx.graphics.getWidth()) {

                x = Gdx.graphics.getWidth() - ship.getWidth();

        } else if(x < 0) {

                x = 0;

        }

        ship.getSprite().setPosition(x, y);

    }

    public boolean checkBounds(Asteroid asteroid) {

        float x = asteroid.x;
        float y = asteroid.y;
        boolean isOutside = false;

        if(y > Gdx.graphics.getHeight()) {isOutside = true;}
        else if(y < -asteroid.getHeight()) {isOutside = true;}

        if(x > Gdx.graphics.getWidth()) {isOutside = true;}
        else if(x < -asteroid.getWidth()) {isOutside = true;}

        asteroid.getSprite().setPosition(x, y);

        return isOutside;
    }


    @Override
    public void resize(int width, int height) {

        game.getViewport().update(width, height);

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

