package com.klusman.java2;


import org.json.JSONArray;
import org.json.JSONObject;

import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class customCellAdapter extends BaseAdapter {

		private JSONArray items;
	    private Context cont;
	    
	    
	    public customCellAdapter(Context context, JSONArray array)
	    {
	        super();
	        this.items = array;
	        this.cont = context;
	    }

	    @Override
	    public int getCount() {
	        return items.length();
	    }

	    @Override
	    public Object getItem(int position) {
	        return null;
	    }

	    @Override
	    public long getItemId(int position) {
	        return 0;

	    }@Override
	    public View getView(int position, View convertView, ViewGroup parent) 
	    {
	        View v = convertView;
	        TextView Title;
	        TextView Weather;
	        TextView Wind;
	        //Log.i("LISTVIEW", "Checking Position" + position);
	        
	        
	        
	        
	        try
	        {       
	            if(!items.isNull(position))
	            {
	                JSONObject item = items.getJSONObject(position);
	                if (v == null) {
	                    v = LayoutInflater.from(cont).inflate(R.layout.day_tile_act, null);
	                }           
	                
	                Title = (TextView) v.findViewById(R.id.title);
	                Weather = (TextView) v.findViewById(R.id.blurb);
	                Wind = (TextView) v.findViewById(R.id.smallCornerText);
	                SmartImageView myImage = (SmartImageView) v.findViewById(R.id.my_image);

	                if(Title != null)
	                {
	                	Title.setText(item.getString("date"));
	                	Weather.setText("High: " + item.getString("tempMaxF") + "   Low: " + item.getString("tempMinF"));
	                	Wind.setText("Windspeed:  " + item.getString("windspeedMiles"));
	                	String picLink = item.getJSONArray("weatherIconUrl").getJSONObject(0).getString("value");
	                	//Log.i("PICLINK", picLink);
	                	myImage.setImageUrl(picLink);  //Test image  "weatherIconUrl"
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