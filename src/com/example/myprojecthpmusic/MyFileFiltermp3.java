package com.example.myprojecthpmusic;

import java.io.File;
import java.io.FilenameFilter;

public class MyFileFiltermp3 implements FilenameFilter{


@Override
public boolean accept(File arg0, String arg1) {
	if(arg1.endsWith(".mp3")||(arg1.endsWith(".MP3")))
	{
	return true;
	}
else{return false;}	
} 	
}
