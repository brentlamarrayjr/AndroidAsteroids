package com.brentlrayjr.androidasteroids;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.brentlrayjr.androidasteroids.BaseGameUtils.BaseGameUtils;
import com.brentlrayjr.androidasteroids.Models.GameInfo;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesStatusCodes;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.multiplayer.Invitation;
import com.google.android.gms.games.multiplayer.OnInvitationReceivedListener;
import com.google.android.gms.games.multiplayer.Participant;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.android.gms.games.multiplayer.realtime.RealTimeMessageReceivedListener;
import com.google.android.gms.games.multiplayer.realtime.Room;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.realtime.RoomStatusUpdateListener;
import com.google.android.gms.games.multiplayer.realtime.RoomUpdateListener;

import java.util.List;

public class AndroidLauncher extends AndroidApplication implements OnInvitationReceivedListener, RoomStatusUpdateListener, RealTimeMessageReceivedListener, RoomUpdateListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, GameCallbacks {


	final String TAG = "AA";
	Player player;

	// request codes we use when invoking an external activity
	private static final int RC_RESOLVE = 5000;
	private static final int RC_UNUSED = 5001;
	private static final int RC_SIGN_IN = 9001;

	private Room room;

	private boolean playing = false;

	// at least 2 players required for our game
	final static int MIN_ARENA_PLAYERS = 2;

	GoogleApiClient client;
	ApiCallbacks apiCallbacks;


	// Are we currently resolving a connection failure?
	private boolean mResolvingConnectionFailure = false;
	private boolean apiClientConnected = false;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useGyroscope = true;  //default is false
		config.useAccelerometer = true;
		config.useCompass = true;


		// Create the Google API Client with access to Games
		client = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this)
				.addApi(Games.API).addScope(Games.SCOPE_GAMES)
				.build();


		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		initialize(new Asteroids(this), config);


	}

	@Override
	public void onConnected(@Nullable Bundle bundle) {

		Log.d(TAG, "onConnected(): connected to Google APIs");
		apiClientConnected = true;

		// Set the greeting appropriately on main menu
		player = Games.Players.getCurrentPlayer(client);

		if (player == null) {
			Log.w(TAG, "mGamesClient.getCurrentPlayer() is NULL!");
		} else {

			apiCallbacks.onApiReady(new GameInfo(player.getPlayerId(), Utils.GameType.NONE));
			Games.Invitations.registerInvitationListener(client, this);
		}




	}

	@Override
	public void onConnectionSuspended(int i) {

		Log.d(TAG, "onConnectionSuspended(): attempting to connect");
		apiClientConnected = false;
		client.connect();

	}

	@Override
	public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {


		Log.d(TAG, "onConnectionFailed(): attempting to resolve");
		apiClientConnected = false;

		if (mResolvingConnectionFailure) {
			Log.d(TAG, "onConnectionFailed(): already resolving");
			return;
		}


			mResolvingConnectionFailure = true;
			if (!BaseGameUtils.resolveConnectionFailure(this, client, connectionResult,
					RC_SIGN_IN, getString(R.string.signin_other_error))) {
				mResolvingConnectionFailure = false;
			}


	}

	@Override
	public void onClassicGameSelected() {
		if (apiClientConnected) {

			// auto-match criteria to invite one random automatch opponent.
			// You can also specify more opponents (up to 3).
			Bundle am = RoomConfig.createAutoMatchCriteria(1, 3, 0);


			// build the room config:
			RoomConfig.Builder roomConfigBuilder = makeBasicRoomConfigBuilder();
			roomConfigBuilder.setAutoMatchCriteria(am);
			RoomConfig roomConfig = roomConfigBuilder.build();

			// create room:
			Games.RealTimeMultiplayer.create(client, roomConfig);

			// prevent screen from sleeping during handshake
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

			// go to game screen

		}
	}

	@Override
	public void onQuickGameRequested() {

	}

	@Override
	public void onPartyGameRequested() {

	}


	// create a RoomConfigBuilder that's appropriate for your implementation
		private RoomConfig.Builder makeBasicRoomConfigBuilder() {
			return RoomConfig.builder(this)
					.setMessageReceivedListener(this)
					.setRoomStatusUpdateListener(this);
		}

	// returns whether there are enough players to start the game
	boolean shouldStartGame(Room room) {
		int connectedPlayers = 0;
		for (Participant p : room.getParticipants()) {
			if (p.isConnectedToRoom()) ++connectedPlayers;

		}
		return connectedPlayers >= MIN_ARENA_PLAYERS;
	}

	// Returns whether the room is in a state where the game should be canceled.
	boolean shouldCancelGame(Room room) {
		// TODO: Your game-specific cancellation logic here. For example, you might decide to
		// cancel the game if enough people have declined the invitation or left the room.
		// You can check a participant's status with Participant.getStatus().
		// (Also, your UI should have a Cancel button that cancels the game too)

		return false;
	}


	@Override
	public boolean onApiStatusRequested() {

		return apiClientConnected;
	}

	@Override
	public void onLeaderBoardRequest() {

	}

	@Override
	public void onAchievementEarned(String achievement) {

		Games.Achievements.unlock(client, achievement);


	}

	@Override
	public void onAchievementEarned(String achievement, int increment) {

		Games.Achievements.increment(client, achievement, increment);

	}

	@Override
	public void onScoreSubmitted(int score, String leaderboard) {

		Games.Leaderboards.submitScore(client, leaderboard, score);

	}

	@Override
	public void onGameInfoPrepared(GameInfo gameInfo) {

		try {
			byte[] message = Utils.toByteArray(gameInfo);

			for (Participant p : room.getParticipants()) {
				if (!p.getParticipantId().equals(player.getPlayerId())) {
					Games.RealTimeMultiplayer.sendUnreliableMessage(client, message,
							room.getRoomId(), p.getParticipantId());
				}


			}
		}catch (Exception e){}



	}

	@Override
	public void onRoomCreated(int i, Room room) {
		if (i != GamesStatusCodes.STATUS_OK) {
			// let screen go to sleep
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

			// show error message, return to main screen.
		}

	}

	@Override
	public void onJoinedRoom(int i, Room room) {

	}

	@Override
	public void onLeftRoom(int i, String s) {

	}

	@Override
	public void onRoomConnected(int i, Room room) {

	}

	@Override
	public void onRealTimeMessageReceived(RealTimeMessage realTimeMessage) {

		try {

			// get real-time message
			byte[] b = realTimeMessage.getMessageData();
			GameInfo info = (GameInfo) Utils.toObject(b);

			// process message

		} catch (Exception e){
			e.printStackTrace();
		}


	}



	@Override
	public void onRoomConnecting(Room room) {

	}

	@Override
	public void onRoomAutoMatching(Room room) {

	}

	@Override
	public void onPeerInvitedToRoom(Room room, List<String> list) {

	}

	@Override
	public void onPeerDeclined(Room room, List<String> list) {

		// peer declined invitation -- see if game should be canceled
		if (!playing && shouldCancelGame(room)) {
			Games.RealTimeMultiplayer.leave(client, null, room.getRoomId());
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}

	}

	@Override
	public void onPeerJoined(Room room, List<String> list) {

	}

	@Override
	public void onPeerLeft(Room room, List<String> list) {

		// peer left -- see if game should be canceled
		if (!playing && shouldCancelGame(room)) {
			Games.RealTimeMultiplayer.leave(client, null, room.getRoomId());
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}

	}

	@Override
	public void onConnectedToRoom(Room room) {

	}

	@Override
	public void onDisconnectedFromRoom(Room room) {

	}

	@Override
	public void onPeersConnected(Room room, List<String> peers) {

		if (playing) {
			// add new player to an ongoing game
		} else if (shouldStartGame(room)) {
			// start game!
		}

	}

	@Override
	public void onPeersDisconnected(Room room, List<String> peers) {

		if (playing) {
			// do game-specific handling of this -- remove player's avatar
			// from the screen, etc. If not enough players are left for
			// the game to go on, end the game and leave the room.
		} else if (shouldCancelGame(room)) {
			// cancel the game
			Games.RealTimeMultiplayer.leave(client, null, room.getRoomId());
			getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}

	}

	@Override
	public void onP2PConnected(String s) {

	}

	@Override
	public void onP2PDisconnected(String s) {

	}

	@Override
	public void onInvitationReceived(Invitation invitation) {

		RoomConfig.Builder roomConfigBuilder = makeBasicRoomConfigBuilder();
		roomConfigBuilder.setInvitationIdToAccept(invitation.getInvitationId());
		Games.RealTimeMultiplayer.join(client, roomConfigBuilder.build());

// prevent screen from sleeping during handshake
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

// now, go to game screen

	}

	@Override
	public void onInvitationRemoved(String s) {

	}

	@Override
	public void onApiCallbacksPrepared(ApiCallbacks apiCallbacks){

		this.apiCallbacks = apiCallbacks;

	}



	}


