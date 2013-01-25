package com.klusman.java2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.widget.Toast;

public class NetworkBroadcastRec extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {  // Notifies when the power cord is plugged in
		myToast(context, "POWER CORD CONNECTED!");  // WORKS
		
//        Intent i = new Intent(context, UpdaterService.class);  
//        context.startActivity(i); 

		
	}

	public void myToast(Context context, String text){
		CharSequence textIN = text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(context, textIN, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	};// end myToast
}
