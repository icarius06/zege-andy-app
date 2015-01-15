package com.zege.devtest;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.zege.devtest.domain.Session;
import com.zege.devtest.flatui.views.FlatButton;
import com.zege.devtest.flatui.views.FlatCheckBox;
import com.zege.devtest.flatui.views.FlatEditText;
import com.zege.devtest.logger.CustomLoggerClient;
import com.zege.devtest.models.TransactionModel;
import com.zege.devtest.utils.Constants;

@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
public class AddTransactionActivity extends Activity {

	private FlatCheckBox veryHighCheck, lowCheck, mediumCheck, highCheck;

	private WebView webView;

	private FlatEditText amountEditText, unitsEditText, particularsEditText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Constants.initializeFlatUi(this);

		setContentView(R.layout.add_new_transaction_activity);

		setTitle("New Transaction");

		initializeButtons();

		initializeCheckBoxes();

		initializeWebView();
	}

	public void initializeWebView() {
		Session gs = (Session) getApplication();
		webView = gs.getWebView();
		webView.loadUrl("file:///android_asset/www/index.html");
		// Step 2.1: Set custom webchrome client to allow logging
		webView.setWebChromeClient(new CustomLoggerClient());
		// Step 3: Enable JavaScript
		webView.getSettings().setJavaScriptEnabled(true);
		// Step 4: Add our js interface for objects transfer
		webView.addJavascriptInterface(new AddTransactionJsInterface(this),
				"MyAndroid2");

	}

	public TransactionModel getModelFromInput() {
		TransactionModel transactionModel = new TransactionModel();

		amountEditText = (FlatEditText) findViewById(R.id.amountEdittext_box);
		transactionModel.setAmount(amountEditText.getText().toString());

		unitsEditText = (FlatEditText) findViewById(R.id.unitsEdittext_box);
		transactionModel.setUnits(unitsEditText.getText().toString());

		particularsEditText = (FlatEditText) findViewById(R.id.particularsEdittext_box);
		transactionModel.setParticulars(particularsEditText.getText()
				.toString());

		if (veryHighCheck.isChecked()) {
			transactionModel.setTran_color("Red");
		}

		if (highCheck.isChecked()) {
			transactionModel.setTran_color("Blue");
		}

		if (mediumCheck.isChecked()) {
			transactionModel.setTran_color("Green");
		}

		if (lowCheck.isChecked()) {
			transactionModel.setTran_color("Yellow");
		}

		return transactionModel;
	}

	public void initializeButtons() {
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
				webView.loadUrl("javascript:addTransaction()");

				Intent i = new Intent(AddTransactionActivity.this,
						MainActivity.class);
				startActivity(i);
				finish();
			}
		});
	}

	public void initializeCheckBoxes() {
		veryHighCheck = (FlatCheckBox) findViewById(R.id.checkbox_very_high);
		veryHighCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				if (arg1) {
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
				if (arg1) {
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
				if (arg1) {
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
				if (arg1) {
					highCheck.setChecked(false);
					veryHighCheck.setChecked(false);
					mediumCheck.setChecked(false);
				}
			}
		});

	}

	/** Index db stuff */

	/**
	 * This class will enable us to pass values to and fro java/js
	 * 
	 * @author Michael
	 * 
	 */
	public class AddTransactionJsInterface {

		Context mContext;

		public AddTransactionJsInterface(Context c) {

			mContext = c;
		}

		@JavascriptInterface
		public String getTextValuesFor(String field) {
			TransactionModel model = getModelFromInput();
			String text_value = "";

			if (field.equals("units")) {
				text_value = model.getUnits();
				return text_value;
			}
			if (field.equals("amount")) {
				text_value = model.getAmount();
				return text_value;
			}
			if (field.equals("particulars")) {
				text_value = model.getParticulars();
				return text_value;
			}

			return text_value;
		}
	}
}
