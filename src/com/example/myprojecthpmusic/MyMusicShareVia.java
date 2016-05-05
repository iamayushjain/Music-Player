package com.example.myprojecthpmusic;

	import android.media.MediaPlayer;
import android.net.Uri;
	import android.os.Bundle;
import android.provider.MediaStore.Audio.Media;
	import android.app.Activity;
	import android.content.Intent;
	import android.view.Menu;
import android.widget.ImageView;

	public class MyMusicShareVia extends Activity {
		MediaPlayer mp;
		Media m;
		
		ImageView view;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			//setContentView(R.layout.activity_my_main);
			mp=new MediaPlayer();
		//	m.
			//view.setImageURI((Uri)getIntent().getExtras().get(Intent.EXTRA_STREAM));
			//mp.
			
		}

		@Override
		public boolean onCreateOptionsMenu(Menu menu) {
			// Inflate the menu; this adds items to the action bar if it is present.
			getMenuInflater().inflate(R.menu.activity_my_main, menu);
			return true;
		}

	}


