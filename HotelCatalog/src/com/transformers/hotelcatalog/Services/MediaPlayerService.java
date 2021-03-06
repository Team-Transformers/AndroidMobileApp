package com.transformers.hotelcatalog.Services;

import com.transformers.hotelcatalog.MainActivity;
import com.transformers.hotelcatalog.R;
import com.transformers.hotelcatalog.R.drawable;
import com.transformers.hotelcatalog.R.raw;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class MediaPlayerService extends Service {

	private MediaPlayer mediaPlayer = null;
	private boolean isPlaying = false;

	private static int classID = 579; // just a number

	public static String START_PLAY = "START_PLAY";

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		if (intent.getBooleanExtra(START_PLAY, false)) {
			play();
		}
		return Service.START_STICKY;
	}

	@SuppressLint("NewApi")
	private void play() {
		if (!isPlaying) {
			isPlaying = true;

			Intent intent = new Intent(this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);

			PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);

			Notification notification = new NotificationCompat.Builder(
					getApplicationContext()).setContentTitle("My Music Player")
					.setContentText("Now Playing: \"Hotel welcome music.mp3\"")
					.setSmallIcon(R.drawable.ic_launcher).setContentIntent(pi)
					.build();

			mediaPlayer = MediaPlayer.create(this, R.raw.song); // change this
																// for your file
			mediaPlayer.setLooping(true); // this will make it loop forever
			mediaPlayer.start();

			startForeground(classID, notification);
		}
	}

	@Override
	public void onDestroy() {
		stop();
	}

	private void stop() {
		if (isPlaying) {
			isPlaying = false;
			if (mediaPlayer != null) {
				mediaPlayer.release();
				mediaPlayer = null;
			}
			stopForeground(true);
		}
	}

}