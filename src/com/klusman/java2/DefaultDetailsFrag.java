package com.klusman.java2;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


public class DefaultDetailsFrag extends Fragment {

	private DefaultDetailsListener listener;
	public String zipCheck;

	public interface DefaultDetailsListener{
		public void onFirstOpen();
		public void onSetText();


	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);

		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.def_act, container, false);


		

		return view;
	}
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try {
			listener = (DefaultDetailsListener) activity;
		}catch (ClassCastException e){
			throw new ClassCastException(activity.toString() + " Must implement form listener");
			
		}
	}
}
