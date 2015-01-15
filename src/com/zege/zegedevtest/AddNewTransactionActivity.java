package com.zege.zegedevtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.Toast;

import com.zege.zegedevtest.flatui.views.FlatButton;
import com.zege.zegedevtest.flatui.views.FlatCheckBox;
import com.zege.zegedevtest.utils.Constants;

public class AddNewTransactionActivity extends Activity {

	private FlatCheckBox veryHighCheck, lowCheck, mediumCheck, highCheck;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Constants.initializeFlatUi(this);

		setContentView(R.layout.add_new_transaction_activity);
		setTitle("New Transaction");

		FlatButton btnCancel = (FlatButton) findViewById(R.id.buttonCancel);
		btnCancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		FlatButton btnSubmit = (FlatButton) findViewById(R.id.buttonSubmit);
		btnSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				finish();
			}
		});

		veryHighCheck = (FlatCheckBox) findViewById(R.id.checkbox_very_high);
		veryHighCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					highCheck.setChecked(false);
					lowCheck.setChecked(false);
					mediumCheck.setChecked(false);
				}
			}
		});

		highCheck = (FlatCheckBox) findViewById(R.id.checkbox_high);
		highCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					veryHighCheck.setChecked(false);
					lowCheck.setChecked(false);
					mediumCheck.setChecked(false);
				}
			}
		});

		mediumCheck = (FlatCheckBox) findViewById(R.id.checkbox_medium);
		mediumCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){				
					highCheck.setChecked(false);
					veryHighCheck.setChecked(false);
					lowCheck.setChecked(false);
				}
			}
		});

		lowCheck = (FlatCheckBox) findViewById(R.id.checkbox_low);
		lowCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if(arg1){
					highCheck.setChecked(false);
					veryHighCheck.setChecked(false);
					mediumCheck.setChecked(false);
				}
			}
		});

	}

}
