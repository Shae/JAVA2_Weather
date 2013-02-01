package com.klusman.java2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter  {
	
//////////////// SET VARIABLES ///////////////////////
	public static final String KEY_ROWID = "_id";
	public static final String KEY_ZIPCODE = "zipcode";
	
	private static final String TAG = "DBAdapter";
	private static final String DATABASE_NAME = "ZipcodeDB";
	static final String DATABASE_TABLE = "Zipcodes"	;	
	private static final int DATABASE_VERSION = 1;
	
	private static final String DATABASE_CREATE = 
			"CREATE TABLE IF NOT EXISTS Zipcodes (_id integer primary key autoincrement, zipcode VARCHAR NOT NULL);";
	
	private DatabaseHelper2 DBHelper;  // From bottom of code
	private SQLiteDatabase db;
	
	private final Context _context;
/////////////////////////////////////////////////////////	
	
	
	
	// SET DBAdapter context and DBHelper
	public DBAdapter(Context context){
		this._context = context;
		DBHelper = new DatabaseHelper2(_context);
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
		Log.i("getAllRecords", "Step 1");
		Cursor c = db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_ZIPCODE}, null, null, null, null, null);
		Log.i("getAllRecords", "Step 2");
		if(c!=null && c.getCount()>0){
			Log.i("getAllRecords", "Data sent");
			c.moveToFirst();
		}else{
			Log.i("getAllRecords", "EMPTY");
		}
		return c;
	}  // END getAllRecords
	
	
	
	public boolean getIdForStringCompare(String zip) {
	    boolean res;
	    
/*
*	arg1 = String dbName = The table name to compile the query against.
*	arg2 = String[] columnNames = A list of which table columns to return. Passing "null" will return all columns.
*	arg3 = String whereClause = Where-clause, i.e. filter for the selection of data, null will select all data.
*	arg4 = String[] selectionArgs = You may include ?s in the "whereClause"". These place holders will get replaced by the values from the selectionArgs array.
*	arg5 = String[] groupBy = A filter declaring how to group rows, null will cause the rows to not be grouped.
*	arg6 = String[] having = Filter for the groups, null means no filter.
*	arg7 = String[] orderBy = Table columns which will be used to order the data, null means no ordering.
*/
	    
	    Cursor cursor = db.query(DATABASE_TABLE, null, "zipcode=?", new String[] {zip}  , null, null, null);
	  
	    if ((cursor != null) && (cursor.getCount() > 0)) {
	        cursor.moveToFirst();
	        res = true;
	    }
	    else {
	        res = false;
	        Log.i("compareString", "No Matches for " + zip);
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
	
	private static class DatabaseHelper2 extends SQLiteOpenHelper
	{
		
		public DatabaseHelper2(Context context)
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

