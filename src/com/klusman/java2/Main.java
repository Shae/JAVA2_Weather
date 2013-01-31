package com.klusman.java2;

import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.klusman.java2.ListViewFrag.ListViewFragListener;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;

import android.app.Activity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;


import android.util.Log;
import android.view.Gravity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;



public class Main extends Activity implements ButtonFrag.FormListener, ListViewFragListener{

	String forecastLength = "5";  // Holds the forecast Length String (default 5)
	String zipLocation;  //  Holds a passed in zip code Location
	String currentZip;  // Holds the Mobile devices Current Zip Location
	Boolean connected = false;  // Holds the flag for connected or not
	JSONArray resultsArrayW;  // Holds the JSON array from the API Pull
	Context _context = this;  // Holds the Context
	HashMap<String,	String> _history;  // Holds the Hashmap results
	int daySpan = 1;  // Holds the int version of the forecast length requested (default 1)
	JSONObject newObj;
	String passMe;
	

//// LOCAL METHODS	
	public void testViewData(){  // Test for Bundles
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			forecastLength = extras.getString("Length");
			zipLocation = extras.getString("Zip");
			
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
		
		if(lm.isProviderEnabled(LocationManager.GPS_PROVIDER)){  // Check for GPS - Enabled
			Location loc = lm.getLastKnownLocation("gps");
			Geocoder geo = new Geocoder(this);
			try{
				List<Address> add = geo.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
				for (Address address : add) {
					String post = address.getPostalCode();
					
					currentZip = post;
				}
			}catch(Exception e){
				e.printStackTrace();  // follow error
			}
		}else if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){  // Check for Network - Enabled
			Location loc = lm.getLastKnownLocation("network");
			Geocoder geo = new Geocoder(this);
			try{
				List<Address> add = geo.getFromLocation(loc.getLatitude(), loc.getLongitude(), 1);
				for (Address address : add) {
					String post = address.getPostalCode();
					//Log.i("POSTAL NETWORK", post);
					currentZip = post;
				}
			}catch(Exception e){
				e.printStackTrace();  // follow error
			}
		}else{
			Log.i("LOCATION", "GPS and NETWORK connections are DISABLED");
		}

	} // end findZip 

	
	
//// SERVICE Starters & Stoppers
	public void runUpdaterService(){  //  to Run the "UpdaterService" Service
		Intent intent = new Intent(this, UpdaterService.class);
		startService(intent);
		
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
	/*
		SQLiteDatabase db = openOrCreateDatabase("ZipcodeTEST", MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS ZipTESTs (id integer primary key autoincrement, zip VARCHAR NOT NULL);");  //id interger primary key autoincrement, 
		db.execSQL("INSERT INTO ZipTESTS (zip) VALUES ('99999');");//"INSERT INTO Zipcodes (zipcode) VALUES ('99999');"
		//String t = db.getPath().toString();
		//Log.i("DB PATH", t);
		Cursor c = db.rawQuery("SELECT * from ZipTESTS", null);
		
		c.moveToFirst();
		Log.d("results", c.getString(c.getColumnIndex("zip")));
		db.close();
		*/
		_context.deleteDatabase("ZipcodeDB");
		
		_history = getStoredHist();
		setContentView(R.layout.main_act);
		connected = com.klusman.java2.webStuff.getConnectionStatus(this);
		checkConnection();
		testViewData();  // Test for Bundles and update data if any
		findZip();
		getTheWeatherNOW();
		

	} // end onCreate

	
//// OPTIONS MENU  
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
				getTheWeatherNOW();
				break;
			case R.id.menu_test_service:
				runServiceAction();
				break;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	
//// BUTTON HANDLERS
	@Override
	public void onWebClick() {
		final String url = "http://www.worldweatheronline.com/country.aspx";
		// Build Intent for web browser
		Intent next = new Intent(Intent.ACTION_VIEW, Uri.parse(url) );
		startActivity(next);

	}


	@Override
	public void onForecastClick() {
		Intent next = new Intent(Main.this, NewForecast.class);
		next.putExtra("ZipPass", currentZip);  // Pass Current Zip Location
		startActivity(next);
	}

	
//// IMPLEMENT LISTENERS
	
	@Override
	public void popList() {
		ListView listView = (ListView) findViewById(R.id.list);
		customCellAdapter lView;
		
		if(resultsArrayW != null){
			//Log.i("POPList", "resultsArray HAS DATA");
			lView = new customCellAdapter(_context, resultsArrayW);
			listView.setAdapter(lView);
		}else{
			//Log.i("POPList", "NO data in'resultsArray' pulling from _history");
			
			try {
				String st = _history.get("WeatherSave");  // Pull Saved data
				JSONObject js = new JSONObject(st);  //  Build as JSON OBJ
				//Log.i("JSON OBJECT", "WORKED!");
				resultsArrayW = js.getJSONArray("weather");  // Pull an Array from JSON
				
			} catch (JSONException e) {
				e.printStackTrace();
			}
			lView = new customCellAdapter(_context, resultsArrayW);
			listView.setAdapter(lView);			
		}
	}


//// GET API DATA
	@SuppressWarnings("unchecked")
	private HashMap<String, String> getStoredHist(){
		Object stored = _ReadStuff.readObjectFile(_context, "saveDataObj", false);
		
		HashMap<String, String> myStoredData;
		if(stored == null){
			//Log.i("READ DATA", "NO PAST DATA SAVED");
			myStoredData = new HashMap<String, String>();
		}else{
			myStoredData = (HashMap<String, String>) stored;
		}
		return myStoredData;
	}


	
////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////STEP 4  -  Receive message data from Service and decode ////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Handler myHandler = new Handler(){
		
		public void handleMessage(Message message){
			//Log.i("TEST","myHandler - envoke");
			Object result = message.obj;
			if (message.arg1 == 0 && result != null){  // had to make arg1 a ZERO instead of RESULT_OK - not sure why
				String resultString = (String) message.obj.toString();
				//Log.i("TEST","myHandler - IF has data and ok");
				try{
					//Log.i("TEST","myHandler - try to make JSON obj");
					JSONObject json = new JSONObject(resultString);
					JSONObject results = json.getJSONObject("data");
					resultsArrayW = results.getJSONArray("weather");
					int arrayLength = resultsArrayW.length();

					if(resultsArrayW == null){
						//Log.i("TEST","myHandler - IF - null");
						//Log.i("JSON GET OBJ", "NOT VALID");
						Toast toast = Toast.makeText(_context, "GET JSON FAILED", Toast.LENGTH_SHORT);
						toast.show();

					}else{
						//Log.i("TEST","myHandler - ELSE - has data");
						Toast toast = Toast.makeText(_context, String.valueOf(arrayLength) + " day(s) requested data received!", Toast.LENGTH_SHORT);
						toast.show();
						//Log.i("Weather Array Length", String.valueOf(resultsArrayW.length()));
						//lineBuild(_context); // call the build 
						_history.put("WeatherSave", results.toString());
						_SaveStuff.storeObjectFile(_context, "saveDataObj", _history, false);  // save local as JSON obj string
						//SaveStuff.storeStringFile(_context, "saveDataString", results.toString(), false);
					}
				}catch (JSONException e){
					Log.i("TEST","myHandler - error");
					Log.e("JSON ERROR", "JSON ERROR");
				}
				//Log.i("HANDLER TEST", "HANDLER WORKS");
				popList();  // rePopulate ListView
			}
		}
	};
			
////////////////////////////////////////////////////////////////////////////////////////////////////////////
//////// STEP 1  -  Opening call method and Messenger targeting Intent (GetForeCast) and sending Data //////
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void getTheWeatherNOW(){
		String ZIPitem;
		
		if (zipLocation != null){
			ZIPitem = zipLocation;
		} else {
			ZIPitem = currentZip;
		}
			
		Messenger messenger = new Messenger(myHandler);
		Intent i = new Intent(getApplicationContext(), GetForecast.class);
		i.putExtra("theZip", ZIPitem);  // add zip code to messenger
		i.putExtra("daysL", forecastLength);  //Pull forecast Length and add to messenger
		i.putExtra("MSNGR", messenger);  // attach Messenger Handler
		//	Log.i("TEST", "getTheWeatherNOW");
		startService(i);  //Start intent Service
		
	} // End getTheWeatherNOW
	

	public void getCursor(){
		
	}
	
	
	@Override
	public ContentResolver getContentResolver() {
		// TODO Auto-generated method stub
		return super.getContentResolver();
	}


	@Override
	public void onClickCell() {
		
		
	}
	
}// END MAIN

