package com.klusman.java2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

public class zipContentProvider extends ContentProvider {

	private static final String AUTH = "com.klusman.java2.zipContentProvider";
	public static final Uri ZIPTABLE_URI = Uri.parse("content://" + AUTH + "/ZipcodeDB");
	
	SQLiteDatabase db;
	//SQLiteOpenHelper dbHelper;
	DBAdapter dbAdaprt;
	
	

	@Override
	public boolean onCreate() {
		dbAdaprt = new DBAdapter(getContext());
		return true;
	}
	
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		//dbAdaprt.getWritableDatabase();
		//dbAdaprt.insertRecord(values);
		return null;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

}
