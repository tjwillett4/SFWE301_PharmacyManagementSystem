package com.sfwe.pharmacy;

import BackEnd.AccountHandling;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The entry point of the Pharmacy Management System application.
 * - Initializes default accounts at application startup.
 * - Loads and displays the login screen as the first interface.
 */

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Create default accounts at the start of the application
            AccountHandling.createDefaultAccounts();
            System.out.println("Default accounts created successfully.");
        } catch (Exception e) {
            // Handle exceptions during the creation of default accounts
            e.printStackTrace();
            System.err.println("Error creating default accounts: " + e.getMessage());
        }

        try {
            // Load and display the login screen
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Login.fxml"));
            Scene scene = new Scene(loader.load());
            primaryStage.setTitle("Pharmacy Management System");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            // Handle exceptions during the loading of the login screen
            e.printStackTrace();
            System.err.println("Error loading the Login screen: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
    }
}


/**
 * Hello world!
 */
/*
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        
        //INITIAL MEDICATION TESTING:
        Medication med = new Medication(
        	    "Omeprazole",     // Name
        	    "Proton Pump Inhibitor", // Category
        	    "Default Supplier",      // Supplier
        	    0                        // Stock (initialize to zero if none)
        	);
        //Set alternative names
        ArrayList<String> brandNames = new ArrayList<>(
        		Arrays.asList("Prilosec OTC", "Prilosec", "Zegerid", "OmePPI", "Zegerid OTC")
		);
        med.setBrandNames(brandNames);
        
        //Create a shipment batch. 
        BatchMedication batchOne = new BatchMedication(
        	    "Omeprazole", // Medication name
        	    300, // Stock count
        	    LocalDate.of(2024, 12, 25) // Expiration date
        	);
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
*/     
        
        
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
        
    }
}
*/