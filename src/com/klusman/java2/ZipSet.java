package com.klusman.java2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ZipSet extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_zipset);

		Button btnNext = (Button) findViewById(R.id.btnLocNext);
		btnNext.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(ZipSet.this, Main.class));

			}
		});

	}
}
