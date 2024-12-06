package BackEnd;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Customer extends Account {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNum;
    private String address;
    private List<String> allergies;
    private List<String> prescriptions; // List of prescriptions associated with the customer

    // Default constructor
    public Customer() {}

    // Constructor with all fields
    public Customer(String firstName, String lastName, String email, String phoneNum, String address, List<String> allergies, List<String> prescriptions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.address = address;
        this.allergies = allergies;
        this.prescriptions = prescriptions;
    }

    // Constructor with minimal fields for simpler instantiation
    public Customer(String firstName, String email, String phoneNum) {
        this.firstName = firstName;
        this.email = email;
        this.phoneNum = phoneNum;
        this.allergies = new ArrayList<>();
        this.prescriptions = new ArrayList<>();
    }

    // Getters and setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public List<String> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<String> prescriptions) {
        this.prescriptions = prescriptions;
    }

    // Mock data for testing
    public static List<Customer> getMockCustomers() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("John", "Doe", "john.doe@example.com", "555-1234", "123 Main St",
                Arrays.asList("Peanuts"), Arrays.asList("Ibuprofen 200mg", "Amoxicillin 500mg")));
        customers.add(new Customer("Jane", "Smith", "jane.smith@example.com", "555-5678", "456 Elm St",
                Arrays.asList("Gluten"), Arrays.asList("Loratadine 10mg", "Metformin 500mg")));
        customers.add(new Customer("Alice", "Johnson", "alice.johnson@example.com", "555-8765", "789 Oak St",
                Arrays.asList("Lactose"), Arrays.asList("Paracetamol 500mg", "Atorvastatin 10mg")));
        return customers;
    }

	public String setName(String string) {
		return firstName + lastName;
	}
}