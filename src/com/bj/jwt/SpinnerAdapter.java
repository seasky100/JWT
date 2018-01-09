package com.bj.jwt;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<String>{
	Context context;  
    String[] items = new String[] {};  
	public SpinnerAdapter(Context context,
			int textViewResourceId, String[] objects) {
		super(context,  textViewResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context=context;
		this.items=objects;
	}
	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if (convertView == null) {  
            LayoutInflater inflater = LayoutInflater.from(context);  
            convertView = inflater.inflate(  
                    android.R.layout.simple_spinner_item, parent, false);  
        }  
  
        TextView tv = (TextView) convertView  
                .findViewById(android.R.id.text1);  
        tv.setText(items[position]); 
       tv.setGravity(Gravity.CENTER); 
        tv.setTextColor(Color.BLUE);  
        tv.setTextSize(30);  
        return convertView;  
		
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		 if (convertView == null) {  
	            LayoutInflater inflater = LayoutInflater.from(context);  
	            convertView = inflater.inflate(  
	                    android.R.layout.simple_spinner_item, parent, false);  
	        }  
	  
	        // android.R.id.text1 is default text view in resource of the android.   
	        // android.R.layout.simple_spinner_item is default layout in resources of android.   
	  
	        TextView tv = (TextView) convertView  
	                .findViewById(android.R.id.text1);  
	        tv.setText(items[position]); 
	       tv.setGravity(Gravity.CENTER); 
	        tv.setTextColor(Color.BLUE);  
	        tv.setTextSize(30);  
	        return convertView;  
	}
	
	

}
