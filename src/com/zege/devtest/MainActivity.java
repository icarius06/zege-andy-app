package com.zege.devtest;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.zege.devtest.adapters.LazyAdapter;
import com.zege.devtest.adapters.ListHeaderAdapter;
import com.zege.devtest.domain.Session;
import com.zege.devtest.flatui.views.FlatButton;
import com.zege.devtest.logger.CustomLoggerClient;
import com.zege.devtest.models.TransactionModel;
import com.zege.devtest.utils.Constants;

public class MainActivity extends ActionBarActivity {

	/**
	 * An array of values to be displayed on the list view
	 */
	private ArrayList<String> transactionsArrayList = new ArrayList<String>();

	/**
	 * This will act as the offline data store
	 */

	private WebView webView;

	private Context context;
	ListView transactionsList;

	/**
	 * Here is the start of the app where i initialize stuff
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Constants.initializeFlatUi(this);

		setContentView(R.layout.activity_main);
		//getScreenSize();

		context = this;

		initializeWebView();

		// initialize the list of transactions from the offline db

		transactionsArrayList = new ArrayList<String>();

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new TransactionsHomeFragment())
					.commit();
		}
	}

	@SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
	public void initializeWebView() {
		// Step 1: Create WebView
		webView = new WebView(this);
		// Step 2: Load html page from assets
		webView.loadUrl("file:///android_asset/www/index.html");
		// Step 2.1: Set custom webchrome client to allow logging
		webView.setWebChromeClient(new CustomLoggerClient());
		// Step 3: Enable JavaScript
		webView.getSettings().setJavaScriptEnabled(true);
		// Step 4: Add our js interface for objects transfer
		webView.addJavascriptInterface(new MainJsInterface(this), "MyAndroid");

		webView.setWebViewClient(new WebViewClient() {
			public void onPageFinished(WebView view, String url) {
				webView.loadUrl("javascript: getAllTransactionItems	()");
			}
		});

		Session gs = (Session) getApplication();
		gs.setWebView(webView);
	}

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

	public void addToTransactionsArrayList(TransactionModel model) {
		transactionsArrayList.add(model.getAmount());
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

			return rootView;
		}

	}

	/** Index db stuff */

	public class MainJsInterface {

		Context mContext;

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
			// arr = getResources().getStringArray(R.array.themes_array);
			for (int i = 0; i < arr.length; i++) {
				if (!transactionsArrayList.contains(arr[i])) {
					transactionsArrayList.add(arr[i]);
				}
			}
						
			runOnUiThread(new Runnable() {

				@Override
				public void run() {
					final MainActivity activity = (MainActivity) context;

					ListView header = (ListView) findViewById(R.id.headerList);
					header.setAdapter(new ListHeaderAdapter(activity));

					transactionsList = (ListView) findViewById(R.id.transactions_list);

					final ArrayList<String> temp = activity
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
											.get(position));
									transactionModel.setMessage_status("Sent");
									transactionModel.setAmount("Kes 1500");
									transactionModel.setTran_color("Green");
									transactionModel
											.setParticulars("Like a tablet but different screen");

									Intent i = new Intent(activity,
											SelectedTransactionActivity.class);
									i.putExtra("model", transactionModel);

									startActivity(i);
								}
							});
				}
			});

		}
	}

	public ArrayList<String> getTransactionsArrayList() {
		return transactionsArrayList;
	}
/*
	@SuppressLint("NewApi")
	public void getScreenSize() {
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		String text = String.valueOf(width) + "<<" + String.valueOf(height);
		Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
	}*/
}
