package com.brentlrayjr.androidasteroids.Models;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.brentlrayjr.androidasteroids.Utils;


public class GameInfo {

    public String id;
    public Array<Debris> debris;
    public Array<Asteroid> asteroids;
    public Ship ship;
    public Utils.GameType gameType;

    public int score = 0;

    public GameInfo(String id, Utils.GameType gameType){

        this.id = id;
        this.gameType = gameType;

        this.debris = new Array<Debris>();
        this.asteroids = new Array<Asteroid>();
        this.ship = null;


    }


}
