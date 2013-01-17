package com.klusman.java2;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class DayTileFrag extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);

		RelativeLayout view = (RelativeLayout) inflater.inflate(R.layout.day_tile_act, container, false);


		

		return view;
	}
	
}
