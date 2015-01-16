package com.zege.devtest.utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.content.Context;

import com.zege.devtest.R;
import com.zege.devtest.flatui.FlatUI;

/**
 * Global constants tht cut across the app
 * @author Michael
 *
 */
public class Constants {
	public static final int APP_THEME = R.array.sea;
	
	public static void initializeFlatUi(Context context){
		
		// converts the default values to dp to be compatible with different screen sizes
        FlatUI.initDefaultValues(context);

        // Default theme should be set before content view is added
        FlatUI.setDefaultTheme(APP_THEME);
        
	}
	
	public static String getDate(String timeStampStr){
		try{
		    DateFormat sdf = new SimpleDateFormat("MM dd,yyyy HH:mm");
		    Date netDate = (new Date(Long.parseLong(timeStampStr)));
		    String date = sdf.format(netDate);
		    String month = getMonth(date.substring(0,2));
		    date = date.replaceFirst(date.substring(0,2), month);
		    return date;
		}
		catch(Exception ex){
		    return "xx";
		 }
	}
	
	private static String getMonth(String month){
		String [] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sept","Nov","Dec"};
		return months[Integer.parseInt(month)-1];	
	}
	
}
