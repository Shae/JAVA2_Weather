package com.klusman.java2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

public class NetworkBroadcastRec extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		myToast(context, "Pulling API Data!");
		//context.startService(new Intent(context, UpdaterService.class));  // START SERVICE

		
	}

	
	public void myToast(Context context, String text){
		CharSequence textIN = text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, textIN, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	};// end myToast
}
