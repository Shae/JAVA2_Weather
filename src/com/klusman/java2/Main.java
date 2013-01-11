package com.klusman.java2;


import java.util.List;

import android.location.Address;
import android.location.Geocoder;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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
	String currentZip;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		displayData(); // Display default data, if any.
		testViewData();  // Test for Bundles and update data if any
		buildPageSetup();  //  build buttons
		findZip();

	} // end onCreate

	public void testViewData(){
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			forecastLength = extras.getString("Length");
			zipLocation = extras.getString("Zip");
			displayData();
		}
	}

	public void displayData(){
		TextView length = (TextView) findViewById(R.id.textViewDays);
		if(forecastLength != ""){
			length.setText(forecastLength);
		}

		TextView zipcode = (TextView) findViewById(R.id.textViewZip);
		if(zipLocation != ""){
			zipcode.setText(zipLocation);
		}
	}

	public void buildPageSetup(){
		// NEW FORECAST BUTTON
		Button btnNEW = (Button) findViewById(R.id.btnNew);
		btnNEW.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Prep Intent and send test data
				Intent next = new Intent(Main.this, NewForecast.class);
				next.putExtra("ZipPass", currentZip);  // Pass Current Zip Location
				startActivity(next);
			}
		});


		// WEB BUTTON
		Button btnWEB = (Button) findViewById(R.id.btnWeb);
		final String url = "http://www.worldweatheronline.com/country.aspx";
		btnWEB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//constructionToast();

				// Build Intent for web browser
				Intent next = new Intent(Intent.ACTION_VIEW, Uri.parse(url) );
				startActivity(next);
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
	}

	public void constructionToast(){
		CharSequence text = "Currently Under Construction!";
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


	public void findZip(){
		LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){  // Check for GPS
			Location loc = lm.getLastKnownLocation("gps");
			Geocoder geo = new Geocoder(this);
			try{
				List<Address> add = geo.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
				for (Address address : add) {
					String post = address.getPostalCode();
					Log.i("POSTAL GPS", post);
					currentZip = post;
				}
			}catch(Exception e){
				e.printStackTrace();  // follow error
			}
		}else if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){  // Check for Network
			Location loc = lm.getLastKnownLocation("network");
			Geocoder geo = new Geocoder(this);
			try{
				List<Address> add = geo.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
				for (Address address : add) {
					String post = address.getPostalCode();
					Log.i("POSTAL NETWORK", post);
					currentZip = post;
				}
			}catch(Exception e){
				e.printStackTrace();  // follow error
			}
		}else{
			Log.i("LOCATION", "GPS and NETWORK disabled");
		}
		
	} // end findZip 
}

/*
		// trying to use XML Parser  // DIDN'T WORK
		Log.i("FIND ZIP", "RUNNING");
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expr = "GeocodeResponse/result/address_component[type=\"postal_code\"]/long_name/text()";
		InputSource iSource = new InputSource("https://maps.googleapis.com/maps/api/geocode/xml?latlng=37.775,-122.4183333&sensor=true");

		try{	
		String zipcode = (String) xpath.evaluate(expr, iSource, XPathConstants.STRING);
		Log.i("ZIPSTRING", zipcode);
		}catch(Exception e){
			Log.i("ZIP ERROR", "NOT WORKING");
			e.printStackTrace();  // follow error
		}
 */