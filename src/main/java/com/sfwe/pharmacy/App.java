package com.sfwe.pharmacy;

import BackEnd.Employee;
import Prescriptions.Medication;
import InventoryControl.BatchMedication;
import InventoryControl.PharmacyInventory;
import Prescriptions.DrugInteraction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import BackEnd.AccountHandling;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        
        //INITIAL MEDICATION TESTING:
        
        Medication med = new Medication("Omeprazole");
        //Set alternative names
        ArrayList<String> brandNames = new ArrayList<>(
        		Arrays.asList("Prilosec OTC", "Prilosec", "Zegerid", "OmePPI", "Zegerid OTC")
		);
        med.setBrandNames(brandNames);
        
        //Create a shipment batch. 
        BatchMedication batchOne = new BatchMedication(
        		//Arrival date
        		LocalDate.now(), 
        		//Expiration date, of december 25th (I believe. check for 0 indexing) 
        		LocalDate.of(2024, 12, 25), 
        		//Stock count
        		300);
        med.setSingleBatch(batchOne);
        
        //set drug interactions
        med.setInteractions(new ArrayList<>(
        			Arrays.asList(
        				new DrugInteraction("Warfarin", Arrays.asList("Blood thinning", "Strengthened effect")),
        				new DrugInteraction("Citalopram", Arrays.asList("heart rhythm issues"))
					))
        );
        
        
        //Write medication to storage
        try {
			PharmacyInventory.addMedication(med);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unable to add medication because: " + e);
		}
        
        

        //get the medication from storage:
        try {
			Medication testMedication = PharmacyInventory.retrieveMedication("Omeprazole");
			
	        //change the stock count of the medicaiton. 
			for (String s : Arrays.asList("joe", "Han", "tobby")) {
				if (!testMedication.getBatches().get(0).updateStock(
						testMedication.getBatches().get(0).getStock()-14, s))
					System.out.println("Error! There is not enough medication left to fill this prescription.");
				
			}
			PharmacyInventory.updateMedication(testMedication);

			
			System.out.println("medication info is: ");
			testMedication.printMedicationInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("Unable to get medication because: " + e);
		}
        
        
        
        // INITIAL ACCOUNT comment out after initial attempt. 
        //String firstName, String user, String p, String secPass
        /*Employee test = new Employee("Bob", "bob", "pass", "pass");
        
        try {
        	//add account the database. 
			AccountHandling.addEmployeeAccount(test);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to add employee because: " + e);
			e.printStackTrace();
		}*/
        /*
        //CHECK TO SEE IF IT EXISTS:
		System.out.println("Does Bob now exist in the system?");
		try {
			if (AccountHandling.employeeExists("bob")) {
				System.out.println("Bob does exist in the new system");
				
			}
			else
				System.out.println("Bob does NOT exist in the system.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to check employee database because: " + e);
			e.printStackTrace();
		}
        
        
        //CHECK IF INITIAL ACCOUNT WAS CREATED BY LOGGING IN AND PRINTING INFO. 
		
		try {
			Employee emp = AccountHandling.logIn("bob", "pass");
			
			System.out.println("Just retrieved Bob's account from the database!");
			System.out.println(emp.getNameFirst() + ", " + emp.getUsername() + ", " + emp.getPassword());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to check log in because: " + e);
			e.printStackTrace();
		}
        */
    }
}
