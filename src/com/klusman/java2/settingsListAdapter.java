package com.klusman.java2;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class settingsListAdapter extends BaseAdapter {

	private JSONArray items;
    private Context cont;
    
	
    public settingsListAdapter(Context context, JSONArray array)
    {
        super();
        this.items = array;
        this.cont = context;
    }
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {
		 View v = view;
		 

	        TextView Title;
	        TextView Count;
	        	Log.i("LISTVIEW", "Checking Position" + position);
	        try
	        {       
	            if(!items.isNull(position))
	            {
	                JSONObject item = items.getJSONObject(position);
	                if (v == null) {
	                    v = LayoutInflater.from(cont).inflate(R.layout.row, null);
	                }           
	                
	                Title = (TextView) v.findViewById(R.id.textName);
	                Count = (TextView) v.findViewById(R.id.textValue);
	                if(Title != null)
	                {
	                	Title.setText(item.getString("NAME"));
	                	Count.setText(item.getString("VALUE"));
	                }
	            }else{
	                return null;
	            }
	        }
	        catch(Exception e)
	        {
	            Log.e("num", "LIST ERROR! " + e.toString());
	        }
	        return v;
	}

}
