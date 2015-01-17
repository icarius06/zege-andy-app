package com.zege.devtest;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.zege.devtest.adapters.LazyAdapter;
import com.zege.devtest.adapters.ListHeaderAdapter;
import com.zege.devtest.domain.Session;
import com.zege.devtest.flatui.views.FlatButton;
import com.zege.devtest.flatui.views.FlatTextView;
import com.zege.devtest.logger.CustomLoggerClient;
import com.zege.devtest.models.TransactionModel;
import com.zege.devtest.utils.Constants;

public class MainActivity extends ActionBarActivity {

	/**
	 * An array of values to be displayed on the list view
	 */
	private ArrayList<TransactionModel> transactionsArrayList = new ArrayList<TransactionModel>();

	/**
	 * This will act as the offline data store
	 */
	private WebView webView;

	/**
	 * Private variables
	 */
	private ListView transactionsList;
	private ProgressDialog pleaseWait;

	/**
	 * Here is the start of the app where i initialize stuff
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Constants.initializeFlatUi(this);
		setContentView(R.layout.activity_main);
		// getScreenSize();

		// initialize the list of transactions from the offline db
		initializeWebView();

		transactionsArrayList = new ArrayList<TransactionModel>();

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new TransactionsHomeFragment())
					.commit();
		}
		pleaseWait = new ProgressDialog(this);
		pleaseWait.setMessage("Please wait ...");
		pleaseWait.show();
	}

	/**
	 * set up js stuff here
	 */
	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	public void initializeWebView() {

		// Pass the web view across the app
		Session gs = (Session) getApplication();

		// Step 1: Create WebView
		if (gs.getWebView() == null) {
			webView = new WebView(this);
			gs.setWebView(webView);
		} else {
			webView = gs.getWebView();
		}
		// Step 2: Load html page from assets
		webView.loadUrl("file:///android_asset/www/index.html");
		// Step 2.1: Set custom webchrome client to allow logging
		webView.setWebChromeClient(new CustomLoggerClient());
		// Step 3: Enable JavaScript
		webView.getSettings().setJavaScriptEnabled(true);
		// Step 4: Add our js interface for objects transfer
		webView.addJavascriptInterface(new MainJsInterface(this), "MyAndroid");

		// webView.setWebViewClient(new WebViewClient() {
		// public void onPageFinished(WebView view, String url) {
		// //webView.loadUrl("javascript: getAllTransactionItems	()");
		// }
		// });

	}

	// Menu stuff
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class TransactionsHomeFragment extends Fragment {

		public TransactionsHomeFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {

			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);

			initializeButtons(rootView);

			return rootView;
		}

		public void initializeButtons(View rootView) {
			FlatButton new_transactionBtn = (FlatButton) rootView
					.findViewById(R.id.button_add_new);
			new_transactionBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					MainActivity activity = (MainActivity) getActivity();
					Intent i = new Intent(activity,
							AddTransactionActivity.class);
					startActivity(i);
				}
			});

			FlatButton filterBtn = (FlatButton) rootView
					.findViewById(R.id.button_filter);
			filterBtn.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// Show alert dialog with filter fields
					/* Alert Dialog Code Start */
					AlertDialog.Builder alert = new AlertDialog.Builder(
							getActivity());
					alert.setTitle("Filter Transactions By"); // Set Alert
																// dialog
																// title here
					LinearLayout layout = new LinearLayout(getActivity());
					Spinner spinner = new Spinner(getActivity());

					spinner.setLayoutParams(new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT, 1f));
					spinner.setGravity(Gravity.TOP);

					final Spinner valuesSpinner = new Spinner(getActivity());
					valuesSpinner
							.setLayoutParams(new LinearLayout.LayoutParams(
									LayoutParams.MATCH_PARENT,
									LayoutParams.MATCH_PARENT, 1f));

					// Create an ArrayAdapter using the string array and a
					// custom spinner layout
					ArrayAdapter<CharSequence> adapter = ArrayAdapter
							.createFromResource(getActivity(),
									R.array.filter_fields_array,
									R.layout.spinner_button);

					// Specify the layout to use when the list of choices
					// appears
					adapter.setDropDownViewResource(R.layout.simple_flat_list_item);

					spinner.setAdapter(adapter);

					spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

						@Override
						public void onItemSelected(AdapterView<?> arg0,
								View arg1, int position, long arg3) {
							FlatTextView fv = (FlatTextView) arg1;
							fv.getAttributes().setRadius(0);

							switch (position) {
							case 0:
								valuesSpinner.setVisibility(View.VISIBLE);
								valuesSpinner.setAdapter(ArrayAdapter
										.createFromResource(getActivity(),
												R.array.priority_array,
												R.layout.spinner_button));
								break;
							case 1:
								valuesSpinner.setVisibility(View.VISIBLE);
								valuesSpinner.setAdapter(ArrayAdapter
										.createFromResource(getActivity(),
												R.array.message_status_array,
												R.layout.spinner_button));
								break;
							case 2:
								valuesSpinner.setVisibility(View.GONE);
								break;

							default:
								break;
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> arg0) {
						}
					});

					layout.setOrientation(LinearLayout.VERTICAL);
					layout.setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT));
					layout.setPadding(10, 10, 10, 10);

					layout.addView(spinner);
					layout.addView(valuesSpinner);
					alert.setView(layout);

					alert.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int whichButton) {
									// Get as string input data in this
									// variable.
									// here we convert the input to a string and
									// do the necessary

									Toast.makeText(getActivity(),
											"Do the necessary",
											Toast.LENGTH_SHORT).show();
								} // End of onClick(DialogInterface dialog, int
									// whichButton)
							}); // End of alert.setPositiveButton

					AlertDialog alertDialog = alert.create();
					alertDialog.show();
					/* Alert Dialog Code End */
				}
			});

		}

	}

	/** Index db stuff */
	public class MainJsInterface {

		private Context mContext;

		public MainJsInterface(Context c) {
			mContext = c;
		}

		/**
		 * Get the transactions loaded via js an array is passed then extracted
		 * 
		 * @param arr
		 */
		@JavascriptInterface
		public void loadAllTransactions(String[] arr) {
			transactionsArrayList.clear();
			for (int i = 0; i < arr.length; i++) {
				TransactionModel model = getModelFromString(arr[i]);
				if (!transactionsArrayList.contains(model)) {
					transactionsArrayList.add(model);
				}
			}

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					final MainActivity activity = (MainActivity) mContext;

					ListView header = (ListView) findViewById(R.id.headerList);
					header.setAdapter(new ListHeaderAdapter(activity));

					transactionsList = (ListView) findViewById(R.id.transactions_list);

					final ArrayList<TransactionModel> temp = activity
							.getTransactionsArrayList();

					transactionsList
							.setAdapter(new LazyAdapter(activity, temp));
					transactionsList
							.setOnItemClickListener(new OnItemClickListener() {
								@Override
								public void onItemClick(AdapterView<?> arg0,
										View arg1, int position, long arg3) {
									TransactionModel transactionModel = new TransactionModel();
									transactionModel.setUnits(temp
											.get(position).getUnits());
									transactionModel.setMessage_status("Sent");
									transactionModel.setAmount(temp.get(
											position).getAmount());
									transactionModel.setTran_color(temp.get(
											position).getTran_color());
									transactionModel.setParticulars(temp.get(
											position).getParticulars());
									transactionModel.setCreated_date_time(temp
											.get(position)
											.getCreated_date_time());
									Intent i = new Intent(activity,
											SelectedTransactionActivity.class);
									i.putExtra("model", transactionModel);
									startActivity(i);
								}
							});
					pleaseWait.dismiss();
				}
			});

		}

		private TransactionModel getModelFromString(String stringFromJS) {
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
	}

	public ArrayList<TransactionModel> getTransactionsArrayList() {
		return transactionsArrayList;
	}
}
