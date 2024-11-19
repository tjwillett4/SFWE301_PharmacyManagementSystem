package InventoryControl;

import BackEnd.FileHelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import Prescriptions.p;

/*
 * PHARMACY INVENTORY:
 * Note: THis is of course not the actual inventory database, which would be a text file. 
 * The system shall list the stores of the pharmacy stockroom and modification history. 
 * The inventory shall be securely managed by restricting access to certain account types (Pharmacist, Management, etc.) 
 * to ensure the records are not maliciously modified. 
 * 
 * 
 * 
 * 
 */

public class PharmacyInventory {
	//Add new Medication
	
	//Retrieve existing medication
	
	//Modify Existing Medication
	
	//writes an object to pharmacy storage. 
	private static <T> void writeMedication(T med, Class<T> classType) throws Exception {
		//Get file
		Path p;
		try { 
			p = FileHelper.findPharmacyInventoryFile();
		} catch (Exception e) {
			//This could be due to different platforms such as mac/linux. Addapt for these.
			e.printStackTrace();
			throw new Exception("Error finding or creating file! " + e);
		}
		ObjectMapper mapper = new XmlMapper();
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		//Get the current list of medications and add the new one to it.
		ArrayList<T> medications = (ArrayList<T>) readPharmacyInventory(p, classType);
		medications.add( (T) med);
		
		//rewrite the file with the new medication added.
		try {
			mapper.writeValue(Files.newOutputStream(p), medications);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error writing to the file. " + e);
		}		
	}
	
	//retrieves all the medication's stored in storage. 
	public static <T> ArrayList<T> readPharmacyInventory(Path p, Class<T> classType) throws Exception{
		//Check that the file exists. 
		if(!Files.exists(p))
			throw new Exception("Cannot load settings because the settings file path could not be loaded.");
		
		//If the file is empty, return an empty list. 
		if (p.toFile().length() == 0)
			return new ArrayList<T>();
		
		//Read the file into an array list.
		try {
			ObjectMapper mapper = new XmlMapper(); 
			TypeReference<ArrayList<T>> typeRef = new TypeReference<ArrayList<T>>() {};
			ArrayList<T> medications = mapper.readValue(Files.newInputStream(p), typeRef);
			return medications;
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
