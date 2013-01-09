package com.klusman.java2;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;




public class Main extends Activity {




	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);



// NEW FORECAST BUTTON
		Button btnNEW = (Button) findViewById(R.id.btnNew);
		btnNEW.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(Main.this, NewForecast.class));

			}
		});
// WEB BUTTON
		Button btnWEB = (Button) findViewById(R.id.btnWeb);


// HISTORY BUTTON
		Button btnHIST = (Button) findViewById(R.id.btnHistory);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
