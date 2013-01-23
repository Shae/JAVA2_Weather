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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.TextView;
import android.widget.Toast;




public class Main extends Activity implements ButtonFrag.FormListener, DefaultDetailsListener{



	String forecastLength = "1-Day Forecast";  // Holds the forecast Length String (default 1)
	String zipLocation;  //  Holds a passed in zip code Location
	String currentZip;  // Holds the Mobile devices Current Zip Location
	Boolean connected = false;  // Holds the flag for connected or not
	JSONArray resultsArrayW;  // Holds the JSON array from the API Pull
	Context _context = this;  // Holds the Context
	HashMap<String,	String> _history;  // Holds the Hashmap results
	int daySpan = 1;  // Holds the int version of the forecast length requested (default 1)



	

//// LOCAL METHODS	
	public void testViewData(){  // Test for Bundles
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			forecastLength = extras.getString("Length");
			zipLocation = extras.getString("Zip");
			displayData();
		}
	}

	public void displayData(){  // Get Data for TextViews 
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
	
	public void myToast(String text){  // Toast Template
		CharSequence textIN = text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(Main.this, textIN, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	};// end myToast
	
	public void checkConnection(){  // Check Connection before attempting zip or API pull
		if (connected  == true){
			Log.i("NETWORK STATUS", "CONNECTED TO WEB");
			Log.i("NETWORK STATUS", com.klusman.java2.webStuff.getConnectionType(this));
			findZip();
		}else{
			currentZip = "";
		}
	}

	public void findZip(){ // Find zip code for API Pull and default text field entry
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

	public String forecastLengthPull(){  // Get length for API Pull
		
		String days = "1";
		
		if(forecastLength.compareTo("1-Day Forecast") == 0){
			days = "1";
		}
		if(forecastLength.compareTo("2-Day Forecast") == 0){
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
		else{
			days = "1";
		}
		return days;
		
	}
	
	
//// SERVICE Starters & Stoppers
	public void runUpdaterService(){  //  to Run the "UpdaterService" Service
		Intent intent = new Intent(this, UpdaterService.class);
		startService(intent);
		
	}
	
	public void stopUpdaterService(){  //  to Run the "UpdaterService" Service
		Intent intent = new Intent(this, UpdaterService.class);
		stopService(intent);
		
	}
	
	public void runServiceAction(){  // to run the "ServiceAction" Service
		Intent intent = new Intent(this, ServiceAction.class);
		startService(intent);
		
	}
	
	public void stopServiceAction(){  // to run the "ServiceAction" Service
		Intent intent = new Intent(this, ServiceAction.class);
		stopService(intent);
		
	}

	
//// ON CREATE 	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_act);
		connected = com.klusman.java2.webStuff.getConnectionStatus(this);
		checkConnection();
		displayData(); // Display default data, if any.
		testViewData();  // Test for Bundles and update data if any
		_history = getStoredHist();
		//runService();


	} // end onCreate

	
////  OPTIONS MENU  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}// end onCreateOptionsMenu
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()){
			case R.id.menu_pull_weather:
				myToast("Pulling API Data!");
				//startService(new Intent(this, UpdaterService.class));
				getWeatherData();
				break;
			case R.id.menu_test_service:
				myToast("Service Test Placeholder!");
				//stopService(new Intent(this, UpdaterService.class));
				break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	
//// BUTTON HANDLERS
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

	
//// IMPLEMENT LISTENERS
	@Override
	public void onFirstOpen() {



		
	}

	@Override
	public void onSetText() {
		// TODO Auto-generated method stub
		
	}

	
//// GET API DATA
	@SuppressWarnings("unchecked")
	private HashMap<String, String> getStoredHist(){
		Object stored = ReadStuff.readObjectFile(_context, "saveDataObj", false);
		
		HashMap<String, String> myStoredData;
		if(stored == null){
			Log.i("READ DATA", "NO PAST DATA SAVED");
			myStoredData = new HashMap<String, String>();
		}else{
			myStoredData = (HashMap<String, String>) stored;
		}
		return myStoredData;
	}
	
	private void getWeatherData(){  // Pulled from Java1 project
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
					Toast toast = Toast.makeText(_context, String.valueOf(arrayLength) + " day(s) requested data received!", Toast.LENGTH_SHORT);
					toast.show();
					Log.i("ArrayLength", String.valueOf(resultsArrayW.length()));
					//lineBuild(_context); // call the build 
					_history.put("WeatherSave", results.toString());
					SaveStuff.storeObjectFile(_context, "saveDataObj", _history, false);  // save local as JSON obj string
					//SaveStuff.storeStringFile(_context, "saveDataString", results.toString(), false);
				}
			}catch (JSONException e){
				Log.e("JSON ERROR", "JSON ERROR");
			}

		}
	}

	
//// BROADCAST RECEIVERS
	BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {

	    @Override
	    public void onReceive(Context context, Intent intent) {
	        Log.w("Network Listener", "Network Type Changed");
	        
	    }
	};
	
	
	
	
//	private Handler messageHandler = new Handler(){
//		public void handleMessage(Message message){
//			//HANDLER CODE BODY
//		}
//	};
	
	
//	Messenger messenger = new Messenger(messageHandler);
//	Intent myIntent = new Intent(getApplicationContext(), Intent.class);
//	myIntent.putExtra("messenger", messenger);
	
	

}

