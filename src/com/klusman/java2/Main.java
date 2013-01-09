package com.klusman.java2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;




public class Main extends Activity {

	String forecastLength;
	String zipLocation;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			forecastLength = extras.getString("Length");
			zipLocation = extras.getString("Zip");
		    constructionToast();
		    
		}
		
		TextView length = (TextView) findViewById(R.id.textViewDays);
		if(forecastLength != ""){
		length.setText(forecastLength);
		//length.refreshDrawableState();
		}
		
		TextView zipcode = (TextView) findViewById(R.id.textViewZip);
		if(zipLocation != ""){
		zipcode.setText(zipLocation);
		//length.refreshDrawableState();
		}
// NEW FORECAST BUTTON
		Button btnNEW = (Button) findViewById(R.id.btnNew);
		btnNEW.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Prep Intent and send test data
				Intent next = new Intent(Main.this, NewForecast.class);
				next.putExtra("MainIntent","TEST");  // Test Data
				startActivity(next);
			}
		});
		
		
// WEB BUTTON
		Button btnWEB = (Button) findViewById(R.id.btnWeb);
		btnWEB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			constructionToast();

			}
		});


// HISTORY BUTTON
		Button btnHIST = (Button) findViewById(R.id.btnHistory);
		btnHIST.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				constructionToast();
			}
		});

		
	} // end onCreate

	
	public void constructionToast(){
		CharSequence text = "Currently Under construction!";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(Main.this, text, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	};
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
