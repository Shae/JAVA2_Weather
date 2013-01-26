package com.klusman.java2;

import java.net.MalformedURLException;
import java.net.URL;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

public class GetForecast extends IntentService {
	

	public GetForecast(String name) {
		super("GetForecast");
		// TODO Auto-generated constructor stub
	}
	
	
	private int _result;
	String _response = null;
	Messenger messenger;
	
	
	public void GetNewForecast(String zip){
		Log.i("GET NEW FORECAST", "TEST 2");
		String zipCode = zip;
		String API_URL = "http://free.worldweatheronline.com/feed/weather.ashx?q=" 
				+ zipCode + "&format=json&num_of_days=5&key=2a0cc91795015022122811";
		URL url = null;
		
		Log.i("TEST", "GetForecastService");
		try {
			url = new URL(API_URL);
			_response = webStuff.getURLStringResponse(url);
		} catch (MalformedURLException e) {
			url = null;
			e.printStackTrace();
		}
		
	}
	
////////////////////////////////////////////////////////////////////////////////////////////////////////////
////////STEP 2  -  Intent gets Messenger Data //////
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
			GetNewForecast(myZip);
			
			Message msg = Message.obtain();
			msg.arg1 = _result;
			msg.obj = _response;
			
			try {
				messenger.send(msg);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				Log.i("MESSAGE ERROR", "onHandleIntent Message failed");
				e.printStackTrace();
				
			}
			
		}
	}
	
	

}
