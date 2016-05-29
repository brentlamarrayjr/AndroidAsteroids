package com.brentlrayjr.androidasteroids.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
import com.brentlrayjr.androidasteroids.ApiCallbacks;
import com.brentlrayjr.androidasteroids.Asteroids;
import com.brentlrayjr.androidasteroids.BodyEditorLoader;
import com.brentlrayjr.androidasteroids.GameCallbacks;
import com.brentlrayjr.androidasteroids.Models.Asteroid;
import com.brentlrayjr.androidasteroids.Models.ParallaxBackground;
import com.brentlrayjr.androidasteroids.Models.Ship;

public abstract class BaseScreen implements Screen, ApiCallbacks {

    Asteroids game;
    Music themeMusic;
    Array<Asteroid> asteroids;
    long lastAsteroidSpawnTime;
    BodyEditorLoader loader;
    ParallaxBackground background;
    GameCallbacks gameCallbacks;


    public BaseScreen(Asteroids game, GameCallbacks gameCallbacks){

        this.game = game;

        background.texture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.ClampToEdge);


        this.gameCallbacks = gameCallbacks;




    }








}
