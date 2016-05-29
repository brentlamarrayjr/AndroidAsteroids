package com.brentlrayjr.androidasteroids;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.brentlrayjr.androidasteroids.Screens.IntroScreen;

public class Asteroids extends Game{


	Viewport viewport;

	SpriteBatch batch;
	BitmapFont font;
	float width, height;
	private GameCallbacks gameCallbacks;


	public Asteroids(GameCallbacks gameCallbacks){

		this.gameCallbacks = gameCallbacks;


	}

	public void create() {
		batch = new SpriteBatch();



		OrthographicCamera camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		viewport = new ScreenViewport(camera);

		width = viewport.getWorldWidth();
		height = viewport.getWorldHeight();

		// Use LibGDX's default Arial font.
		font = new BitmapFont();
		this.setScreen(new IntroScreen(this, gameCallbacks));
	}

	public void render() {
		super.render(); // important!
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}

	public SpriteBatch getBatch() {
		return batch;
	}

	public void setBatch(SpriteBatch batch) {
		this.batch = batch;
	}

	public BitmapFont getFont() {
		return font;
	}

	public void setFont(BitmapFont font) {
		this.font = font;
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

	public Viewport getViewport() {
		return viewport;
	}

	public void setViewport(Viewport viewport) {
		this.viewport = viewport;
	}


}
