package com.klusman.java2;

import android.app.Activity;
import android.app.Fragment;

import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;


public class ButtonFrag extends Fragment {

	private FormListener listener;
	
		public interface FormListener{
			public void onWebClick();
			public void onForecastClick();
			
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.btn_act, container, false);
		
		// NEW FORECAST BUTTON
		Button btnNEW = (Button) view.findViewById(R.id.btnNew);
		
		btnNEW.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onForecastClick();

			}
		});// end buildPageSetup

		// WEB BUTTON
		Button btnWEB = (Button) view.findViewById(R.id.btnWeb);
		btnWEB.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				listener.onWebClick();
			}
		});

		// HISTORY BUTTON
		//Button btnHIST = (Button) view.findViewById(R.id.btnHistory);
//		btnHIST.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				listener.onHistClick();
//			}
//		});			


		return view;
	}
	
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try {
			listener = (FormListener) activity;
		}catch (ClassCastException e){
			throw new ClassCastException(activity.toString() + " Must implement form listener");
			
		}
	}
}
