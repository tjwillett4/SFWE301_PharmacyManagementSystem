package InventoryControl;

import java.time.LocalDate;

/*
 * List attached to a medication. 
 * This shall contain information about a specific shipment of a medication, meeting the requirement to 
 * check expiration dates. As the "batch" of medication from 6 months ago has a different expiration then the
 * "batch" of medication from this week, but both are in the pharmacy inventory. 
 */

public class BatchMedication {
	LocalDate arrivalDate;
	LocalDate expirationDate;
	int Stock;
	
	public BatchMedication(LocalDate arrivalDate, LocalDate expirationDate, int stock) {
		this.arrivalDate = arrivalDate;
		this.expirationDate = expirationDate;
		this.Stock = stock;
	}
	
	public long daysToExpiration(LocalDate currentDate) {
		return -1;
		
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
	public int getStock() {
		return Stock;
	}
	public void setStock(int stock) {
		Stock = stock;
	}
	
}
