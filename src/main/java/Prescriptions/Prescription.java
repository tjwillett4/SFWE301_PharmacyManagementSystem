package Prescriptions;

/*
 * Object holding the prescription information being filled. 
 * Including Patient, medication, insurance, etc. 
 * This is just an organization of prescription information.
 */

public class Prescription {
	private int id;
    private String customerName;
    private String medicationName;
    private String status;

    public Prescription(int id, String customerName, String medicationName, String status) {
        this.id = id;
        this.customerName = customerName;
        this.medicationName = medicationName;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getMedicationName() {
        return medicationName;
    }

    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}