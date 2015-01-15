package com.zege.devtest.utils;

import android.content.Context;

import com.zege.devtest.R;
import com.zege.devtest.flatui.FlatUI;


public class Constants {
	public static final int APP_THEME = R.array.sea;
	
	public static void initializeFlatUi(Context context){
		
		// converts the default values to dp to be compatible with different screen sizes
        FlatUI.initDefaultValues(context);

        // Default theme should be set before content view is added
        FlatUI.setDefaultTheme(APP_THEME);
	}
}