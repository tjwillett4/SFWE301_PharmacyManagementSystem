package BackEnd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/*
 * Used to interact with encrypted files/user information. A kind of service for the in-between stages
 * 
 * ALL exceptions bubbled upwards.
 */


public class AccountHandling {
	private final static int LOCKOUT_COUNT = 5;
	// Getter for LOCKOUT_COUNT
    public static int getLockoutCount() {
        return LOCKOUT_COUNT;
    }

	//Role requirement check. Takes in the empoyee attempting and a list of roles ["manager", "pharamacist"]
	//TODO: Should this require password?
	//TODO: Implement this with all account actions. 
	private static boolean roleVerification(Employee emp, List<Role> roleRequirement){
		//get the account list
		ArrayList<Employee> accounts;
		try {
			accounts = readEmployeeStorage(FileHelper.findEmployeeFile());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		//Find the account
		for (Employee acc : accounts) {
			//account found
			if (acc.getUsername().equals(emp.getUsername())) {
				//check if the account has a role contained in "roleRequirement" list
				if (roleRequirement.contains(acc.getAccountRole()) ) 
					return true;
			}
		}
		return false;
	}
	
	/**
     * Creates a default admin account (bypassing encryption)
     */
	public static void createDefaultAccounts() throws Exception {
	    // Define default accounts
	    List<Employee> defaultAccounts = Arrays.asList(
	        new Employee("admin", "password", Role.pharmacyManager) // Admin account
	    );

	    // Read existing accounts
	    ArrayList<Employee> existingAccounts = readEmployeeStorage(FileHelper.findEmployeeFile());

	    // Add default accounts if they don't exist
	    for (Employee defaultAccount : defaultAccounts) {
	        boolean accountExists = existingAccounts.stream()
	            .anyMatch(emp -> emp.getUsername().equals(defaultAccount.getUsername()));

	        if (!accountExists) {
	            System.out.println("Creating default account: " + defaultAccount.getUsername());
	            addEmployeeAccount(defaultAccount); // Add default account
	        } else {
	            System.out.println("Account already exists: " + defaultAccount.getUsername());
	        }
	    }
	}

    /**
     * Handles user login with a simplified check.
     * returns Exception for file handling errors. 
     * returns an Employee account with a username containing the error. 
     * On successful login, return full account. 
     * 
     */
	public static Employee logIn(String username, String password) throws Exception {
	    ArrayList<Employee> accounts = readEmployeeStorage(FileHelper.findEmployeeFile());
	    
	    //prompts:
        String invalidPassword = "Invalid login attempt for username: " + username;
        String accountLocked = "Account is locked for username: " + username;
        String userNotFound = "User not found: " + username;

	    for (Employee acc : accounts) {
	        if (acc.getUsername().equals(username)) {
	            System.out.println("Found account for username: " + username);

	            // Check account lockout
	            if (acc.getLoginAttempts() >= LOCKOUT_COUNT) {
	                System.out.println(accountLocked);
	                return new Employee(accountLocked);
	            }

	            // Password check
	            if (acc.getPassword().equals(password)) {
	                System.out.println("Login successful for username: " + username);
	                acc.setLoginAttempts(0); // Reset login attempts
	                writeEmployee(accounts); // Save changes
	                return acc;
	            } else {
	                acc.setLoginAttempts(acc.getLoginAttempts() + 1);
	                writeEmployee(accounts); // Save changes
	                System.out.println(invalidPassword);
	                return new Employee(invalidPassword); // Empty Employee on failure
	            }
	        }
	    }

	    System.out.println(userNotFound);
	    return new Employee(userNotFound); // Empty Employee on failure
	}

    /**
     * Unlocks a locked account, ensuring only managers can perform this action.
     */
    public static boolean unlockEmployeeAccount(String username, String managerUsername) throws Exception {
        Employee manager = getEmployeeByUsername(managerUsername);
        if (manager == null || manager.getAccountRole() != Role.pharmacyManager) {
            throw new Exception("Unauthorized: Only managers can unlock accounts.");
        }

        ArrayList<Employee> accounts = readEmployeeStorage(FileHelper.findEmployeeFile());

        for (Employee acc : accounts) {
            if (acc.getUsername().equals(username)) {
                acc.setLoginAttempts(0); // Reset login attempts
                writeEmployee(accounts);
                System.out.println("Account unlocked for user: " + username);
                return true;
            }
        }

        System.out.println("User not found: " + username);
        return false; // User not found
    }

    // Helper method to check if an employee exists
    public static boolean employeeExists(String username) throws Exception {
        ArrayList<Employee> employees = readEmployeeStorage(FileHelper.findEmployeeFile());

        for (Employee emp : employees) {
            if (emp.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adds an employee to the storage (bypasses encryption for simplicity).
     */
    public static void addEmployeeAccount(Employee newEmployee) throws Exception {
        // Get the current list of accounts
        ArrayList<Employee> accounts = readEmployeeStorage(FileHelper.findEmployeeFile());

        // Add the new account
        accounts.add(newEmployee);

        // Write the updated list back to storage
        writeEmployee(accounts);
    }

	//Removes an employee from the database
	public static boolean removeEmployeeAccount(Employee emp, Employee managerAccount) throws Exception {
		//check for role requirement
		if (!roleVerification(managerAccount, Arrays.asList(Role.pharmacyManager)))
			return false;
		
		//get the account list
		ArrayList<Employee> accounts = readEmployeeStorage(FileHelper.findEmployeeFile());
		
		
		//Find the account
		for (Employee acc : accounts) {
			//account found
			if (acc.getUsername().equals(emp.getUsername())) {
				//remove account from list, write list. 
				accounts.remove(emp);
				writeEmployee(accounts);
				return true;
		
			}
		}
		return false;
	}
	
	public static void updateEmployeeAccount(String username, Employee updatedEmployee) throws Exception {
	    ArrayList<Employee> accounts = readEmployeeStorage(FileHelper.findEmployeeFile());

	    for (Employee account : accounts) {
	        if (account.getUsername().equals(username)) {
	            accounts.set(accounts.indexOf(account), updatedEmployee); // Replace with updated employee
	            writeEmployee(accounts); // Save changes to storage
	            return;
	        }
	    }

	    throw new Exception("Employee with username '" + username + "' not found.");
	}
	
	//UnlockAccount
	public static boolean unlockEmployeeAccount(Employee emp, Employee managerAccount) throws Exception{
		//check for role requirement
		if (!roleVerification(managerAccount, Arrays.asList(Role.pharmacyManager)))
			return false;
		
		//get the account list
		ArrayList<Employee> accounts = readEmployeeStorage(FileHelper.findEmployeeFile());
		
		
		//Find the account
		for (Employee acc : accounts) {
			//account found
			if (acc.getUsername().equals(emp.getUsername())) {
				//remove account from list, write list. 
				acc.setLoginAttempts(0);
				writeEmployee(accounts);
				return true;
		
			}
		}
		return false;
	}
	
	// Method to get an employee by their username
    public static Employee getEmployeeByUsername(String username) throws Exception {
        ArrayList<Employee> employees = FileHelper.readEmployeeStorage(FileHelper.findEmployeeFile());

        for (Employee employee : employees) {
            if (employee.getUsername().equals(username)) {
                return employee; // Return the matching employee
            }
        }

        return null; // No matching employee found
    }
	
	// Increment login attempts for a user
    public static void incrementLoginAttempts(String username) throws Exception {
        Path employeeFile = FileHelper.findEmployeeFile();
        ArrayList<Employee> employees = FileHelper.readEmployeeStorage(employeeFile);

        for (Employee employee : employees) {
            if (employee.getUsername().equals(username)) {
                employee.setLoginAttempts(employee.getLoginAttempts() + 1);
                FileHelper.updateEmployee(employee);
                return;
            }
        }
    }
	
	// Check if a user's account is locked
    public static boolean isAccountLocked(String username) throws Exception {
        Path employeeFile = FileHelper.findEmployeeFile();
        ArrayList<Employee> employees = FileHelper.readEmployeeStorage(employeeFile);

        for (Employee employee : employees) {
            if (employee.getUsername().equals(username)) {
                return employee.getLoginAttempts() >= LOCKOUT_COUNT;
            }
        }
        return false; // User not found
    }
	
	//updates a user account based on username. Verification done in controller.
	public static void changeEmployeeAccount(String username, Employee acc) throws Exception {
		//get accounts list:
		ArrayList<Employee> accounts = readEmployeeStorage(FileHelper.findEmployeeFile());
		
		//find the account
		for (Employee a : accounts) {
			if (a.getUsername().equals(username)) {
				accounts.set(accounts.indexOf(a), Serializer.encryptEmployee(acc));
				break;
			}
		}
		//update
		AccountHandling.writeEmployee(accounts);
	}
	
	
	
	
	/* 
	 * Employee FILE HANDLING 
	 */
	//writes an ecrypted Account object to storage. 
	private static void writeEmployee(Employee encryptedAccount) throws Exception {
		//Get file
		Path p = FileHelper.findEmployeeFile();
		XmlMapper mapper = new XmlMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		//Get the current list of accounts and add the new one to it.
		ArrayList<Employee> accounts =  readEmployeeStorage(p);
		accounts.add(encryptedAccount);
		
		//rewrite the file with the new account added.
		try {
			mapper.writeValue(Files.newOutputStream(p), accounts);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error writing to the file. " + e);
		}		
	}

	//writes an ecrypted Account LIST to storage. 
	private static void writeEmployee(ArrayList<Employee> encryptedList) throws Exception {
		//Get file
		Path p;
		try {
			//either customer or employee file. 
			p = FileHelper.findEmployeeFile();
		} catch (Exception e) {
			//This could be due to different platforms such as mac/linux. Addapt for these.
			e.printStackTrace();
			throw new Exception("Error finding or creating file! " + e);
		}
		XmlMapper mapper = new XmlMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		//rewrite the file with the new account added.
		try {
			mapper.writeValue(Files.newOutputStream(p), encryptedList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error writing to the file. " + e);
		}		
	}
	
	//retrieves all the Account's stored in storage. 
	public static ArrayList<Employee> readEmployeeStorage(Path p) throws Exception{
		//Check that the file exists. 
		if(!Files.exists(p))
			throw new Exception("Cannot load settings because the settings file path could not be loaded.");
		
		//If the file is empty, return an empty list. 
		if (p.toFile().length() == 0)
			return new ArrayList<Employee>();
		
		//Read the file into an array list.
		try {
			XmlMapper mapper = new XmlMapper(); 
			TypeReference<ArrayList<Employee>> typeRef = new TypeReference<ArrayList<Employee>>() {};
			ArrayList<Employee> accounts = mapper.readValue(Files.newInputStream(p), typeRef);
			return accounts;
		} catch(FileNotFoundException e1) {
			e1.printStackTrace();
			throw new Exception("Failed find the settings file.");
		}
		catch (UnrecognizedPropertyException e1){
			e1.printStackTrace();
			throw new Exception("Unrecognized properties found or missing in the selected settings file.");
		} 
		catch(IOException e2) {
			e2.printStackTrace();
			throw new Exception("Failed to open, or close, the desired settings file." + e2);
		}
		catch(InaccessibleObjectException e3) {
			throw new Exception("Tried to load in accessible object from settings... " + e3);
		}
		catch (Exception e4) {
			e4.printStackTrace();
			throw new Exception("Something went wrong while trying to load the selected settings file."); 
		}
	}

	/*
	 * CUSTOMER FILE HANDLING 
	 */
	//writes an ecrypted Account object to storage. 
	private static void writeCustomer(Customer encryptedAccount) throws Exception {
		//Get file
		Path p = FileHelper.findCustomerFile();
		XmlMapper mapper = new XmlMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		//Get the current list of accounts and add the new one to it.
		ArrayList<Customer> accounts =  readCustomerStorage(p);
		accounts.add(encryptedAccount);
		
		//rewrite the file with the new account added.
		try {
			mapper.writeValue(Files.newOutputStream(p), accounts);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error writing to the file. " + e);
		}		
	}

	//writes an ecrypted Account LIST to storage. 
	private static void writeCustomer(ArrayList<Customer> encryptedList) throws Exception {
		//Get file
		Path p;
		try {
			//either customer or employee file. 
			p = FileHelper.findCustomerFile();
		} catch (Exception e) {
			//This could be due to different platforms such as mac/linux. Addapt for these.
			e.printStackTrace();
			throw new Exception("Error finding or creating file! " + e);
		}
		XmlMapper mapper = new XmlMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		//rewrite the file with the new account added.
		try {
			mapper.writeValue(Files.newOutputStream(p), encryptedList);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error writing to the file. " + e);
		}		
	}
	
	//retrieves all the Account's stored in storage. 
	public static ArrayList<Customer> readCustomerStorage(Path p) throws Exception{
		//Check that the file exists. 
		if(!Files.exists(p))
			throw new Exception("Cannot load settings because the settings file path could not be loaded.");
		
		//If the file is empty, return an empty list. 
		if (p.toFile().length() == 0)
			return new ArrayList<Customer>();
		
		//Read the file into an array list.
		try {
			XmlMapper mapper = new XmlMapper(); 
			TypeReference<ArrayList<Customer>> typeRef = new TypeReference<ArrayList<Customer>>() {};
			ArrayList<Customer> accounts = mapper.readValue(Files.newInputStream(p), typeRef);
			return accounts;
		} catch(FileNotFoundException e1) {
			e1.printStackTrace();
			throw new Exception("Failed find the settings file.");
		}
		catch (UnrecognizedPropertyException e1){
			e1.printStackTrace();
			throw new Exception("Unrecognized properties found or missing in the selected settings file.");
		} 
		catch(IOException e2) {
			e2.printStackTrace();
			throw new Exception("Failed to open, or close, the desired settings file." + e2);
		}
		catch(InaccessibleObjectException e3) {
			throw new Exception("Tried to load in accessible object from settings... " + e3);
		}
		catch (Exception e4) {
			e4.printStackTrace();
			throw new Exception("Something went wrong while trying to load the selected settings file."); 
		}
	}
}
