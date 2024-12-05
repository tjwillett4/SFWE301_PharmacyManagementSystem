package BackEnd;

/*
 * Used for different file and gui helping functions. 
 * 
 * public static Path selectFile(String openingDirectory, String title)
 * public static Boolean confirmationWindow(String message)
 * public static void errorMessage(String title, String message)
 * public static void infoMessage(String title, String message)
 * public static Boolean passwordValid(String password)
 * 
 * 
 */

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import InventoryControl.BatchMedication;
import Prescriptions.Medication;

public class FileHelper {
	public final static String DATA_DIR = System.getenv("APPDATA") + "//pharmacy";
	public final static String ACCOUNT_STORAGE_FILE = "/CustomerStorage.xml";
	public final static String EMPLOYEE_STORAGE_FILE = "/EmployeeStorage.xml";
	public final static String PHARMACY_INVENTORY_FILE = "/PharmacyInventory.xml";
	
	
	//return of property/database files.
	public static Path findPharmacyInventoryFile() throws Exception {
		return findPropertiesFile(PHARMACY_INVENTORY_FILE);
	}
	public static Path findEmployeeFile() throws Exception {
		return findPropertiesFile(EMPLOYEE_STORAGE_FILE);
	}
	public static Path findCustomerFile() throws Exception{
		return findPropertiesFile(ACCOUNT_STORAGE_FILE);
	}
	private static Path findPropertiesFile(String dataFile)  throws Exception {
		
		//Find directory or create it if it doesn't exist.
		Path dir = Paths.get(DATA_DIR);
		if (!Files.exists(dir)) {try {
			Files.createDirectory(dir);
		} catch (IOException e) {
			throw new Exception("Unable to create directory for the program settings. Check permissions. " + e);
		}} 
		
		//find file or create it if it doesn't exist.
		Path file = Paths.get(DATA_DIR + dataFile);
		if (!Files.exists(file)) {try {
			Files.createFile(file);
		} catch (IOException e) {
			throw new Exception("Unable to create program settings file. Check permissions. " + e);
		}}

		return file;
	}
	
	//file selector gui
	public static Path selectFile(String openingDirectory, String title) {
		File directory = (openingDirectory==null)? new File(System.getProperty("user.dir"))
												: new File(openingDirectory);
		JFileChooser chooser = new JFileChooser(directory);
		chooser.setDialogTitle(title);
		chooser.setAcceptAllFileFilterUsed(true);
		

		int option = chooser.showOpenDialog(null);
		if(option == JFileChooser.APPROVE_OPTION) 
			return chooser.getSelectedFile().toPath();
		return null;
	}
	
	//GUI's used to prompt the user with information.
	public static Boolean confirmationWindow(String message) {
		int dialogResult = JOptionPane.showConfirmDialog(null, message);
		return dialogResult == JOptionPane.YES_OPTION;
	}
	public static void errorMessage(String title, String message) {
		JOptionPane.showMessageDialog(null,
			    "Error: " + message,
			    title,
			    JOptionPane.ERROR_MESSAGE);
	}
	public static void infoMessage(String title, String message) {
		JOptionPane.showMessageDialog(null,
			    "Error: " + message,
			    title,
			    JOptionPane.INFORMATION_MESSAGE);
	}

	//Checks password against certain criteria to see if it meets requirements. 
	public static Boolean passwordValid(String password) {
		//check for password length
		boolean validCheck = false;
		if (password != null && password.length () >= 8) {
			validCheck = true;
		}
		else {
			infoMessage("Info", "Password must be at least 8 characters long");
		}
		
		return validCheck;
	}
	
	// Read employee storage from a file
    public static ArrayList<Employee> readEmployeeStorage(Path employeeFile) throws IOException {
        if (!Files.exists(employeeFile) || employeeFile.toFile().length() == 0) {
            return new ArrayList<>(); // Return an empty list if the file does not exist or is empty
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            return mapper.readValue(Files.newInputStream(employeeFile),
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Employee.class));
        } catch (IOException e) {
            throw new IOException("Error reading employee storage file: " + e.getMessage(), e);
        }
    }

    // Update an employee in the storage file
    public static void updateEmployee(Employee employee) throws IOException {
        Path employeeFile;
        try {
            employeeFile = FileHelper.findEmployeeFile();
        } catch (Exception e) {
            throw new IOException("Error finding employee file: " + e.getMessage(), e);
        }

        ArrayList<Employee> employees = readEmployeeStorage(employeeFile);

        boolean updated = false;
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getUsername().equals(employee.getUsername())) {
                employees.set(i, employee);
                updated = true;
                break;
            }
        }

        if (!updated) {
            employees.add(employee);
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.writeValue(Files.newOutputStream(employeeFile), employees);
    }
    
    public static ArrayList<Medication> getAllMedications() throws Exception {
        Path medicationFile = findPharmacyInventoryFile();

        if (!Files.exists(medicationFile) || medicationFile.toFile().length() == 0) {
            return new ArrayList<>(); // Return an empty list if the file does not exist or is empty
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            ArrayList<BatchMedication> batchMedications = mapper.readValue(
                Files.newInputStream(medicationFile),
                mapper.getTypeFactory().constructCollectionType(ArrayList.class, BatchMedication.class)
            );

            // Convert BatchMedication to Medication
            return batchMedications.stream().map(batch -> {
                Medication medication = new Medication();
                medication.setName(batch.getMedicationName());
                medication.setStock(batch.getStock());
                ArrayList<BatchMedication> batches = new ArrayList<>();
                batches.add(batch);
                medication.setBatches(batches);
                return medication;
            }).collect(Collectors.toCollection(ArrayList::new));

        } catch (IOException e) {
            throw new IOException("Error reading medication storage file: " + e.getMessage(), e);
        }
    }
    
    public static ArrayList<Medication> searchMedications(String keyword, String category, String supplier, boolean inStockOnly) throws Exception {
        // Fetch all medications
        ArrayList<Medication> allMedications = getAllMedications();

        // Filter based on the provided criteria
        ArrayList<Medication> filteredMedications = new ArrayList<>();
        for (Medication medication : allMedications) {
            boolean matchesKeyword = (keyword == null || keyword.isEmpty()) || medication.getName().toLowerCase().contains(keyword.toLowerCase());
            boolean matchesCategory = (category == null || category.isEmpty()) || medication.getCategory().equalsIgnoreCase(category);
            boolean matchesSupplier = (supplier == null || supplier.isEmpty()) || medication.getSupplier().equalsIgnoreCase(supplier);
            boolean matchesStock = !inStockOnly || medication.getStock() > 0;

            if (matchesKeyword && matchesCategory && matchesSupplier && matchesStock) {
                filteredMedications.add(medication);
            }
        }
        return filteredMedications;
    }
    
    public static ArrayList<Customer> getAllCustomers() throws Exception {
        Path customerFile = findCustomerFile();

        if (!Files.exists(customerFile) || customerFile.toFile().length() == 0) {
            return new ArrayList<>(); // Return an empty list if the file does not exist or is empty
        }

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        try {
            return mapper.readValue(Files.newInputStream(customerFile),
                    mapper.getTypeFactory().constructCollectionType(ArrayList.class, Customer.class));
        } catch (IOException e) {
            throw new IOException("Error reading customer storage file: " + e.getMessage(), e);
        }
    }
    
    public static void removeCustomer(Customer customer) throws Exception {
        Path customerFile = findCustomerFile();
        ArrayList<Customer> customers = getAllCustomers();

        if (customers.removeIf(existingCustomer -> existingCustomer.getPhoneNum().equals(customer.getPhoneNum()))) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.writeValue(Files.newOutputStream(customerFile), customers);
        } else {
            throw new IOException("Customer not found in storage.");
        }
    }
}
