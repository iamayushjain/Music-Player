package com.example.myprojecthpmusic;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;


public class MyMusicService extends Service implements OnCompletionListener {
	MediaPlayer player;
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
		public void onCreate() {
			// TODO Auto-generated method stub
			super.onCreate();
			Toast.makeText(this,"CREATE",Toast.LENGTH_LONG).show();
			//Bundle bund=getIntent().getExtra();
			//String s=bund.getString("CurrentDuration");
			//int a=Integer.parseInt(s);
			//String p=bund.getString("Song");
			//int b=Integer.parseInt(p);
			//player=MediaPlayer.create(MyMusicService.this,b);
			
		//	player=MediaPlayer.create(MyMusicService.this,b);
		}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	if(player.isPlaying())
	player.stop();
	}

	@Override
	public void onCompletion(MediaPlayer v) {
		// TODO Auto-generated method stub
		v.stop();
	} @Override
		public int onStartCommand(Intent intent, int flags, int startId) {
			// TODO Auto-generated method stub
		Bundle bund=intent.getExtras();
		String s=bund.getString("CurrentDuration");
		int a=Integer.parseInt(s);
		String p=bund.getString("Song");
		int b=Integer.parseInt(p);
		
		
		if(!player.isLooping()){
		player.start();
	}
	return START_STICKY;
	}
	}

