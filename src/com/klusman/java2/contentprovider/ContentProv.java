package com.klusman.java2.contentprovider;

import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.util.Log;

public class ContentProv extends ContentProvider {

	Context _context;
	public static final String ContentName = "com.klusman.java2.contentprovider.ContentProv";
	public static final Uri ContentName_URI = Uri.parse("content://" + ContentName + "/history");  // ??
	 
	public static final int PULL_ONE = 1;
	public static final int PULL_ALL = 2;
	int _id = 0;
	JSONObject newObj;
	
	
	@Override
	public boolean onCreate() {
		getStoredData();
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private HashMap<String, String> getStoredData(){  // Trying to pull from history ?!?!?!?  so lost :P
		Object stored = com.klusman.java2.ReadStuff.readObjectFile(_context, "saveDataObj", false);
		
		HashMap<String, String> myStoredData;
		if(stored == null){
			Log.i("READ DATA", "NO PAST DATA SAVED");
			myStoredData = new HashMap<String, String>();
		}else{
			myStoredData = (HashMap<String, String>) stored;
		}
		return myStoredData;
	}
	
	@Override
	public String getType(Uri uri) {
		return "vnd.android.cursor.dir/vnd.com.klusman.java2.provider";	
	}
	
	
	
	@Override
	public int update(Uri uri, ContentValues arg1, String arg2, String[] arg3) {
		
		return 0;
	}
	
	
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		
		return uri;
	}
	

	
	@Override
	public int delete(Uri uri, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}




	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		MatrixCursor resultMatrix = new MatrixCursor(new String[] {"_id","Date","High","Low","Wind"});
		
// How the hell do I get the resultsArrayW over here?
		// do I do this here or in Main?  ARGH!
// Going to try pulling from history using hashmap
		
		
		/*
			for(int i=0;i < (resultsArrayW.length()) ;i++){       
			
 
 
				String _mId;
				String _mDate;
				String _mHigh;
				String _mLow;
				String _mWind;
		
				// Set Values
				try{
					newObj = resultsArrayW.getJSONObject(i);
		
					_mId = String.valueOf(i);
					_mDate = newObj.getString("date");
					_mHigh = newObj.getString("tempMaxF");
					_mLow = newObj.getString("tempMinF");
					_mWind = newObj.getString("windspeedMiles");
					
					resultMatrix.addRow( new Object[] {  _mId , _mDate, _mHigh, _mLow, _mWind});
				}catch(JSONException e){
					Log.e("JSON", "failure in Content Provider");
				}
				//resultMatrix.addRow( new Object[] {  _mId, _mDate, _mHigh, _mLow, _mWind});

			}

		
		*/

	return resultMatrix;
	}


}


