package com.zege.devtest;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class SelectedTransactionActivity extends Activity {

	private TransactionModel model;
	private FlatEditText amountEditText, unitsEditText, particularsEditText;
	private FlatButton button_editRecord, button_submitEdit,
			button_deleteRecord;

	private Spinner edit_priority_spinner;

	private ArrayList<FlatEditText> editFields = new ArrayList<FlatEditText>();

	private WebView webView;

	String[] priorities;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Constants.initializeFlatUi(this);

		setContentView(R.layout.selected_transaction_activity);

		Intent i = getIntent();

		model = (TransactionModel) i.getExtras().get("model");

		initializeWebView();

		priorities = getResources().getStringArray(R.array.priority_array);

		button_submitEdit = (FlatButton) findViewById(R.id.button_submitEdit);
		button_submitEdit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (Constants.areEmptyFieldsHandled(editFields,
						SelectedTransactionActivity.this)) {
					webView.loadUrl("javascript:updateTransaction("
							+ model.getCreated_date_time() + ")");
					Toast.makeText(SelectedTransactionActivity.this,
							"Record edited", Toast.LENGTH_SHORT).show();
					finish();
				}
			}
		});

		button_editRecord = (FlatButton) findViewById(R.id.button_editRecord);
		button_editRecord.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				enableViews(true);
			}
		});

		button_deleteRecord = (FlatButton) findViewById(R.id.button_deleteRecord);
		button_deleteRecord.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				AlertDialog.Builder alert = new AlertDialog.Builder(
						SelectedTransactionActivity.this);
				alert.setTitle("Are you sure?");
				alert.setPositiveButton("YES",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Toast.makeText(SelectedTransactionActivity.this,model.getCreated_date_time()
								// , Toast.LENGTH_SHORT).show();
								webView.loadUrl("javascript:deleteTransaction("
										+ model.getCreated_date_time() + ")");
								finish();
							}
						});
				// End of alert.setPositiveButton
				alert.setNegativeButton("CANCEL",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
								// Canceled.
								dialog.cancel();
							}
						}); // End of alert.setNegativeButton

				AlertDialog alertDialog = alert.create();
				alertDialog.show();

			}
		});

		initializeViews();
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void initializeWebView() {
		Session gs = (Session) getApplication();
		webView = gs.getWebView();
		webView.loadUrl("file:///android_asset/www/index.html");
		// Step 2.1: Set custom webchrome client to allow logging
		webView.setWebChromeClient(new CustomLoggerClient());
		// Step 3: Enable JavaScript
		webView.getSettings().setJavaScriptEnabled(true);
		webView.addJavascriptInterface(
				new SelectedTransactionJsInterface(this), "MyAndroid3");
	}

	public TransactionModel getModelFromInput() {
		TransactionModel transactionModel = new TransactionModel();
		transactionModel.setAmount(amountEditText.getText().toString());
		transactionModel.setUnits(unitsEditText.getText().toString());
		transactionModel.setParticulars(particularsEditText.getText()
				.toString());

		if (edit_priority_spinner.getSelectedItem().toString()
				.equals(priorities[0])) {
			transactionModel.setTran_color("Yellow");
		}

		if (edit_priority_spinner.getSelectedItem().toString()
				.equals(priorities[1])) {
			transactionModel.setTran_color("Green");
		}

		if (edit_priority_spinner.getSelectedItem().toString()
				.equals(priorities[2])) {
			transactionModel.setTran_color("Blue");
		}

		if (edit_priority_spinner.getSelectedItem().toString()
				.equals(priorities[3])) {
			transactionModel.setTran_color("Red");
		}

		return transactionModel;
	}

	/**
	 * Enabled the disabled views
	 */
	public void enableViews(boolean enabled) {
		amountEditText.setEnabled(enabled);
		amountEditText.setFocusableInTouchMode(enabled);
		amountEditText.setFocusable(enabled);

		unitsEditText.setEnabled(enabled);
		unitsEditText.setFocusable(enabled);
		unitsEditText.setFocusableInTouchMode(enabled);

		particularsEditText.setEnabled(enabled);
		particularsEditText.setFocusable(enabled);
		particularsEditText.setFocusableInTouchMode(enabled);
		if (enabled) {
			particularsEditText.requestFocus();
			edit_priority_spinner.setVisibility(View.VISIBLE);
			FlatEditText edittext_priorityDisplay = (FlatEditText) findViewById(R.id.edittext_priorityDisplay);
			edittext_priorityDisplay.setVisibility(View.GONE);
		}

		editFields.add(particularsEditText);
		editFields.add(amountEditText);
		editFields.add(unitsEditText);

		button_submitEdit.setEnabled(enabled);
	}

	public void initializeViews() {

		edit_priority_spinner = (Spinner) findViewById(R.id.edit_priority_spinner);
		// Create an ArrayAdapter using the string array and a custom spinner
		// layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.priority_array, R.layout.spinner_button);

		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(R.layout.simple_flat_list_item);

		edit_priority_spinner.setAdapter(adapter);
		edit_priority_spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						if (arg2 == 0) {
							FlatTextView v = (FlatTextView) arg1;
							v.getAttributes().setTheme(FlatUI.SAND,
									getResources());
						}
						if (arg2 == 1) {
							FlatTextView v = (FlatTextView) arg1;
							v.getAttributes().setTheme(FlatUI.GRASS,
									getResources());
						}
						if (arg2 == 2) {
							FlatTextView v = (FlatTextView) arg1;
							v.getAttributes().setTheme(FlatUI.SEA,
									getResources());
						}
						if (arg2 == 3) {
							FlatTextView v = (FlatTextView) arg1;
							v.getAttributes().setTheme(FlatUI.BLOOD,
									getResources());
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}

				});

		amountEditText = (FlatEditText) findViewById(R.id.edittext_amountDisplay);
		amountEditText.setText(model.getAmount());

		unitsEditText = (FlatEditText) findViewById(R.id.edittext_unitsDisplay);
		unitsEditText.setText(model.getUnits());

		particularsEditText = (FlatEditText) findViewById(R.id.edittext_particularsDisplay);
		particularsEditText.setText(model.getParticulars());

		FlatEditText msgStatusEditText = (FlatEditText) findViewById(R.id.edittext_msgStatusDisplay);
		msgStatusEditText.setText("SMS Sent/SMS not sent");

		// Dont need to enable these ones
		FlatEditText priorityEditText = (FlatEditText) findViewById(R.id.edittext_priorityDisplay);
		if (model.getTran_color() != null) {
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
		} else {
			priorityEditText.getAttributes().setTheme(FlatUI.SAND,
					getResources());
			priorityEditText.setText("Low");
		}

		FlatEditText totalEditText = (FlatEditText) findViewById(R.id.edittext_totalDisplay);
		totalEditText.setText(model.getAmount());

		FlatEditText dateEditText = (FlatEditText) findViewById(R.id.edittext_dateDisplay);
		if (model.getCreated_date_time() != (null))
			dateEditText
					.setText(Constants.getDate(model.getCreated_date_time()));// "May 28,2014");

		enableViews(false);
	}

	/**
	 * This class will enable us to pass values to and fro java/js
	 * 
	 * @author Michael
	 * 
	 */
	public class SelectedTransactionJsInterface {

		Context mContext;

		public SelectedTransactionJsInterface(Context c) {

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

			if (field.equals("priority")) {
				text_value = model.getTran_color();
				return text_value;
			}

			return text_value;
		}
	}

}
