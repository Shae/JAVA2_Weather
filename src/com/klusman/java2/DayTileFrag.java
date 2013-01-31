package com.klusman.java2;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class DayTileFrag extends Fragment {
	Context _context;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);

		RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.day_tile_act, container, false);
		//ListView lv = (ListView) new ListView(R.id.listview_frag);  // STUCK HERE
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				myToast("ONCLICK TEST  GOOD!");

		
			}
		});
		

		return view;
	}
	
	
	public void myToast(String text){  // Toast Template
		CharSequence textIN = text;
		int duration = Toast.LENGTH_SHORT;
		Toast toast = Toast.makeText(_context, textIN, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	};// end myToast
}
