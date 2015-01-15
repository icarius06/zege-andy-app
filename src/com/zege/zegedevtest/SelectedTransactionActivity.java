package com.zege.zegedevtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.zege.zegedevtest.utils.Constants;

public class SelectedTransactionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Constants.initializeFlatUi(this);

		setContentView(R.layout.selected_transaction_activity);
		
		Intent i = getIntent();
		String title  = i.getExtras().get("item").toString();
		setTitle(title);
		
	}

	
}
