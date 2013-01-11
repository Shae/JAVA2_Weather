package com.klusman.java2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class NewForecast extends Activity{
	
	String zipp;
	String choice = "5";
	private RadioGroup radioGroup;
	private RadioButton radioButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newforecast);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
		    zipp = extras.getString("ZipPass");
		    //constructionToast();
		    
		}
		
		
		
		Button btnNext = (Button) findViewById(R.id.btnForecastNext);
		radioGroup = (RadioGroup) findViewById(R.id.radioGoupOpts);
		
		btnNext.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			// Prepping Intent and send data
				
		    // get selected radio button from radioGroup
			int selectedId = radioGroup.getCheckedRadioButtonId();
 
			// find the radiobutton by returned id
		        radioButton = (RadioButton) findViewById(selectedId);
		        choice = radioButton.getText().toString();
 
				Intent next = new Intent(NewForecast.this, ZipSet.class);
				next.putExtra("ForecastLength", choice);  // Pass Forecast Radio Selection
				next.putExtra("Zippp", zipp);  // Pass Current Zip
				startActivity(next);

			}
		});

	}
	
	
	public void constructionToast(){
		CharSequence text = zipp;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(NewForecast.this, text, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	};
}
