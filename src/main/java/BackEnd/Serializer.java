package BackEnd;

/*
 * The system shall use an encryption model to protect sensitive information 
 * such as account passwords in databases. 
 */


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;


/*
 * Files and information is serialized to protect/lock it from being interacted with by outside users or anything else 
 * 
 * utilizes Password-Based Encryption (PBE) 
 * For additional information and implementation, refer to Jasypt's official documentation: http://www.jasypt.org/general-usage.html
 * 
 * Many exceptions are bubbled back up to the caller as Jasypt has vague exceptions. A few simple exceptions may be handled here as well. 
 * 
 *  
 */

public class Serializer {
	//Generates the serialized string the account uses for Password Based Encryption (PBE). 
	//This is then serialized by both user password and the user security questions for account retrieval. 
	public static String generateSerializerString() throws Exception {
		int n = 30;
		byte[] array = new byte[256]; // length is bounded by 7
	    new Random().nextBytes(array);
	    String randomString 
	    = new String(array, Charset.forName("UTF-8")); 
	    
	    // Create a StringBuffer to store the result 
	    StringBuffer r = new StringBuffer(); 
	   
	    // Append first 20 alphanumeric characters 
	    // from the generated random String into the result 
	    for (int k = 0; k < randomString.length(); k++) { 
	   
	     char ch = randomString.charAt(k); 
	   
	     if (((ch >= 'a' && ch <= 'z') 
	      || (ch >= 'A' && ch <= 'Z') 
	      || (ch >= '0' && ch <= '9')) 
	      && (n > 0)) { 
	   
	      r.append(ch); 
	      n--; 
	     } 
	    } 
	   
	    // return the resultant string 
	    return r.toString();
	}
	

	
	
	
	//TODO!
	/* ACCOUNT_INFO HANDLING */
	public static Employee encryptEmployee(Employee emp) throws Exception{
		String password = emp.getSecCodePass();
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(password);                         // we HAVE TO set a password
		
		//encrypt any info needed to be encrypted.
		emp.setPassword(encryptor.encrypt(emp.getPassword()));
		emp.setSecCodePass(encryptString(emp.getSecCodePass(), emp.getPassword()));
		
		return emp;
	}
	public static Employee decryptEmployee(Employee account, String password) throws Exception {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(password);                         // we HAVE TO set a password
		
		//decrypt any encrypted info
		account.setPassword(encryptor.decrypt(account.getPassword()));
		account.setSecCodePass(password);
		
		return account;
		
	}
	
	public static Customer encryptCustomer(Customer account) throws Exception{
		return new Customer();
	}
	public static Customer decryptEmployee(Customer account, String password) throws Exception {
		return new Customer();
	}

	
	
	/* FILE HANDLING */
	public static void encryptFile(String loc, String password, Boolean deleteOld, String homeDir) {
		encryptFile(Paths.get(loc), password, deleteOld, homeDir);
	}
	public static void encryptFile(Path p, String password, Boolean deleteOld, String homeDir) {
		//create encryption object. 
		StandardPBEByteEncryptor encryptor = new StandardPBEByteEncryptor();
		encryptor.setPassword(password);
		
		//get file name and extension.
		int dotIndex = p.toFile().getName().lastIndexOf('.');
		String name = (dotIndex == -1) ? "" : p.toFile().getName().substring(0, dotIndex);
		String ext = (dotIndex == -1) ? "" : p.toFile().getName().substring(dotIndex + 1);
		if (name.isEmpty() || ext.isEmpty()) {
			FileHelper.errorMessage("Error", "Unable to retrieve file information " + p.getFileName() + ". ");
			return;
		}
		
		//read the file
		byte[] data = null;
		try {
			data = Files.readAllBytes(p);
		} catch (IOException e) {
			FileHelper.errorMessage("Error", "Unable to open the selected file " + p.getFileName() + ". " + e);
			e.printStackTrace();
			return;
		}
		
		//create new file for the serialized information
		String outputFile = homeDir + "/" + name + "_serialized." + ext;
		try {
			Files.write(Paths.get(outputFile), encryptor.encrypt(data));
		} catch (Exception e) {
			FileHelper.errorMessage("Error", "Unable to encrypt file. " + e);
			e.printStackTrace();
		} 
		
		//Do we delete the old file?
		if(deleteOld) {
			try {Files.delete(p);}
			catch (IOException e) {
				FileHelper.errorMessage("Error", "Could not delete file \"" + p.getFileName() + "\"");
			}
		}
		
	}
	
	public static void decryptFile(String loc, String password, Boolean deleteOld, String homeDir) {
		decryptFile(Paths.get(loc), password, deleteOld, homeDir);
	}
	public static void decryptFile(Path p, String password, Boolean deleteOld, String homeDir) {
		//create encryption object. 
		StandardPBEByteEncryptor encryptor = new StandardPBEByteEncryptor();
		encryptor.setPassword(password);
		
		//get file name and extension.
		int dotIndex = p.toFile().getName().lastIndexOf('.');
		String name = (dotIndex == -1) ? "" : p.toFile().getName().substring(0, dotIndex).replace("_serialized", "");
		String ext = (dotIndex == -1) ? "" : p.toFile().getName().substring(dotIndex + 1);
		if (name.isEmpty() || ext.isEmpty()) {
			FileHelper.errorMessage("Error", "Unable to retrieve file information " + p.getFileName() + ". ");
			return;
		}
		
		//read the file
		byte[] data = null;
		try {
			data = Files.readAllBytes(p);
		} catch (IOException e) {
			FileHelper.errorMessage("Error", "Unable to open the selected file " + p.getFileName() + ". " + e);
			e.printStackTrace();
		}
		
		//create new file for the serialized information
		String outputFile = homeDir + "/" + name + "." + ext;
		try {
			Files.write(Paths.get(outputFile), encryptor.decrypt(data));
		} catch (Exception e) {
			FileHelper.errorMessage("Error", "Unable to decrypt file. " + e);
			e.printStackTrace();
		} 
		
		//Do we delete the old file?
		if(deleteOld) {
			try {Files.delete(p);}
			catch (IOException e) {
				FileHelper.errorMessage("Error", "Could not delete file \"" + p.getFileName() + "\"");
			}
		}		
	}

	
	/* STRING HANDLING */
	public static String encryptString(String toEncrypt, String password) {
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(password);                         // we HAVE TO set a password
		
		return encryptor.encrypt(toEncrypt);
	}
	public static String decryptString(String toDecrypt, String password) {
		if (toDecrypt == null || password == null)
			return null;
		StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
		encryptor.setPassword(password);                         // we HAVE TO set a password
		
		return encryptor.decrypt(toDecrypt);
	}

}