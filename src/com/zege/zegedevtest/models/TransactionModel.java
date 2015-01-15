package com.zege.zegedevtest.models;

public class TransactionModel {

	String	transaction_id;
	String units;
	String amount;
	String total;
	String particulars; 
	String tran_color;
	String created_date_time;
	String message_status;
	String synced_id;
	String synced_date_time;
	
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getUnits() {
		return units;
	}
	public void setUnits(String units) {
		this.units = units;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getParticulars() {
		return particulars;
	}
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	public String getTran_color() {
		return tran_color;
	}
	public void setTran_color(String tran_color) {
		this.tran_color = tran_color;
	}
	public String getCreated_date_time() {
		return created_date_time;
	}
	public void setCreated_date_time(String created_date_time) {
		this.created_date_time = created_date_time;
	}
	public String getMessage_status() {
		return message_status;
	}
	public void setMessage_status(String message_status) {
		this.message_status = message_status;
	}
	public String getSynced_id() {
		return synced_id;
	}
	public void setSynced_id(String synced_id) {
		this.synced_id = synced_id;
	}
	public String getSynced_date_time() {
		return synced_date_time;
	}
	public void setSynced_date_time(String synced_date_time) {
		this.synced_date_time = synced_date_time;
	}
}
