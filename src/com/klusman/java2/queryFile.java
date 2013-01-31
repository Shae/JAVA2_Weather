package com.klusman.java2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import android.R.array;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;



public class queryFile  {

	
	public boolean onCreate(int id, String name, String date) {
		
		// get a file to save info to
		// check to see if the data already exists
		//
		
		String _id;
		String Zipcode;
		JSONObject obj = new JSONObject();
		//obj.put("id", _id);
		//obj.put("zipcode", Zipcode);
		
		
		return true;
	}
	
//	@Override
//	public Cursor query(Uri uri, String[] projection, String selection,
//			String[] selectionArgs, String sortOrder) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	public static Object getFile(Context context, String filename, Boolean extrenal ){
		Object obj = new Object();
		
			try {
				File file;  // declare File name
				FileInputStream FileIn;  // declare File Input stream name
				ObjectInputStream OIS;
				
				if(extrenal){  // use the Boolean variable to check and see if the file is external
					file = new File(context.getExternalFilesDir(null), filename);
					FileIn = new FileInputStream(file);
				}else{
					file = new File(filename);
					FileIn = context.openFileInput(filename);
				}
				
				OIS = new ObjectInputStream(FileIn);  // declare Object input stream
				
				try{
					obj = (Object) OIS.readObject();  // try and put the object input stream into a declared object
					
				}catch (ClassNotFoundException e){  // if failed
					Log.e("READ ERROR", "INVALID JAVA OBJECT FILE");
				}
				OIS.close();  // close the stream
				FileIn.close();  // close the file
			}catch(FileNotFoundException e){  //catch errors
				Log.e("READ ERROR", "FILE NOT FOUND: " + filename);
				return null;
			}catch(IOException e){
				Log.e("READ ERROR", "I/O ERROR");	
			}
		
		return obj;
	}
}
