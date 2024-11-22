package BackEnd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
	/* EMPLOYEE ACCOUNTS */
	//adds account to account database. 
	public static void addEmployeeAccount(Employee newEmployee) throws Exception{
		//serialize account
		Employee acc = Serializer.encryptEmployee(newEmployee);
		
		//Send off to update storage
		writeEmployee(acc);
	}
	
	//finds and returns Account. Returns null if account is not found.
	public static Employee logIn(String username, String password) throws Exception{
		
		//get the accounts. 
		ArrayList<Employee> accounts = readEmployeeStorage(FileHelper.findEmployeeFile());
		
		//See if the accounts list contains the username. 
		for (Employee acc : accounts) {
			//account found
			if (acc.getUsername().equals(username)) {
				String secCode = Serializer.decryptString(acc.getSecCodePass(), password);
				if (Serializer.decryptString(acc.getPassword(), secCode).equals(password)) {
					return Serializer.decryptEmployee(acc, secCode);	
				}
			}
		}
		return null;
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
		AccountHandling.writeAccount(accounts, Employee.class);
	}

	//TODO
	//Role authentication. Checks the database, rather than client side info. 
	public static boolean authenticateAccountRole(String username, String password, List<Role> allowedRoles) throws Exception {
		return true;
	}
	
	
	/* ACCOUNT FILE HANDLING */
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
	private static <T> void writeAccount(ArrayList<T> encryptedList, Class<T> classType) throws Exception {
		//Get file
		Path p;
		try {
			//either customer or employee file. 
			p = classType == Customer.class ? FileHelper.findCustomerFile() : FileHelper.findEmployeeFile();
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

}
