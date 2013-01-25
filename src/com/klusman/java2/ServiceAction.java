package com.klusman.java2;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.view.Gravity;
import android.widget.Toast;

public class ServiceAction extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

		
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		myToast("Service Destroyed!");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		boolean b = isNetworkAvailable();
		if (b == true){
			myToast("Internet Connection is Accessable");
		}else{
			myToast("NO Internet Connection");
		}
		
		myToast("'ServiceAction' Tested!");
		
		return super.onStartCommand(intent, flags, startId);
	}

	
////TOAST Template
	public void myToast(String text){
		CharSequence textIN = text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(ServiceAction.this, textIN, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	};// end myToast
	
	private boolean isNetworkAvailable() {
	    ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    if (activeNetworkInfo != null){
	    	return true;
	    }else{
	    	return false;
	    }
	}
}
