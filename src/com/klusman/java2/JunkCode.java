package com.klusman.java2;

public class JunkCode {

}
/*	
///////////  LEFT IN UNTIL I GET THE MESSAGE AND SERVICE WORKING CORRECTLY ///////////////////
private void getWeatherData(){  
String daysREQd = forecastLengthPull();  //Check Req'd forecast length (Default 5)
String zipCode = currentZip;  //Pull ZipCode from global values

Log.i("DAYS TO GET", "Pull this many days: " + daysREQd );
// Concat http address
String messURL = "http://free.worldweatheronline.com/feed/weather.ashx?q=" + zipCode + "&format=json&num_of_days=" + daysREQd + "&key=2a0cc91795015022122811";
String mString;

try{
	mString = URLEncoder.encode(messURL, "UTF-8");  //encode URL
	Log.i("URL to CALL", mString);  // URL test LOG
} catch(Exception e){
	Log.e("BAD URL", "Encoding Problem");
	mString = "";
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


// should be able to remove post execute now.  Added to handler
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

*/



////BROADCAST RECEIVERS
//BroadcastReceiver networkStateReceiver = new BroadcastReceiver() {
//
//   @Override
//   public void onReceive(Context context, Intent intent) {
//       
//       
//   }
//};




//private Handler messageHandler = new Handler(){
//	public void handleMessage(Message message){
//		//HANDLER CODE BODY
//	}
//};


//Messenger messenger = new Messenger(messageHandler);
//Intent myIntent = new Intent(getApplicationContext(), Intent.class);
//myIntent.putExtra("messenger", messenger);

