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
import com.fasterxml.jackson.databind.ObjectMapper;
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
	public enum actionType {
		Manager,
		Pharmacist,
		PharmacyTech,
		Cashier
		
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
	
	
	
	/* 
	 * EMPLOYEE ACCOUNTS 
	 */
	//adds account to account database. 
	public static void addEmployeeAccount(Employee newEmployee) throws Exception{
		//serialize account
		Employee acc = Serializer.encryptEmployee(newEmployee);
		
		//Send off to update storage
		writeEmployee(acc);
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
	
	//finds and returns Account. 
	//Returns null if lockout. Returns empty employee if failed.
	public static Employee logIn(String username, String password) throws Exception{
		
		//get the accounts. 
		ArrayList<Employee> accounts = readEmployeeStorage(FileHelper.findEmployeeFile());
		
		//See if the accounts list contains the username. 
		for (Employee acc : accounts) {
			//account found
			if (acc.getUsername().equals(username)) {
				//check account lockout. 
				if (acc.getLoginAttempts() >= LOCKOUT_COUNT)
					return null;
				
				String secCode = Serializer.decryptString(acc.getSecCodePass(), password);
				if (Serializer.decryptString(acc.getPassword(), secCode).equals(password)) {
					//Reset the account lockout count, check first to reduce load. 
					if (acc.getLoginAttempts() > 0) {
						acc.setLoginAttempts(0);
						writeEmployee(accounts);
					}
					
					return Serializer.decryptEmployee(acc, secCode);	
				}
				
				//log in failed, update login attempts. 
				acc.setLoginAttempts(acc.getLoginAttempts() + 1);
				writeEmployee(accounts);
				return new Employee();
			}
		}
		return new Employee();
	}
	
	//check if the account exists based off a user name. 
	public static Boolean employeeExists(String username) throws Exception {
		ArrayList<Employee> employees = readEmployeeStorage(FileHelper.findEmployeeFile());
		
		//See if the accounts list contains the username. 
		for (Employee emp : employees) {
			if (emp.getUsername().equals(username)) {
				return true;
			}
		}
		
		return false;
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
		ObjectMapper mapper = new XmlMapper();
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
		ObjectMapper mapper = new XmlMapper();
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
			ObjectMapper mapper = new XmlMapper(); 
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
		ObjectMapper mapper = new XmlMapper();
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
		ObjectMapper mapper = new XmlMapper();
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
			ObjectMapper mapper = new XmlMapper(); 
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
