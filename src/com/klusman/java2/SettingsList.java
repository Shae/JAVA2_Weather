package com.klusman.java2;


import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.CursorLoader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

///////////////////////////////////////////////////////////////////////////////////
// Trying something new, getting the settings values instead of my JSON from API //
///////////////////////////////////////////////////////////////////////////////////

public class SettingsList extends Activity {
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_settings_list);
    
      // Get content provider and cursor
      ContentResolver cr = getContentResolver();
      Cursor cursor = cr.query(Settings.System.CONTENT_URI, null, null, null, null);
    
      // Let activity manage the cursor
      
      ArrayList<String> mArrayList = new ArrayList<String>();
      
      cursor.moveToFirst();
      while(!cursor.isAfterLast()) {
           mArrayList.add(cursor.getString(cursor.getColumnIndex("0"))); //add the item
           cursor.moveToNext();
      }
      
    //startManagingCursor(cursor);
    
      // Get the list view
      ListView listView = (ListView) findViewById(R.id.settings_list);
      String[] from = { Settings.System.NAME, Settings.System.VALUE };
      int[] to = { R.id.textName, R.id.textValue };

      //CursorLoader cLoader = new CursorLoader(this);
      
      
      
      
/// STUCK HERE!!!!!!!!!!!!!!
/*      
      ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
    		  android.R.layout.simple_list_item_1, R.id.textName, R.id.textValue, mArrayList);
  */    
    //  listView.setAdapter(adapter); 
      
		//listView.setAdapter(cLoader);	
    }
}
