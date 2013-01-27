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
	protected void onHandleIntent(Intent intent) {
		Toast.makeText(this, TAG, Toast.LENGTH_SHORT).show();
		
	}
	
}
