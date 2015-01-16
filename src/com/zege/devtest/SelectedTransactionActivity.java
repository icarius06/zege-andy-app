package com.zege.devtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.zege.devtest.flatui.FlatUI;
import com.zege.devtest.flatui.views.FlatButton;
import com.zege.devtest.flatui.views.FlatEditText;
import com.zege.devtest.models.TransactionModel;
import com.zege.devtest.utils.Constants;

public class SelectedTransactionActivity extends Activity {

	private TransactionModel model;
	private FlatEditText amountEditText,unitsEditText,particularsEditText ;
	FlatButton button_editRecord,button_submitEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Constants.initializeFlatUi(this);

		setContentView(R.layout.selected_transaction_activity);

		Intent i = getIntent();
		
		model = (TransactionModel) i.getExtras().get("model");
		
		
		button_submitEdit = (FlatButton)findViewById(R.id.button_submitEdit);
		button_submitEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});
		
		button_editRecord = (FlatButton)findViewById(R.id.button_editRecord);
		button_editRecord.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				enableViews(true);
			}
		});
		initializeViews();
	}
	
	/**
	 * Enabled the disabled views
	 */
	public void enableViews(boolean enabled){
		amountEditText.setEnabled(enabled);
		amountEditText.setFocusableInTouchMode(enabled);
		amountEditText.setFocusable(enabled);
		
		unitsEditText.setEnabled(enabled);
		unitsEditText.setFocusable(enabled);
		unitsEditText.setFocusableInTouchMode(enabled);

		particularsEditText.setEnabled(enabled);
		particularsEditText.setFocusable(enabled);
		particularsEditText.setFocusableInTouchMode(enabled);
		if (enabled)
		particularsEditText.requestFocus();
		
		button_submitEdit.setEnabled(enabled);
	}

	public void initializeViews() {

		amountEditText = (FlatEditText) findViewById(R.id.edittext_amountDisplay);
		amountEditText.setText(model.getAmount());

		unitsEditText = (FlatEditText) findViewById(R.id.edittext_unitsDisplay);
		unitsEditText.setText(model.getUnits());

		particularsEditText = (FlatEditText) findViewById(R.id.edittext_particularsDisplay);
		particularsEditText.setText(model.getParticulars());

		//Dont need to enable these ones
		FlatEditText priorityEditText = (FlatEditText) findViewById(R.id.edittext_priorityDisplay);
		if(model.getTran_color()!=null){
			if (model.getTran_color().equals("Red")) {
				priorityEditText.getAttributes().setTheme(FlatUI.BLOOD,
						getResources());
				priorityEditText.setText("Very High");
			} else if (model.getTran_color().equals("Blue")) {
				priorityEditText.getAttributes().setTheme(FlatUI.SEA,
						getResources());
				priorityEditText.setText("High");
			} else if (model.getTran_color().equals("Green")) {
				priorityEditText.getAttributes().setTheme(FlatUI.GRASS,
						getResources());
				priorityEditText.setText("Medium");
			} else if (model.getTran_color().equals("Yellow")) {
				priorityEditText.getAttributes().setTheme(FlatUI.SAND,
						getResources());
				priorityEditText.setText("Low");
			} else {
				priorityEditText.getAttributes().setTheme(FlatUI.SAND,
						getResources());
				priorityEditText.setText("Low");
			}
		}
		else {
			priorityEditText.getAttributes().setTheme(FlatUI.SAND,
					getResources());
			priorityEditText.setText("Low");
		}

		FlatEditText totalEditText = (FlatEditText) findViewById(R.id.edittext_totalDisplay);
		totalEditText.setText(model.getAmount());

		FlatEditText dateEditText = (FlatEditText) findViewById(R.id.edittext_dateDisplay);
		if(model.getCreated_date_time()!=(null))
		dateEditText.setText(Constants.getDate(model.getCreated_date_time()));//"May 28,2014");
		
		enableViews(false);
	}

}
