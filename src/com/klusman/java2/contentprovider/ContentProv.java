package com.klusman.java2.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

public class ContentProv extends ContentProvider {
	public static final String AUTHORITY = "content://com.klusman.java2.provider";
	public static final Uri CONTENT_URI = Uri.parse(AUTHORITY);
	

	SQLiteDatabase db;
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		//dbHelper = new DbHelper(getContext());
		return true;
	}
	
	@Override
	public String getType(Uri uri) {
		if(uri.getLastPathSegment()== null){
			return "vnd.android.cursor.item/vnd.com.klusmen.java2.provider.table1";
		}else{
			return "vnd.android.cursor.dir/vnd.com.klusman.java2.provider.table1";
		}
			
	}
	
	@Override
	public int update(Uri uri, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	@Override
	public int delete(Uri uri, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}




	@Override
	public Cursor query(Uri uri, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		return null;
	}


}


