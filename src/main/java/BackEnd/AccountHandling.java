package BackEnd;

/* TODO: create a manager key that can open up customer accounts. 
 * 
 */

/*
 * ACCOUNT DATABASE
 * Note: This is of course not the database. that will be an encrypted text file. 
 * The system shall store all created accounts in a secure location. Individual account types shall have different access 
 * permissions to this database. (i.e. only certain roles may create accounts and customer roles may only read their own data.)
 * 
 * ACCOUNT HANDLING
 * The system shall allow Account Handling to serve as the communication between front end and back end to access the account database, returning restricted information based on the user/requester's permissions.
 * Shall communicate with the Account Database and Encryption. 
 * 
 * ACCOUNT DELETION
 * The system shall allow only certain user types to update account information. 
 * All user types can update their account's password while logged in, but only the pharmacy manager can update a user's account type.
 * 
 * ACCOUNT LOCK OUT:
 * The system shall lock any account that performs 5 unsuccessful login attempts. 
 * When an account is locked, no further login attempts are allowed until the account is manually unlocked by the pharmacy manager.
 * 
 * UNLOCK ACCOUNT: 
 * The system shall allow only the pharmacy manager to unlock an account that has been locked due to unsuccessful login attempts.
 * 
 * MANAGEMENT USER ACCOUNT CREATION:
 * The system shall allow for the pharmacy manager to add new user accounts to the system and assign them with a user type.
 */

//Handle account creation, deletion, and editing. 
//MUST verify accessing account role in the backend. 

import BackEnd.Serializer;

import BackEnd.Account;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	/* ACCOUNT ACTIONS */
	//adds account to account database. 
	public static void addAccount(Account newAccount) throws Exception{
		//serialize account
		Account acc = Serializer.encryptAccount(newAccount);
		
		//Send off to update storage
		writeAccount(acc);
	}
	
	//finds and returns Account. Returns null if account is not found.
	public static Account logIn(String username, String password) throws Exception{
		/*
		//get the accounts. 
		ArrayList<Account> accounts = readAccountStorage(FileHelper.findAccountsFile());
		
		//See if the accounts list contains the username. 
		for (Account acc : accounts) {
			//account found
			if (acc.getUsername().equals(username)) {
				String secCode = Serializer.decryptString(acc.getSecCodePass(), password);
				if (Serializer.decryptString(acc.getPassword(), secCode).equals(password)) {
					return Serializer.decryptAccount(acc, secCode);	
				}
			}
		}*/
		return null;
	}
	
	//check if the account exists based off a user name. 
	public static Boolean accountExists(String username) throws Exception {
		ArrayList<Account> accounts = readAccountStorage(FileHelper.findAccountsFile());
		
		//See if the accounts list contains the username. 
		for (Account acc : accounts) {
			if (acc.getUsername().equals(username)) {
				return true;
			}
		}
		
		return false;
	}
	
	//updates a user account based on username. Verification done in controller.
	public static void changeAccount(String username, Account acc) throws Exception {
		//get accounts list:
		ArrayList<Account> accounts = readAccountStorage(FileHelper.findAccountsFile());
		
		//find the account
		for (Account a : accounts) {
			if (a.getUsername().equals(username)) {
				accounts.set(accounts.indexOf(a), Serializer.encryptAccount(acc));
				break;
			}
		}
		//update
		AccountHandling.writeAccount(accounts);
	}

	//TODO
	//Role authentication. Checks the database, rather than client side info. 
	public static boolean authenticateAccountRole(String username, String password, List<Role> allowedRoles) throws Exception {
		return true;
	}
	
	
	/* ACCOUNT FILE HANDLING */
	//writes an ecrypted Account object to storage. 
	private static void writeAccount(Account encryptedAccount) throws Exception {
		//Get file
		Path p;
		try {
			p = FileHelper.findAccountsFile();
		} catch (Exception e) {
			//This could be due to different platforms such as mac/linux. Addapt for these.
			e.printStackTrace();
			throw new Exception("Error finding or creating file! " + e);
		}
		ObjectMapper mapper = new XmlMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		//Get the current list of accounts and add the new one to it.
		ArrayList<Account> accounts = readAccountStorage(p);
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
	private static void writeAccount(ArrayList<Account> encryptedList) throws Exception {
		//Get file
		Path p;
		try {
			p = FileHelper.findAccountsFile();
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
	public static ArrayList<Account> readAccountStorage(Path p) throws Exception{
		//Check that the file exists. 
		if(!Files.exists(p))
			throw new Exception("Cannot load settings because the settings file path could not be loaded.");
		
		//If the file is empty, return an empty list. 
		if (p.toFile().length() == 0)
			return new ArrayList<Account>();
		
		//Read the file into an array list.
		try {
			ObjectMapper mapper = new XmlMapper(); 
			TypeReference<ArrayList<Account>> typeRef = new TypeReference<ArrayList<Account>>() {};
			ArrayList<Account> accounts = mapper.readValue(Files.newInputStream(p), typeRef);
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
