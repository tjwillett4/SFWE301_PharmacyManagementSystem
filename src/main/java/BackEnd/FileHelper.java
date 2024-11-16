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

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class FileHelper {
	public final static String DATA_DIR = System.getenv("APPDATA") + "//pharmacy";
	public final static String ACCOUNT_STORAGE_FILE = "/CustomerStorage.xml";
	public final static String EMPLOYEE_STORAGE_FILE = "/EmployeeStorage.xml";
	
	//return of property/database files.
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
}
