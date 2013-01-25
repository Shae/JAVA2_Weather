package com.klusman.java2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    // Define the version and database file name  
 private static final String DB_NAME = "java2_weather_zip_history.db";  
 private static final int DB_VERSION = 1;  
       
     // Use a static class to defined the data structure  
     // This will come in very handy if you using Agile   
     // As your development model  
 private static class UserTable {  
     private static final String _DATE = "date";  
     private static final String _ID = "id";  
     private static final String _High = "high";  
     private static final String _LOW = "low";  
     private static final String _WIND = "wind";
 }  

 private SQLiteDatabase db;  

     // Constructor to simplify Business logic access to the repository   
 public DBHelper(Context context) {  

     super(context, DB_NAME, null, DB_VERSION);  
             // Android will look for the database defined by DB_NAME  
             // And if not found will invoke your onCreate method  
     this.db = this.getWritableDatabase();  

 }  

 @Override  
 public void onCreate(SQLiteDatabase db) {  
               
             // Android has created the database identified by DB_NAME  
             // The new database is passed to you vai the db arg  
             // Now it is up to you to create the Schema.  
             // This schema creates a very simple user table 
     db.execSQL(String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY, %s TEXT, %s TEXT, %s TEXT)",  
                     UserTable._DATE, UserTable._ID,  
                     UserTable._High, UserTable._LOW,
                     UserTable._WIND));  

 }  

   
 public String[] getInfo() {  

     String[] info;  
     Cursor cursor;  

     info = new String[3];  

     cursor = this.db.query(UserTable._DATE, new String[] {  
             UserTable._High, UserTable._LOW},   
                             null, null, null, null, null);  

     if (cursor.moveToFirst()) {  

    	 info[0] = cursor.getString(0);  
    	 info[1] = cursor.getString(1);  
    	 info[2] = cursor.getString(2);  
         cursor.close();  
           
     } else {  
           
         try {
			throw new Exception("No User Credentials Found");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
           
     }  

     return info;  

 }  

 @Override  
 public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {  
     // Later when you change the DB_VERSION   
             // This code will be invoked to bring your database  
             // Upto the correct specification  
 }  
}
