package com.zege.devtest.utils;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.widget.Toast;

import com.zege.devtest.R;
import com.zege.devtest.flatui.FlatUI;
import com.zege.devtest.flatui.views.FlatEditText;
import com.zege.devtest.models.TransactionModel;

/**
 * Global constants tht cut across the app
 * 
 * @author Michael
 * 
 */
public class Constants {
	public static final int APP_THEME = R.array.deep;

	public static void initializeFlatUi(Context context) {

		// converts the default values to dp to be compatible with different
		// screen sizes
		FlatUI.initDefaultValues(context);

		// Default theme should be set before content view is added
		FlatUI.setDefaultTheme(APP_THEME);
	}

	@SuppressLint("NewApi")
	public void getScreenSize(Context context) {
		Activity activity = (Activity) context;
		Display display = activity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		String text = String.valueOf(width) + "<<" + String.valueOf(height);
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	@SuppressLint("SimpleDateFormat") 
	public static String getDate(String timeStampStr) {
		try {
			DateFormat sdf = new SimpleDateFormat("MM dd,yyyy HH:mm");
			Date netDate = (new Date(Long.parseLong(timeStampStr)));
			String date = sdf.format(netDate);
			String month = getMonth(date.substring(0, 2));
			date = date.replaceFirst(date.substring(0, 2), month);
			return date;
		} catch (Exception ex) {
			return "xx";
		}
	}
	
	/**
	 * Get json object from string passed from js function
	 * @param stringFromJS
	 * @return
	 */
	public static TransactionModel getModelFromString(String stringFromJS) {
		TransactionModel model = new TransactionModel();
		try {
			JSONObject jsonObject = new JSONObject(stringFromJS);
			model.setAmount(jsonObject.getString("amount"));
			model.setParticulars(jsonObject.getString("particulars"));
			model.setUnits(jsonObject.getString("units"));
			model.setTran_color(jsonObject.getString("priority"));
			if (jsonObject.getString("timeStamp") != null)
				model.setCreated_date_time(jsonObject
						.getString("timeStamp"));
		} catch (JSONException e) {
		}
		return model;
	}

	/**
	 * Check if the passed fields array list is fully entered
	 * @param editFields
	 * @param a
	 * @return
	 */
	public static boolean areEmptyFieldsHandled(ArrayList<FlatEditText> editFields,Activity a) {
		boolean all_fields_entered = true;
		for (FlatEditText entry : editFields) {
			if (entry.getText().toString().isEmpty()) {
				all_fields_entered = false;
				entry.getAttributes().setTheme(FlatUI.CANDY, a.getResources());
				return all_fields_entered;
			} else {
				entry.getAttributes().setTheme(Constants.APP_THEME,
						a.getResources());
			}
		}
		return all_fields_entered;
	}

	
	/**
	 * 
	 * @param month
	 * @return
	 */
	private static String getMonth(String month) {
		String[] months = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug", "Sept", "Nov", "Dec" };
		return months[Integer.parseInt(month) - 1];
	}

}
