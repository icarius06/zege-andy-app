package com.zege.devtest.domain;

import java.util.ArrayList;

import android.app.Application;
import android.webkit.WebView;

import com.zege.devtest.models.TransactionModel;

/**
 * This is a global POJO that we attach data to, to use across the
 * application
 * 
 * @author Michael Otieno
 * 
 */
public class Session extends Application {
	private WebView webView ;
	
	private ArrayList<TransactionModel> transactions;

	private TransactionModel current_transaction;
	
	public WebView getWebView() {
		return webView;
	}

	public void setWebView(WebView webView) {
		this.webView = webView;
	}

	public TransactionModel getCurrentTransaction(){
		return current_transaction;
	}
	
	public void setCurrentTransaction(TransactionModel transactionModel){
		this.current_transaction = transactionModel;
	}
		
	//Used to allow global access of products list
	public void setTransactionsInSession(ArrayList<TransactionModel> transactions){
		this.transactions = transactions;
	}
	
	public ArrayList<TransactionModel> getTransactionsInSession(){
		return this.transactions;
	}
		
}