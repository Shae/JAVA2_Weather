package com.klusman.java2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class zipContentProvider extends ContentProvider {

	private static final String AUTH = "com.klusman.java2.zipContentProvider";
	private static final String PATH = "ZipcodeDB";
	private static final String TAG = "zipContentProvider";
	private static final String DATABASE_NAME = "ZipcodeDB";
	static final String DATABASE_TABLE = "Zipcodes"	;	
	private static final int DATABASE_VERSION = 1;
	private SQLiteDatabase sqlDB;
	private DatabaseHelper dbHelper;
	
	public static final Uri ZIPTABLE_URI = Uri.parse("content://" + AUTH + "/" + PATH);
	public static final UriMatcher uriMatcher;
	public static final String _ID = "_id";
	MatrixCursor mCursor = new MatrixCursor(new String[] {"_id","zipcode"});
	Context _context;
	
	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI(AUTH, "", 1);  
		uriMatcher.addURI(AUTH, "*", 2);
	}
	
	DBAdapter db = new DBAdapter(null);
	
	@Override
	public boolean onCreate() {
		_context = getContext();
		Log.i("ContentProvider", "Power UP");
		return true;
	}
	
	
	
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		sqlDB = dbHelper.getWritableDatabase();
		long rowId = sqlDB.insert(DATABASE_TABLE,"",values);
		if(rowId>0){
			//Uri rowUri = ContentUris.appendId(MyUser.User.ZIPTABLE_URI.buildUpon, id)
		}
		return uri;
	}
	
	
	
	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		return 0;
	}
	
	
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	
	@Override
	public String getType(Uri uri) {
		switch (uriMatcher.match(uri)){
        //---get all terms---
        case 2:  // ALL
           return "vnd.android.cursor.dir/vnd.com.klusman.java2.zipContentProvider.ZipcodeDB ";
        //---get a particular term---
        case 1:                
           return "vnd.android.cursor.item/vnd.com.klusman.java2.zipContentProvider.ZipcodeDB ";
        default:  // ONE
           throw new IllegalArgumentException("Unsupported URI: " + uri); 
		}
	}
	
	
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		//MatrixCursor result = new MatrixCursor(new String[] {"_id","zipcode"});
		db.open();
		Cursor c = db.getAllRecords();
		db.close();
		
		return c;
	}
	
	
}
