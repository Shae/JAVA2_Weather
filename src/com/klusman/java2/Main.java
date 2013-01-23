package com.klusman.java2;


import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.klusman.java2.DefaultDetailsFrag.DefaultDetailsListener;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;
import android.widget.Toast;




public class Main extends Activity implements ButtonFrag.FormListener, DefaultDetailsListener{



	String forecastLength;
	String zipLocation;
	String currentZip;
	Boolean connected = false;
	JSONArray resultsArrayW;
	Context _context = this;
	HashMap<String,	String> _history;
	int daySpan = 1;



	

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
	
	public void myToast(String text){
		CharSequence textIN = text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(Main.this, textIN, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	};// end myToast
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()){
			case R.id.menu_service_go:
				myToast("Go Go Go!");
				//startService(new Intent(this, UpdaterService.class));
				getWeatherData();
				break;
			case R.id.menu_service_stop:
				myToast("Pause Service!");
				//stopService(new Intent(this, UpdaterService.class));
				break;
		}
		return super.onMenuItemSelected(featureId, item);
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

	
	
	/*	////////////// FUTURE IDEAS  //////////////

	// Under Construction
	public void displayCurrent(){  // To Display the current weather as default

	}// end displayCurrent

	public void displayWeatherList(){ // To display the forecast chosen if requested

	}// end displayWeatherList
	 */
	
//	BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {
//
//	    @Override
//	    public void onReceive(Context context, Intent intent) {
//	        Log.w("Network Listener", "Network Type Changed");
//	    }
//	};
	public String forecastLengthPull(){
		
		Log.i("CHOICE", forecastLength);
		
		String days = "1";
		
		if(forecastLength .compareTo("1-Day Forecast") == 0){
			days = "1";
		}
		if(forecastLength .compareTo("2-Day Forecast") == 0){
			days = "2";
		}
		if(forecastLength.compareTo("3-Day Forecast") == 0){
			days = "3";
		}
		if(forecastLength.compareTo("4-Day Forecast") == 0){
			days = "4";
		}
		if(forecastLength.compareTo("5-Day Forecast") == 0){
			days = "5";
		} 
		
		return days;
		
	}
	
	public void checkConnection(){
		if (connected  == true){
			Log.i("NETWORK STATUS", "CONNECTED TO WEB");
			Log.i("NETWORK STATUS", com.klusman.java2.webStuff.getConnectionType(this));
			findZip();
		}else{
			currentZip = "";
		}
	}

	public void runService(){
		Intent intent = new Intent(this, UpdaterService.class);
		startService(intent);
		
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_act);
		connected = com.klusman.java2.webStuff.getConnectionStatus(this);
		checkConnection();
		displayData(); // Display default data, if any.
		testViewData();  // Test for Bundles and update data if any

		//runService();


	} // end onCreate

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}// end onCreateOptionsMenu
	
	
//	private Handler messageHandler = new Handler(){
//		public void handleMessage(Message message){
//			//HANDLER CODE BODY
//		}
//	};
	
	
//	Messenger messenger = new Messenger(messageHandler);
//	Intent myIntent = new Intent(getApplicationContext(), Intent.class);
//	myIntent.putExtra("messenger", messenger);
	
	
		
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
		myToast("Under Construction!");

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

	
	
	private void getWeatherData(){
		//String dayString = String.valueOf(daySpan);  // int to string
		String daysREQd = forecastLengthPull();
		String zipCode = currentZip;  //Pull ZipCode from global values

		Log.i("DAYS TO GET", "Pull this many days: " + daysREQd );
		// Concat http address
		String messURL = "http://free.worldweatheronline.com/feed/weather.ashx?q=" + zipCode + "&format=json&num_of_days=" + daysREQd + "&key=2a0cc91795015022122811";
		String qs;

		try{
			qs = URLEncoder.encode(messURL, "UTF-8");  //encode URL
			Log.i("URL to CALL", qs);  // URL test LOG
		} catch(Exception e){
			Log.e("BAD URL", "Encoding Problem");
			qs = "";
		}
		
		URL finalURL;

		try{
			finalURL = new URL(messURL);  
			weatherRequest myREQ = new weatherRequest();
			myREQ.execute(finalURL);

		} catch(MalformedURLException e){
			Log.e("BAD URL", "URL Problem Final");
		}

	}

	
	private class weatherRequest extends AsyncTask<URL, Void, String>{


		@Override
		protected String doInBackground(URL... urls) {
			String response = "";
			for (URL url: urls){
				response = WebConnections.getURLStringResponse(url);
			}
			return response;
		}

		
		
		@Override
		protected void onPostExecute(String result){
			Log.i("URL RESPONSE:", result);

			try{
				JSONObject json = new JSONObject(result);
				JSONObject results = json.getJSONObject("data");
				resultsArrayW = results.getJSONArray("weather");
				int arrayLength = resultsArrayW.length();

				if(resultsArrayW == null){
					Log.i("JSON GET OBJ", "NOT VALID");
					Toast toast = Toast.makeText(_context, "GET JSON FAILED", Toast.LENGTH_SHORT);
					toast.show();

				}else{
					Toast toast = Toast.makeText(_context, "REQUEST RECEIVED!" + String.valueOf(arrayLength), Toast.LENGTH_SHORT);
					toast.show();
					//lineBuild(_context);
					//_history.put("WeatherSave", results.toString());
					SaveStuff.storeObjectFile(_context, "saveDataObj", _history, false);  // save local as JSON obj string
					//SaveStuff.storeStringFile(_context, "saveDataString", results.toString(), false);
				}
			}catch (JSONException e){
				Log.e("JSON ERROR", "JSON ERROR");
			}

		}
	}
}

