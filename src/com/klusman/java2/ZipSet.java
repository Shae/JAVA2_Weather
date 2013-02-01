package com.klusman.java2;



import java.util.ArrayList;
import java.util.List;


import android.app.Activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class ZipSet extends Activity {
	String choice;
	int choiceInt;
	String enteredZip;
	String currentZip;
	Context _context;
	String SpinChoice;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zipset);
		final DBAdapter db;
		final List<String> list = new ArrayList<String>();
		db = new DBAdapter(this);
		
		
		
		db.open();
		Cursor c = db.getAllRecords();
		c.moveToFirst();
			// ADD ITEMS TO SPINNER	
		do {
			list.add(c.getString(c.getColumnIndex("zipcode")));
		} while (c.moveToNext());
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

				
				enteredZip = et.getText().toString();
				
				Intent next = new Intent(ZipSet.this, Main.class);
				next.putExtra("Length", choice);  // pass Data
				next.putExtra("Zip", enteredZip);  // pass Data
				
				
				//Check to see if that zip code search has been done before
				db.open();
				boolean f = db.getIdForStringCompare(enteredZip);
				if(f == true){
					
					myToast("Zipcode match found, duplate result NOT added to DB ");
					
				}else{
						
					myToast("NO MATCHES, Zip code added to Database");
					db.insertRecord(enteredZip);
				}
				db.close();
				
				startActivity(next);
			}
		});  // End Button
		
		// SPINNER
		Spinner spn = (Spinner) findViewById(R.id.spinner1);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
		spn.setAdapter(spinnerArrayAdapter);
		
		spn.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
				
				int m = arg2;
				// Get the item Selected and Print out in log
				String listItem =  list.get(m);
				Log.i("ITEM SELECTED", listItem);

				SpinChoice = listItem;
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});

		Button spnNext = (Button) findViewById(R.id.button1);
		spnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				
				Intent next = new Intent(ZipSet.this, Main.class);
				next.putExtra("Length", choice);  // pass Data
				next.putExtra("Zip", SpinChoice);  // pass Data
				
				startActivity(next);
			}
		});  // End Button
		
		

		
	
	} // End onCreate


	
/////////////  TOASTS  //////////////
public void toastZip(Cursor c){
	int duration = Toast.LENGTH_SHORT;
	Toast toast = Toast.makeText(this, "ID: " + c.getString(0) + "\n" + "ZIPCODE: " + c.getString(1), duration );
	toast.setGravity(Gravity.BOTTOM, 0, 0);
	toast.show();
}

public void myToast(String text){  // Toast Template
	CharSequence textIN = text;
	int duration = Toast.LENGTH_SHORT;
	Toast toast = Toast.makeText(ZipSet.this, textIN, duration);
	toast.setGravity(Gravity.BOTTOM, 0, 0);
	toast.show();
};// end myToast


	public void constructionToast(){
		CharSequence text = choice;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(ZipSet.this, text, duration);
		toast.setGravity(Gravity.BOTTOM, 0, 0);
		toast.show();
	};

}
