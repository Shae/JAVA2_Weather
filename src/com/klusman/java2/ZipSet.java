package com.klusman.java2;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.lang.reflect.Array;

import android.app.Activity;

import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;

import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ZipSet extends Activity {
	String choice;
	int choiceInt;
	String enteredZip;
	String currentZip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zipset);
		final DBAdapter db;
		
		Array[] zipHistory;
		
		/*
		try{
			String destpath = "data/data/com.klusman.java2/databases/zipcodes";
			File f = new File(destpath);
			if(!f.exists()){
				CopyDB(( getBaseContext().getAssets().open("zipcodes")), new FileOutputStream(destpath));
			}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}*/
		
		
		
		db = new DBAdapter(this);
		db.open();
		//Cursor c = db.rawQuery("SELECT * from ZipTESTS", null);
		Cursor x = db.getAllRecords();
		x.moveToFirst();
		
		//Log.d("results", x.getString(x.getColumnIndex("zipcode")));
		//toastZip(x);
		db.close();
		
		
		
		
		
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    choice = extras.getString("ForecastLength");	
		    currentZip = extras.getString("Zippp");
		}
		
		final EditText et = (EditText) findViewById(R.id.editTextZip);
		et.setText(currentZip); // Pre-Set Current Zip Code and Default Entry
		
		
		
		// BUTTON & onClick
		Button btnNext = (Button) findViewById(R.id.btnLocNext);
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Prep Intent and send test data
				db.open();
				Cursor x = db.getAllRecords();
				enteredZip = et.getText().toString();
				Intent next = new Intent(ZipSet.this, Main.class);
				next.putExtra("Length", choice);  // Test Data
				next.putExtra("Zip", enteredZip);  // Test Data
				db.getIdForStringCompare(enteredZip);
				db.close();
				startActivity(next);
			}
		});  // End Button
		
		
		
		
		/*
		//   ADD RECORDS
		db.open();
		long id = db.insertRecord("99999");
		db.close();
		*/
		
		// GET ALL
		db.open();
		Cursor c = db.getAllRecords();
		if(c.moveToFirst())
			{
				do{
					toastZip(c);
				}while (c.moveToNext());
			}
		db.close();
	} // End onCreate
	
	
	
	public void CopyDB(InputStream is, FileOutputStream fos) throws IOException {
		byte[] buffer = new byte[1024];
		int length;
		while ((length = is.read(buffer)) > 0){
			fos.write(buffer, 0, length);
		}
		is.close();
		fos.close();
		
	}

	
/////////////  TOASTS  //////////////
public void toastZip(Cursor c){
	Toast.makeText(this, 
			"ID: " + c.getString(0) + "\n" +
					"ZIPCODE: " + c.getString(1),
					Toast.LENGTH_SHORT).show();
}

	public void constructionToast(){
		CharSequence text = choice;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(ZipSet.this, text, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	};

}
