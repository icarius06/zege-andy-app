package com.zege.devtest;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.zege.devtest.domain.Session;
import com.zege.devtest.flatui.FlatUI;
import com.zege.devtest.flatui.views.FlatButton;
import com.zege.devtest.flatui.views.FlatEditText;
import com.zege.devtest.flatui.views.FlatTextView;
import com.zege.devtest.logger.CustomLoggerClient;
import com.zege.devtest.models.TransactionModel;
import com.zege.devtest.utils.Constants;

@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
public class AddTransactionActivity extends Activity {

	private Spinner spinner;

	private WebView webView;

	private FlatEditText amountEditText, unitsEditText, particularsEditText;
	String[] priorities;

	private ArrayList<FlatEditText> editFields = new ArrayList<FlatEditText>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Constants.initializeFlatUi(this);

		setContentView(R.layout.add_new_transaction_activity);

		setTitle("New Transaction");

		priorities = getResources().getStringArray(R.array.priority_array);

		amountEditText = (FlatEditText) findViewById(R.id.amountEdittext_box);
		unitsEditText = (FlatEditText) findViewById(R.id.unitsEdittext_box);
		particularsEditText = (FlatEditText) findViewById(R.id.particularsEdittext_box);

		editFields.add(amountEditText);
		editFields.add(unitsEditText);
		editFields.add(particularsEditText);

		initializeButtons();

		initializeSpinner();

		initializeWebView();

	}

	public void initializeSpinner() {
		spinner = (Spinner) findViewById(R.id.priority_spinner);

		// Create an ArrayAdapter using the string array and a custom spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.priority_array, R.layout.spinner_button);

		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(R.layout.simple_flat_list_item);

		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				if (arg2 == 0) {
					FlatTextView v = (FlatTextView) arg1;
					v.getAttributes().setTheme(FlatUI.SAND, getResources());
				}
				if (arg2 == 1) {
					FlatTextView v = (FlatTextView) arg1;
					v.getAttributes().setTheme(FlatUI.GRASS, getResources());
				}
				if (arg2 == 2) {
					FlatTextView v = (FlatTextView) arg1;
					v.getAttributes().setTheme(FlatUI.SEA, getResources());
				}
				if (arg2 == 3) {
					FlatTextView v = (FlatTextView) arg1;
					v.getAttributes().setTheme(FlatUI.BLOOD, getResources());
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}

		});
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

		transactionModel.setAmount(amountEditText.getText().toString());
		transactionModel.setUnits(unitsEditText.getText().toString());
		transactionModel.setParticulars(particularsEditText.getText()
				.toString());

		if (spinner.getSelectedItem().toString().equals(priorities[0])) {
			transactionModel.setTran_color("Yellow");
		}

		if (spinner.getSelectedItem().toString().equals(priorities[1])) {
			transactionModel.setTran_color("Green");
		}

		if (spinner.getSelectedItem().toString().equals(priorities[2])) {
			transactionModel.setTran_color("Blue");
		}

		if (spinner.getSelectedItem().toString().equals(priorities[3])) {
			transactionModel.setTran_color("Red");
		}

		return transactionModel;
	}

	private boolean areEmptyFieldsHandled() {
		boolean all_fields_entered = true;
		for (FlatEditText entry : editFields) {
			if (entry.getText().toString().isEmpty()) {
				all_fields_entered = false;
				entry.getAttributes().setTheme(FlatUI.CANDY, getResources());
				return all_fields_entered;
			} else {
				entry.getAttributes().setTheme(Constants.APP_THEME,
						getResources());
			}
		}
		return all_fields_entered;
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
				if (areEmptyFieldsHandled()) {
					/*
					 * webView.loadUrl("javascript:addTransaction()"); Intent i
					 * = new
					 * Intent(AddTransactionActivity.this,MainActivity.class);
					 * startActivity(i); finish();
					 */
					Toast.makeText(AddTransactionActivity.this, "CLick",
							Toast.LENGTH_SHORT).show();
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
