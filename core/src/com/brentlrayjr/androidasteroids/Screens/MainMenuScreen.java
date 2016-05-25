package com.brentlrayjr.androidasteroids.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.brentlrayjr.androidasteroids.Asteroids;


public class MainMenuScreen extends GameScreen {

    public MainMenuScreen(Asteroids game) {

        super(game);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getViewport().getCamera().update();
        game.getBatch().setProjectionMatrix(game.getViewport().getCamera().combined);

        game.getBatch().begin();
        game.getFont().setColor(Color.RED);
        game.getFont().draw(game.getBatch(), "Android Asteroids", game.getWidth() / 2, game.getHeight() / 2);
        game.getFont().draw(game.getBatch(), "Tap anywhere to begin!", game.getWidth() / 2, game.getHeight() / 2 - 10);
        game.getBatch().end();

        if (Gdx.input.isTouched()) {
            game.setScreen(new ClassicGameScreen(game));
            dispose();
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
