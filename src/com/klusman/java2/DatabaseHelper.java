package com.klusman.java2;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS Zipcodes (_id integer primary key autoincrement, zipcode VARCHAR NOT NULL);";
	
	
	@Override
	public SQLiteDatabase getWritableDatabase() {
		
		return super.getWritableDatabase();
	}

	private static final String TAG = "DatabaseHelper";
	
	
	public DatabaseHelper(Context context, String name, int version)
	{
		super(context, name, null, version);  // SET DB context and info
	}  //  END Super
	
	@Override
	public void onCreate(SQLiteDatabase db) {  
		try {
			db.execSQL(DATABASE_CREATE);  // BUILD DATABASE TABLE
			Log.i("DATABASE TEST", "Create TABLE Zipcodes");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}  //  END onCreate
	
	@Override
	public String getDatabaseName() {
		
		return super.getDatabaseName();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading database from older version");
		db.execSQL("DROP TABLE IF EXISTS Zipcodes");
		onCreate(db);
		
	}  //  END onUpgrade

}
