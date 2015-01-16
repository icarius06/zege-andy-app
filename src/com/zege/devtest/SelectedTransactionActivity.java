package com.zege.devtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zege.devtest.flatui.FlatUI;
import com.zege.devtest.flatui.views.FlatEditText;
import com.zege.devtest.models.TransactionModel;
import com.zege.devtest.utils.Constants;

public class SelectedTransactionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Constants.initializeFlatUi(this);

		setContentView(R.layout.selected_transaction_activity);
		
		Intent i = getIntent();
		
		TransactionModel  model= (TransactionModel)i.getExtras().get("model");
		
		FlatEditText amountEditText = (FlatEditText) findViewById(R.id.edittext_amountDisplay);
		amountEditText.setText(model.getAmount());
		
		FlatEditText unitsEditText = (FlatEditText) findViewById(R.id.edittext_unitsDisplay);
		unitsEditText.setText(model.getUnits());
		
		FlatEditText particularsEditText = (FlatEditText) findViewById(R.id.edittext_particularsDisplay);
		particularsEditText.setText(model.getParticulars());
		
		FlatEditText priorityEditText = (FlatEditText) findViewById(R.id.edittext_priorityDisplay);
		
		if(model.getTran_color().equals("Red")){
			priorityEditText.getAttributes().setTheme(FlatUI.BLOOD, getResources());
			priorityEditText.setText("Very High");
		}
		else if(model.getTran_color().equals("Blue")){
			priorityEditText.getAttributes().setTheme(FlatUI.SEA, getResources());
			priorityEditText.setText("High");
		}
		else if(model.getTran_color().equals("Green")){
			priorityEditText.getAttributes().setTheme(FlatUI.GRASS, getResources());
			priorityEditText.setText("Medium");
		}
		else if(model.getTran_color().equals("Yellow")){
			priorityEditText.getAttributes().setTheme(FlatUI.SAND, getResources());
			priorityEditText.setText("Low");
		}
		else{
			priorityEditText.getAttributes().setTheme(FlatUI.SAND, getResources());
			priorityEditText.setText("Low");
		}
		
		FlatEditText totalEditText = (FlatEditText)findViewById(R.id.edittext_totalDisplay);
		totalEditText.setText(model.getAmount());
		
		FlatEditText dateEditText = (FlatEditText)findViewById(R.id.edittext_dateDisplay);
		dateEditText.setText("May 28,2014");
		
	}

	
}
