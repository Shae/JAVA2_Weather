package com.klusman.java2.contentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

public class ContentProv extends ContentProvider {
//	public static final String AUTHORITY = "content://com.klusman.java2.contentprovider.ContentProv";
//	public static final Uri CONTENT_URI = Uri.parse(AUTHORITY);
	
	 public static final String ContentName = "com.klusman.java2.contentprovider.ContentProv";
	 public static final Uri ContentName_URI = Uri.parse("content://" + ContentName + "/history");  // ??
	 
	public static final int PULL_ONE = 1;
	public static final int PULL_ALL = 2;
	int _id = 0;
	
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
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
	
	
/*
	public MatrixCursor result = new MatrixCursor(new String[] {"_id","Date","High","Low","Wind"}); {
		// TODO Auto-generated method stub
		Log.i("TRACE HistoryProvider", "queried history provider");
//		_queryCursor.setNotificationUri(getContext().getContentResolver(), uri);
		MatrixCursor c = _queryCursor;
		Log.i("TRACE HistoryProvider", "found c" + c.toString());
		return c;
	}
	*/
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


