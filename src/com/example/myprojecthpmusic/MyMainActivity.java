package com.example.myprojecthpmusic;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Camera.ShutterCallback;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils.TruncateAt;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class MyMainActivity extends Activity implements TextToSpeech.OnInitListener{
	ImageButton b1,b2,b3,b4,b5;
	TextView view,nameview,view2;
	ListView lv;
	SeekBar dur;
	MediaPlayer player;
	TextToSpeech speech;
	String string;
	int repeat;
	 
	String path;
	ArrayList<HashMap<String, String>> al;
	ArrayAdapter<String> ad;
	ArrayList<HashMap<String, String>> songsList;
	int songIndex,seekbars;
	Intent intent,i;
	Uri ring;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_main);
		b1=(ImageButton)findViewById(R.id.imageButton1);
		b2=(ImageButton)findViewById(R.id.imageButton2);
		b3=(ImageButton)findViewById(R.id.imageButton3);
		b4=(ImageButton)findViewById(R.id.imageButton4);
		b5=(ImageButton)findViewById(R.id.imageButton5);
		view=(TextView)findViewById(R.id.textView1);
		nameview=(TextView)findViewById(R.id.textView2);
		view2=(TextView)findViewById(R.id.textView3);
		dur=(SeekBar)findViewById(R.id.seekBar1);
		lv=(ListView)findViewById(R.id.listView1);
		b1.setBackgroundColor(Color.DKGRAY);
		b2.setBackgroundColor(Color.WHITE);
		b3.setBackgroundColor(Color.WHITE);
		b4.setBackgroundColor(Color.WHITE);
		b5.setBackgroundColor(Color.DKGRAY);
		lv.setBackgroundColor(Color.TRANSPARENT);
		player=new MediaPlayer();
		intent=new Intent(MyMainActivity.this,MyMusicService.class);
		string="WELCOME";
		speech=new TextToSpeech(this,this);
		b3.setImageResource(R.drawable.pause);
		dur.setBackgroundColor(Color.DKGRAY);
		al= new ArrayList<HashMap<String, String>>();
		ad=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1);
		lv.setAdapter(ad);
		registerForContextMenu(lv);
		
		
		SharedPreferences set=PreferenceManager.getDefaultSharedPreferences(this);
		Boolean b11=set.getBoolean("blue",false);
		Boolean b22=set.getBoolean("green",false);
		Boolean b33=set.getBoolean("yellow",false);
		Boolean b44=set.getBoolean("white",false);
		if(b11==true)
		{
			nameview.setTextColor(Color.BLUE);
			view.setTextColor(Color.BLUE);
			view2.setTextColor(Color.BLUE);
		}
		else if(b22==true)
		{
			nameview.setTextColor(Color.GREEN);
			view.setTextColor(Color.GREEN);
			view2.setTextColor(Color.GREEN);
		}
		else if(b33==true)
		{
			nameview.setTextColor(Color.YELLOW);
			view.setTextColor(Color.YELLOW);
			view2.setTextColor(Color.YELLOW);
		}
		else
		{
			nameview.setTextColor(Color.WHITE);
			view.setTextColor(Color.WHITE);
			view2.setTextColor(Color.WHITE);
		}		

		
		try
		{
		//	path=Environment.getExternalStorageDirectory().getAbsolutePath();
			//path=Environment.getRootDirectory().toString();
		path = "/storage/emulated/0/mp3";
			Toast.makeText(getApplicationContext(), path, 3000).show();
			File f = new File(path);
			 if(f.listFiles(new MyFileFiltermp3()).length>0)
			 {
				 for(File file:f.listFiles(new MyFileFiltermp3()))
								{
					
					 HashMap<String, String>   song = new HashMap<String, String>();
						song.put("songTitle", file.getName().substring(0, (file.getName().length() - 4)));
						song.put("songPath", file.getPath());
						
						al.add(song);
						
					 }
			 }
			 
		}
		
		catch (Exception e) {
			
			e.printStackTrace();
		}
		 songsList = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> songsListData = new ArrayList<HashMap<String, String>>();
		
			songsList=al;
			for (int i = 0; i < songsList.size(); i++) {
				HashMap<String, String> song = songsList.get(i);
				songsListData.add(song);
			}
			try{
			//String path=Environment.getExternalStorageDirectory().getAbsolutePath();
			String path = "/storage/emulated/0/mp3";
			File f=new File(path);
			if(f.isDirectory())
			{
				String name;
				File[] files=f.listFiles(new MyFileFiltermp3());
						for(File file:files)
						{
							name=file.getName();
							name=file.getName().substring(0,file.getName().length()-4);
							ad.add(name);
							ad.notifyDataSetChanged();
						
						}
			}
			}
		catch (Exception e) {
			
			e.printStackTrace();
		}
		
		
		


			 
				 
		
        b1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if(songIndex > 0)
				{
					songIndex =songIndex - 1;
					musicplay(songIndex);
				}
				else
				{
					musicplay(0);
					
				}

			}
		});

        b2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int position = player.getCurrentPosition();

				if(position - 3000 >= 0)
				{
					player.seekTo(position - 3000);
				}else
				{
					player.seekTo(0);
				}
			}
        });
        b3.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// check for already playing
				if(player.isPlaying())
				{
						player.pause();
						//b3.refreshDrawableState();
						b3.setImageResource(R.drawable.play);
				}
				else
				{
						player.start();
						//b3.refreshDrawableState();
						b3.setImageResource(R.drawable.pause);
				}
				}

			
		});
        




        b4.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) 
			{
				int position = player.getCurrentPosition();
				
				if(position + 3000 <= player.getDuration())
				{
					
					player.seekTo(position + 3000);
				}
				else
				{
					
					player.seekTo(player.getDuration());
				}
			}
		});

        b5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				if(songIndex < (songsList.size() - 1)){
					musicplay(songIndex + 1);
					songIndex = songIndex + 1;
				}
				else
				{

					songIndex=0;
					musicplay(songIndex);

				}

			}
		});
    
    	
		
        dur.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				// TODO Auto-generated method stub
				
			}
		});    		
    	lv.setOnItemClickListener(clickb);
	}
    	
	
	
		OnItemClickListener clickb = new OnItemClickListener() 
		{
    	public void onItemClick(android.widget.AdapterView<?> arg0, View arg1, int arg2, long arg3) 
    	{
    		songIndex=arg2;
    	 String info = ((TextView) arg1).getText().toString();
    	    //String address = info.substring(info.length() - 17);
    	Toast.makeText(getApplicationContext(), info, 3000).show();

    		musicplay(songIndex);
    	}
    	};
    	
    	public void musicplay(int Indexsong)
    	{

    	try{

        	player.reset();
    		player.setDataSource(songsList.get(Indexsong).get("songPath"));
    		player.prepare();
    		player.start();
    		String songTitle = songsList.get(Indexsong).get("songTitle");
    		nameview.setText(songTitle);
    		nameview.setSelected(true);
    		nameview.setTypeface(null, Typeface.BOLD);
    		nameview.setSingleLine();
    		nameview.setEllipsize(TruncateAt.MARQUEE);
    		nameview.setHorizontallyScrolling(true);
    		dur.setProgress(0);
    		//seekbars=player.getCurrentPosition()/1000;
    		b3.setImageResource(R.drawable.pause);
    		nameview.setBackgroundColor(Color.DKGRAY);
     		obtainTime();
    		obtainProgress();
    		obtainSeekbar();
    		//dur.setProgress(0);
    		dur.setMax((player.getDuration())/1000);
    		
    			
    	}
    	catch (Exception e) {
    		// TODO: handle exception
    		e.printStackTrace();
		}

    	}
	
    	public void obtainTime()
    	{
    		int duration=player.getDuration();
    		int TotalSeconds=(int)(duration/1000);
    		String time= "";
    		String secondsString = "";
    		int minutes = (int)( TotalSeconds / (60));
    		int seconds = (int) ((TotalSeconds %60));


    		if(seconds < 10){
    			secondsString = "0" + seconds;
    		}
    		else
    		{
    			secondsString = "" + seconds;
    			}
    		time = minutes + ":" + secondsString;
    		view.setText(time);
    		
	  // view2.setText(player.getCurrentPosition());
	 
}
    	public void obtainProgress()
    	{	
    			
	
	//dur.setProgress((int)player.getCurrentPosition());
	//dur.setProgress(100);
//	dur.setProgress(i);

	//  
	/*dur.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
		
		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
						}
		
		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
			// TODO Auto-generated method stub
//			int  duration= player.getDuration();
			//int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);
			seekbars=arg1;
			player.seekTo(arg1*1000);
			}
	});*/

}

public void obtainSeekbar()
{
Thread t=new Thread(){
public void run()
		{
			for(int i=0;i <=((player.getDuration())/1000)||(player.isPlaying());i++)
			{
				
				{
				//dur.setProgress(i);
				try
				{
					sleep(1000);
					seekbars++;
					dur.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
						
						@Override
						public void onStopTrackingTouch(SeekBar arg0) {
							// TODO Auto-generated method stub
										}
						
						@Override
						public void onStartTrackingTouch(SeekBar arg0) {
							// TODO Auto-generated method stub
							
						}
						
						@Override
						public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
							// TODO Auto-generated method stub
//							int  duration= player.getDuration();
							//int currentPosition = utils.progressToTimer(seekBar.getProgress(), totalDuration);
					
							dur.setProgress(arg1);
							arg1=arg1+1;
							seekbars=arg1;
							player.seekTo(arg1*1000);
							//player.getCurrentPosition();
					
				}
					});
				//	i=seekbars;
				}
				
				catch(Exception e)
				{
					e.printStackTrace();
				}
				}}
	}};
	t.start();
}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_my_main, menu);
		return true;
	}

@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch(item.getItemId())
		{
		case R.id.Exit :
			player.release();
			 speech.speak("Byee  Byeee", TextToSpeech.QUEUE_FLUSH,null);
			 Toast.makeText(getBaseContext(), "Exit",3000).show();
			 finish();
			//setVisible(false);
			
			break;
		case R.id.Settings :
			Toast.makeText(getBaseContext(), "Settings",3000).show();
			i=new Intent(MyMainActivity.this,SettingsPref.class);
			startActivityForResult(i,100);
		
			break;
			
		}
		return super.onOptionsItemSelected(item);
		
	}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	if(requestCode==100)
	{
		SharedPreferences set=PreferenceManager.getDefaultSharedPreferences(this);
		Boolean b1=set.getBoolean("blue",false);
		Boolean b2=set.getBoolean("green",false);
		Boolean b3=set.getBoolean("yellow",false);
		Boolean b4=set.getBoolean("white",false);
		if(b1==true)
		{
			nameview.setTextColor(Color.BLUE);
			view.setTextColor(Color.BLUE);
			view2.setTextColor(Color.BLUE);
		}
		else if(b2==true)
		{
			nameview.setTextColor(Color.GREEN);
			view.setTextColor(Color.GREEN);
			view2.setTextColor(Color.GREEN);
		}
		else if(b3==true)
		{
			nameview.setTextColor(Color.YELLOW);
			view.setTextColor(Color.YELLOW);
			view2.setTextColor(Color.YELLOW);
		}
		else
		{
			nameview.setTextColor(Color.WHITE);
			view.setTextColor(Color.WHITE);
			view2.setTextColor(Color.WHITE);
		}		
	}
}

@Override
public void onCreateContextMenu(ContextMenu menu, View v,
		ContextMenuInfo menuInfo) {
	// TODO Auto-generated method stub
	super.onCreateContextMenu(menu, v, menuInfo);
	menu.setHeaderTitle("Select ur Action");
	menu.add(0,v.getId(),0,"Set As Ringtone");
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getTitle()=="Set As Ringtone"){
			Toast.makeText(getBaseContext(), "Set As Ringtone",3000).show();
			ring=Uri.parse("file://"+songsList.get(songIndex).get("songPath"));
			try{
			RingtoneManager.setActualDefaultRingtoneUri(this, RingtoneManager.TYPE_RINGTONE, ring);
			}
			catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
	return true;
	}

@Override
public void onInit(int arg0) {
	// TODO Auto-generated method stub
	if(arg0==speech.SUCCESS)
	{
		int result=speech.setLanguage(Locale.US);
		if(result==TextToSpeech.LANG_MISSING_DATA||result==TextToSpeech.LANG_NOT_SUPPORTED)
		{
			Toast.makeText(getApplicationContext(), "Not Supported", 3000).show();
		}
		else
		{
			 speech.speak(string, TextToSpeech.QUEUE_FLUSH,null);
		}
		
	}	

}
@Override
protected void onDestroy() {
	// TODO Auto-generated method stub
	super.onDestroy();
	player.release();
	finish();
}
}
