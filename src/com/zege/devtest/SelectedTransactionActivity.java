package com.zege.devtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

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
		setTitle(model.getUnits());
		
	}

	
}
