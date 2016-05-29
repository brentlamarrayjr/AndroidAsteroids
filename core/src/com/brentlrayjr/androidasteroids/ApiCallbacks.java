package com.brentlrayjr.androidasteroids;

import com.brentlrayjr.androidasteroids.Models.GameInfo;

/**
 * Created by blray on 5/25/2016.
 */
public interface ApiCallbacks {

    void onApiReady(GameInfo gameInfo);



    void onApiDisconnected();

    void onGameInfoReceived(GameInfo gameInfo);





}
