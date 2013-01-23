package com.klusman.java2;

import android.app.IntentService;
import android.content.Intent;
import android.widget.Toast;

public class UpdaterService extends IntentService {

	private static final String TAG = UpdaterService.class.getSimpleName();
	
	public UpdaterService(String name) {
		super("UpdaterService");
		
	}
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	    Toast.makeText(this, TAG, Toast.LENGTH_SHORT).show();
	    return super.onStartCommand(intent,flags,startId);
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
	}
	
}
