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

    /**
     * Represents a prescription with associated details such as customer name, medication name, and status.
     * This class provides getter and setter methods for the prescription attributes.
     */
    public Prescription(int id, String customerName, String medicationName, String status) {
        this.id = id; // Unique identifier for the prescription
        this.customerName = customerName; // Name of the customer for whom the prescription is issued
        this.medicationName = medicationName; // Name of the prescribed medication
        this.status = status; // Current status of the prescription (e.g., pending, fulfilled, etc.)
    }

    // Getter and setter methods for the Prescription attributes.

    /**
     * Retrieves the unique identifier for the prescription.
     * @return The prescription ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier for the prescription.
     * @param id The new prescription ID.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the customer associated with the prescription.
     * @return The customer's name.
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the name of the customer associated with the prescription.
     * @param customerName The new customer name.
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Retrieves the name of the prescribed medication.
     * @return The medication name.
     */
    public String getMedicationName() {
        return medicationName;
    }

    /**
     * Sets the name of the prescribed medication.
     * @param medicationName The new medication name.
     */
    public void setMedicationName(String medicationName) {
        this.medicationName = medicationName;
    }

    /**
     * Retrieves the current status of the prescription.
     * @return The prescription status.
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the current status of the prescription.
     * @param status The new prescription status.
     */
    public void setStatus(String status) {
        this.status = status;
    }
}