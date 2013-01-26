package com.klusman.java2;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
//import android.widget.Toast;

public class GetForecast extends IntentService {
	


	public GetForecast() {
		super("GetForecast");
		// TODO Auto-generated constructor stub
		Log.i("TEST", "GetForecast Intent 1");
	}
	
	
	private int _result;
	String _response = null;
	Messenger messenger;
	
	
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////STEP 2  -  Intent gets Messenger Data //////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	@Override
	protected void onHandleIntent(Intent intent) {
		Log.i("GET FORECAST", "onHandleIntent");
		Bundle extras = intent.getExtras();
		if (extras != null){
			messenger = (Messenger) extras.get("MSNGR");
			String myZip = (String) extras.get("theZip");
			String days = (String) extras.get("daysL");
			Log.i("onHandleIntent", myZip);
			
			GetNewForecast(myZip, days);  // RUN STEP 3
			
			
			Message msg = Message.obtain();
			msg.arg1 = _result;
			Log.i("onHandleIntent", String.valueOf(msg.arg1));
			msg.obj = _response;
			
			try {
				messenger.send(msg);  // Return info to Step 4 message Handler
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				Log.i("MESSAGE ERROR", "onHandleIntent Message failed");
				e.printStackTrace();
				
			}
			
		}
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////STEP 3  -  Run URL builder and Query to get result /////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	public void GetNewForecast(String zip, String days){
		
		String myDays = days;
		Log.i("DAYS TO PULL", "GetNewForecast days: " + myDays );
		
		String zipCode = zip;
		Log.i("ZIP TO PULL", "GetNewForecast zip: " + zipCode );
		
		String API_URL = "http://free.worldweatheronline.com/feed/weather.ashx?q=" 
				+ zipCode + "&format=json&num_of_days=" + myDays + "&key=2a0cc91795015022122811";

		URL url;

		try {
			url = new URL(API_URL);
			_response = webStuff.getURLStringResponse(url);  //Test connection and hopefully get a response
			Log.i("URL Response", _response);
		} catch (MalformedURLException e) {
			url = null;
			e.printStackTrace();
		}
		
	}

}
