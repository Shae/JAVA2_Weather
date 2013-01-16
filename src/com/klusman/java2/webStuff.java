package com.klusman.java2;


import java.io.BufferedInputStream;
import java.net.URL;
import java.net.URLConnection;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

	// CHECK for Web Connections
	public class webStuff {
		static Boolean connection = false;
		static String connectionType = "Unavailable";
 
		public static String getConnectionType(Context context){
		netInfo(context);
		return connectionType;
	}
	
	// Get connection Status
	public static Boolean getConnectionStatus(Context context){
		netInfo(context);
		return connection;
	}
	
	// Get connection Info
	private static void netInfo(Context context){
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if(netInfo != null){
			if(netInfo.isConnectedOrConnecting()){
				connectionType = netInfo.getTypeName();
				connection = true;
			}
		}
	}
	
	// Check URL response
	public static String getURLStringResponse(URL url){
		String response = "";
		
		try{
			URLConnection connection = url.openConnection();
			BufferedInputStream bin = new BufferedInputStream(connection.getInputStream());
			
			byte[] contentBytes = new byte[1024];
			int bytesRead = 0;
			StringBuffer respBuffer = new StringBuffer();
			while((bytesRead = bin.read(contentBytes))!= -1){
				response = new String(contentBytes, 0, bytesRead);
				respBuffer.append(response);
			}
				return respBuffer.toString();
			}catch (Exception e){
				Log.e("URL RESPONSE ERROR", "getURLStringResp");
			}
		
		return response;
		
	}
	
}
