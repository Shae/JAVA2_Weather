package com.klusman.java2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class UpdaterService extends Service {

	private static final String TAG = UpdaterService.class.getSimpleName();
	
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d(TAG, "Service Created");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "Service Destroyed");
	}


	
	
}
