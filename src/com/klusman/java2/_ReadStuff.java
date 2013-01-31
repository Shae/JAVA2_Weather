package com.klusman.java2;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import android.content.Context;
import android.util.Log;

public class _ReadStuff {
	public static Object readStringFile(Context context, String filename, Boolean external){
		String content = "";
		
		try{
			File file;
			FileInputStream fin;
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				fin = new FileInputStream(file);
			}else{
				file = new File(filename);
				fin = context.openFileInput(filename);
			}
			BufferedInputStream bin = new BufferedInputStream(fin);
			byte[] contentBytes = new byte[1024];
			int bytesRead = 0;
			StringBuffer contentBuffer = new StringBuffer();
			
			while ((bytesRead = bin.read(contentBytes)) != -1) {
				content = new String(contentBytes, 0, bytesRead);
				contentBuffer.append(content);
			}
			content = contentBuffer.toString();
			fin.close();
		}catch(FileNotFoundException e){
			Log.e("READ ERROR", "FILE NOT FOUND: " + filename);
		}catch(IOException e){
			Log.e("READ ERROR", "I/O ERROR");	
		}
		
		return content;
	}
	

	public static Object readObjectFile(Context context, String filename, Boolean external){
		Object content = new Object();
		try{
			File file;
			FileInputStream fin;
			if(external){
				file = new File(context.getExternalFilesDir(null), filename);
				fin = new FileInputStream(file);
			}else{
				file = new File(filename);
				fin = context.openFileInput(filename);
			}
			ObjectInputStream ois = new ObjectInputStream(fin);
			try{
				content = (Object) ois.readObject();
			}catch (ClassNotFoundException e){
				Log.e("READ ERROR", "INVALID JAVA OBJECT FILE");
			}
			ois.close();
			fin.close();
		}catch(FileNotFoundException e){
			Log.e("READ ERROR", "FILE NOT FOUND: " + filename);
			return null;
		}catch(IOException e){
			Log.e("READ ERROR", "I/O ERROR");	
		}
		
		return content;
	}
	
}
