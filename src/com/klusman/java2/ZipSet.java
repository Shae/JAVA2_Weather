package com.klusman.java2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ZipSet extends Activity {
	String choice;
	String zip;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zipset);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    choice = extras.getString("ForecastLength");
		    constructionToast();
		    
		}
		
		final EditText et = (EditText) findViewById(R.id.editTextZip);
		
		Button btnNext = (Button) findViewById(R.id.btnLocNext);
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Prep Intent and send test data
				zip = et.getText().toString();
				Intent next = new Intent(ZipSet.this, Main.class);
				next.putExtra("Length", choice);  // Test Data
				next.putExtra("Zip", zip);  // Test Data
				startActivity(next);
			}
		});
	} // End onCreate
	
	
	
	public void constructionToast(){
		CharSequence text = choice;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(ZipSet.this, text, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	};

}
