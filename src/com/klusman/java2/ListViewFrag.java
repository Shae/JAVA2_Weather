package com.klusman.java2;


import android.app.Activity;
import android.app.Fragment;
//import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;




public class ListViewFrag extends Fragment  {

	@SuppressWarnings("unused")
	private ListViewFragListener listener;

	public interface ListViewFragListener {
		public void popList();

	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);

		LinearLayout view = (LinearLayout) inflater.inflate(R.layout.listview_frag, container, false);
		
		return view;
	}
	
	@Override           
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try {
			listener = (ListViewFragListener) activity;
		}catch (ClassCastException e){
			throw new ClassCastException(activity.toString() + " Must implement form listener");
			
		}
	}
	
}
