package com.klusman.java2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
	
//////////////// SET VARIABLES ///////////////////////
	public static final String KEY_ROWID = "id";
	public static final String KEY_ZIPCODE = "zipcode";
	
	private static final String TAG = "DBAdapter";
	private static final String DATABASE_NAME = "ZipcodeDB";
	private static final String DATABASE_TABLE = "Zipcodes"	;	
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = 
			"CREATE TABLE IF NOT EXISTS Zipcodes (id integer primary key autoincrement, zipcode VARCHAR NOT NULL);";
	
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	private final Context _context;
/////////////////////////////////////////////////////////	
	
	
	
	// SET DBAdapter context and DBHelper
	public DBAdapter(Context context){
		this._context = context;
		DBHelper = new DatabaseHelper(_context);
	}  //  End DBAdapter
	
	
	public DBAdapter open() throws SQLException{
		db = DBHelper.getWritableDatabase();
			Log.i("DB PATH", db.getPath().toString());  // See path of database
		return this;
	}  //  END open
	
	
	public DBAdapter close() throws SQLException{
		DBHelper.close();
		return this;
	}  //  END Close
	
	
////////////////  GET /////////////////
		
	
	public Cursor getRecord(long rowId) throws SQLException{
		Cursor mCursor = 
				db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID, KEY_ZIPCODE}, KEY_ROWID + "=" + rowId, null, null, null, null, null);
		if (mCursor != null){
			mCursor.moveToFirst();
		}
		return mCursor;
	}  //  END getRecord
	
	
	public Cursor getAllRecords(){
		return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_ZIPCODE}, null, null, null, null, null);
	}  // END getAllRecords
	
	
	
	public int getIdForStringCompare(String str) {
	    int res;

	    Cursor cursor = db.query(DATABASE_TABLE, new String[] { KEY_ROWID,}, KEY_ZIPCODE + "=?",new String[] { str }, null, null, null, null);
	    if ((cursor != null) && (cursor.getCount() > 0)) {
	        cursor.moveToFirst();
	        res = cursor.getInt(cursor.getColumnIndex(KEY_ZIPCODE));
	    }
	    else {
	        res = -1;
	        Log.i("compareString", "No Matches for " + str);
	    }
	    if (cursor != null) {
	        cursor.close();
	    }
	    return res;
	}

	
	
//////////////	MODIFY ///////////////////

	
	public long insertRecord(String zipcode){
		ContentValues initialValues = new ContentValues();
		initialValues.put(KEY_ZIPCODE, zipcode);
		return db.insert(DATABASE_TABLE, null, initialValues);
	}  //  END insertRecord
	
	
	public boolean deleteRecord(long rowId){
		return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
	}  //  END deleteRecord
	

	public boolean updateRecord(long rowId, String zipcode)
	{
		ContentValues args = new ContentValues();
		args.put(KEY_ZIPCODE, zipcode);
		return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
	}  // END update
	
	
////////////// REMOVE DATABE / TABLE /////////
	
	
	public void dropDB(String DBname){
		_context.deleteDatabase(DBname);
		
	}  //  END Drop Database

	
	public void dropTable(String tableName){
		db.execSQL("DROP " + tableName);
		
	}  //  END Drop Table
	
	
	
	
////////////////////////////////////////
////  Build class Database Helper  ////
///////////////////////////////////////
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		
		public DatabaseHelper(Context context)
		{
			super(context, DATABASE_NAME, null, DATABASE_VERSION);  // SET DB context and info
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
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			Log.w(TAG, "Upgrading database from older version");
			db.execSQL("DROP TABLE IF EXISTS Zipcodes");
			onCreate(db);
			
		}  //  END onUpgrade
	}  //  END DatabaseHelper Class
	
}

