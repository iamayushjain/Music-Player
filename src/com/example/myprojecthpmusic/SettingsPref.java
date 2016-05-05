package com.example.myprojecthpmusic;

import java.util.Locale;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.speech.tts.TextToSpeech;
import android.widget.Toast;


public class SettingsPref extends PreferenceActivity implements TextToSpeech.OnInitListener{
	 TextToSpeech speech;
	 String string;
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 
		 string="Settings";
		 speech=new TextToSpeech(this,this);
		 //speech.speak(string, TextToSpeech.QUEUE_FLUSH,null);
		 addPreferencesFromResource(R.xml.mysetting);
		 
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
				

}
