package com.higradius;

public class InvoiceDetails {
	private String name_customer;
	private String cust_number;
	private String clear_date;
	private float total_open_amount;
	private String due_in_date;
	private String notes;

	
	public String getCustNumber() {
		return cust_number;
	}
	public void setCustNumber(String cust_number) {
		this.cust_number = cust_number;
	}
	
	public String getNameCustomer() {
		return name_customer;
	}
	public void setNameCustomer(String name_customer) {
		this.name_customer = name_customer;
	}
	
	public String getClearDate() {
		return clear_date;
	}
	public void setClearDate(String clear_date) {
		this.clear_date = clear_date;
	}
	
	
	public String getDueInDate() {
		return due_in_date;
	}
	public void setDueInDate(String due_in_date) {
		this.due_in_date = due_in_date;
	}
	
	public float getTotalOpenAmount() {
		return total_open_amount;
	}
	public void setTotalOpenAmount(float total_open_amount) {
		this.total_open_amount = total_open_amount;
	}
	
	
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	
}
