package InventoryControl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
/*
 * List attached to a medication. 
 * This shall contain information about a specific shipment of a medication, meeting the requirement to 
 * check expiration dates. As the "batch" of medication from 6 months ago has a different expiration then the
 * "batch" of medication from this week, but both are in the pharmacy inventory. 
 */

public class BatchMedication {
	LocalDate arrivalDate;
	LocalDate expirationDate;
	int stock;
	ArrayList<String> records;
	
	
	public BatchMedication() {};
	public BatchMedication(LocalDate arrivalDate, LocalDate expirationDate, int stock) {
		this.arrivalDate = arrivalDate;
		this.expirationDate = expirationDate;
		this.stock = stock;
	}
	
	public long daysToExpiration(LocalDate currentDate) {
		return currentDate.until(expirationDate, ChronoUnit.DAYS);
	}
	public String recordsAsString() {
		String res = "";
		for (String s : records)
			res += s + "\n";
		return res;
	}
	
	public boolean updateStock(int stock, String name) {
		if (stock < 0)
			return false;
		if(records == null)
			records = new ArrayList<String>();
		String edit;
		//find if it's a removal or addition
		if (stock < this.stock) 
			edit = "removed " + String.valueOf(this.stock - stock) + " from";
		else
			edit = "added " + String.valueOf(stock - this.stock) + " to";
		
		String item = LocalDate.now() + ": " + edit + " the batch by " + name + ".";
		
		records.add(item);
			
		this.stock = stock;
		return true;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public ArrayList<String> getRecords() {
		return records;
	}
	public void setRecords(ArrayList<String> records) {
		this.records = records;
		
	}
	public LocalDate getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(LocalDate arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public LocalDate getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}
	
}
