package com.brentlrayjr.androidasteroids;

import com.brentlrayjr.androidasteroids.Models.GameInfo;

/**
 * Created by blray on 5/26/2016.
 */
public interface GameCallbacks {

    void onClassicGameSelected();

    void onQuickGameRequested();

    void onPartyGameRequested();

    boolean onApiStatusRequested();

    void onApiCallbacksPrepared(ApiCallbacks apiCallbacks);

    void onLeaderBoardRequest();

    void onAchievementEarned(String achievement);

    void onAchievementEarned(String achievement, int increment);

    void onScoreSubmitted(int score, String leaderboard);

    void onGameInfoPrepared(GameInfo gameInfo);



}
