package InventoryControl;

import BackEnd.Date;

/*
 * List attached to a medication. 
 * This shall contain information about a specific shipment of a medication, meeting the requirement to 
 * check expiration dates. As the "batch" of medication from 6 months ago has a different expiration then the
 * "batch" of medication from this week, but both are in the pharmacy inventory. 
 */

public class BatchMedication {
	Date arrivalDate;
	Date expirationDate;
	int Stock;
	
	public BatchMedication() {};
	public BatchMedication(Date arrivalDate, Date expirationDate, int stock) {
		this.arrivalDate = arrivalDate;
		this.expirationDate = expirationDate;
		this.Stock = stock;
	}
	
	public long daysToExpiration(Date currentDate) {
		return -1;
		
	}
	
	public Date getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(int month, int day, int year) {
		this.arrivalDate = new Date(month, day, year);
	}
	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public void setExpirationDate(int month, int day, int year) {
		this.expirationDate = new Date(month, day, year);
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public int getStock() {
		return Stock;
	}
	public void setStock(int stock) {
		Stock = stock;
	}
	
}
