package InventoryControl;

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
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import BackEnd.FileHelper;
import Prescriptions.Medication;



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


//TODO: ensure role requirement
public class PharmacyInventory {
	/* 
	 * ADD NEW MEDICATION 
	 */
	public static void addMedication(Medication med) throws Exception{

		
		//Send off to update storage
		writeMedication(med);
	}

	
	/* 
	 * RETRIEVE MEDICATION 
	 */
	//retrieve medication by medication item
	public static Medication retrieveMedication(Medication med) throws Exception{
		return retrieveMedication(med.getName());
	}
	
	//retrieve medication by name
	public static Medication retrieveMedication(String name) throws Exception{
		
		//get the Medications. 
		ArrayList<Medication> medList = readPharmacyInventory(FileHelper.findPharmacyInventoryFile());
		
		//See if the medication list contains the medicaiton. 
		for (Medication med : medList) {
			//medication found
			if (med.getName().equals(name)) {
				return med;
			}
		}
		return null;
	}
	
	/* 
	 * MODIFY EXISTING MEDICATION
	 */
	//updates a medication
	public static void updateMedication(Medication med) throws Exception {
		//get the Medications. 
		ArrayList<Medication> medList = readPharmacyInventory(FileHelper.findPharmacyInventoryFile());
		
		//find the medication
		for (Medication m : medList) {
			if (m.getName().equals(med.getName())) {
				medList.set(medList.indexOf(m), med);
				break;
			}
		}
		//update
		writeMedication(medList);
	}
	
	/* 
	 * FILE READING AND WRITING 
	 */
	//writes a medication to pharmacy storage. 
	private static void writeMedication(Medication med) throws Exception {
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
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		//Get the current list of medications and add the new one to it.
		ArrayList<Medication> medications = (ArrayList<Medication>) readPharmacyInventory(p);
		medications.add(med);
		
		//rewrite the file with the new medication added.
		try {
			mapper.writeValue(Files.newOutputStream(p), medications);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error writing to the file. " + e);
		}		
	}
	//writes a medication list to pharmacy storage
	private static void writeMedication(ArrayList<Medication> medications) throws Exception {
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
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		

		//rewrite the file with the new medication added.
		try {
			mapper.writeValue(Files.newOutputStream(p), medications);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Error writing to the file. " + e);
		}		
	}
	
	//retrieves all the medication's stored in storage. 
	public static ArrayList<Medication> readPharmacyInventory(Path p) throws Exception{
		//Check that the file exists. 
		if(!Files.exists(p))
			throw new Exception("Cannot load settings because the settings file path could not be loaded.");
		
		//If the file is empty, return an empty list. 
		if (p.toFile().length() == 0)
			return new ArrayList<Medication>();
		
		//Read the file into an array list.
		try {
			ObjectMapper mapper = new XmlMapper(); 
			mapper.registerModule(new JavaTimeModule());
			mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			TypeReference<ArrayList<Medication>> typeRef = new TypeReference<ArrayList<Medication>>() {};
			ArrayList<Medication> medications = mapper.readValue(Files.newInputStream(p), typeRef);
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