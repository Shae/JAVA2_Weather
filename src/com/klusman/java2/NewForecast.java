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
	String choice;
	int choiceInt;
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
		        choiceInt = selectedId;
		        
		       // Log.i("Selected RadioBTN", choice);
		        
				Intent next = new Intent(NewForecast.this, ZipSet.class);
				next.putExtra("ForecastLength", forecastLengthPull());  // Pass Forecast Radio Selection
				next.putExtra("Zippp", zipp);  // Pass Current Zip
				startActivity(next);

			}
		});

	}
	
public String forecastLengthPull(){  // Get length for API Pull
		
		String days = "5";
		
		if(choice.compareTo("1-Day Forecast") == 0){
			days = "1";
		}
		if(choice.compareTo("2-Day Forecast") == 0){
			days = "2";
		}
		if(choice.compareTo("3-Day Forecast") == 0){
			days = "3";
		}
		if(choice.compareTo("4-Day Forecast") == 0){
			days = "4";
		}
		if(choice.compareTo("5-Day Forecast") == 0){
			days = "5";
		} 

		//Log.i("NEW FORECAST DAYS", days);
		return days;
		
	}
	public void constructionToast(){
		CharSequence text = zipp;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(NewForecast.this, text, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	};
}
