package com.klusman.java2;


import java.util.List;


import com.klusman.java2.DefaultDetailsFrag.DefaultDetailsListener;

import android.location.Address;
import android.location.Geocoder;
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

import android.widget.TextView;
import android.widget.Toast;




public class Main extends Activity implements ButtonFrag.FormListener, DefaultDetailsListener{

	String forecastLength;
	String zipLocation;
	String currentZip;
	Boolean connected = false;



	

	public void testViewData(){  // Test for Bundles
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			forecastLength = extras.getString("Length");
			zipLocation = extras.getString("Zip");
			displayData();
		}
	}

	public void displayData(){
		TextView length = (TextView) findViewById(R.id.textViewDays);
		
		length.setText(forecastLength);
		
		if (currentZip == null){
			findZip();
		}

		TextView zipcode = (TextView) findViewById(R.id.textViewZip);
		
		if(zipLocation != ""){
			zipcode.setText(zipLocation);
		}
	
	}	


	public void constructionToast(){
		CharSequence text = "Currently Under Construction!";
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(Main.this, text, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	};// end constructionToast

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}// end onCreateOptionsMenu

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

	
	
	/*	////////////// FUTURE IDEAS  //////////////

	// Under Construction
	public void displayCurrent(){  // To Display the current weather as default

	}// end displayCurrent

	public void displayWeatherList(){ // To display the forecast chosen if requested

	}// end displayWeatherList
	 */
	public void checkConnection(){
		if (connected  == true){
			Log.i("NETWORK STATUS", "CONNECTED TO WEB");
			Log.i("NETWORK STATUS", com.klusman.java2.webStuff.getConnectionType(this));
			findZip();
		}else{
			currentZip = "";
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_act);
		connected = com.klusman.java2.webStuff.getConnectionStatus(this);
		checkConnection();
		displayData(); // Display default data, if any.
		testViewData();  // Test for Bundles and update data if any



		

	} // end onCreate

	@Override
	public void onWebClick() {
		//constructionToast();
		final String url = "http://www.worldweatheronline.com/country.aspx";
		// Build Intent for web browser
		Intent next = new Intent(Intent.ACTION_VIEW, Uri.parse(url) );
		startActivity(next);

	}

	@Override
	public void onHistClick() {
		constructionToast();

	}

	@Override
	public void onForecastClick() {
		Intent next = new Intent(Main.this, NewForecast.class);
		next.putExtra("ZipPass", currentZip);  // Pass Current Zip Location
		startActivity(next);
	}

	
	
	@Override
	public void onFirstOpen() {



		
	}

	@Override
	public void onSetText() {
		// TODO Auto-generated method stub
		
	}


}

