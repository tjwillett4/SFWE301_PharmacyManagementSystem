package UserInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import BackEnd.AccountHandling;
import BackEnd.Employee;
import InventoryControl.BatchMedication;
import InventoryControl.PharmacyInventory;
import Prescriptions.DrugInteraction;
import Prescriptions.Medication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;


public class MedicationInventory {
	 @FXML
	 private void initialize() {
		 //see if this is a new medication:
		 TextInputDialog dialog = new TextInputDialog();
	        dialog.setTitle("Medication");
	        dialog.setHeaderText("Enter the name of the medication");
	        dialog.setContentText("Medication");

	        Optional<String> result = dialog.showAndWait();
	        result.ifPresent(medName -> {
	            try {
	            	if (!result.isPresent() || result.isEmpty() || medName.length() == 0) {
	            		//if canceled return
	            		NavigationUtil.loadMainDashboard();
	            		return;
	            	}
	            	currentMedication = PharmacyInventory.retrieveMedication(medName);
	         

	            	initializeMedicationUI(currentMedication != null);
	            	
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });
		 
		 

	 }
	 
	 
	 private void initializeMedicationUI(Boolean existingMedication) {
		 if (existingMedication) {
			 newMedicationDrugInteraction.setVisible(false);
			 newMedicationInfo.setVisible(false);
		 }
	 }
	 
	 @FXML private VBox newMedicationDrugInteraction;
	 @FXML private VBox newMedicationInfo;
	 
	 @FXML private TextField medicationName;
	 @FXML private TextField medicationCategory;
	 @FXML private TextField medicationStock;
	 @FXML private TextField medicationBrandNames;
	 @FXML private TextField medicationSupplier;
	 @FXML private TextField medicationExpirationDate;
	 @FXML private TextField interactionDrugName;
	 
	 private Boolean existingMedication = false;
	 Medication currentMedication = null;
	 
	 @FXML
	 private void handleMedicationSubmission(ActionEvent event) {
		 System.out.println("Handle medication submission!");
		 String name = medicationName.getText();
		 String cat = medicationCategory.getText();
		 int stock = Integer.parseInt(medicationStock.getText());
		 ArrayList<String> brands = new ArrayList<>(Arrays.asList(medicationBrandNames.getText().split("\\s*,\\s*")));
		 String supplier = medicationSupplier.getText();
		 String[] expirationDateAsStringList = medicationExpirationDate.getText().split("/");
		 ArrayList<String> interactionDrugName = new ArrayList<>(Arrays.asList(medicationBrandNames.getText().split("\\s*,\\s*")));
		 
		 Medication med;
		 
		 
		//create a shipment batch
		 int month = Integer.parseInt(expirationDateAsStringList[0]);
		 int day = Integer.parseInt(expirationDateAsStringList[1]);
		 int year = Integer.parseInt(expirationDateAsStringList[2]);
		 BatchMedication b = new BatchMedication(
				 name,	// Name. 
				 stock, // Stock count
				 LocalDate.of(year, month, day) //TODO: Fix expiration date! 
				 );

		 
		 
		 //if new med. 
		 if (currentMedication == null) {
			 med = new Medication(
					 name,
					 cat,
					 supplier,
					 stock
					 );
			 med.setBrandNames(brands);
			 med.addSingleBatch(b);
			 
			 //Drug interactions
			 ArrayList<DrugInteraction> DI = new ArrayList<DrugInteraction>();
			 for (String i : interactionDrugName) {
				 DI.add( new DrugInteraction(i, Arrays.asList("")));
			 }
			 med.setInteractions(DI);
			 
	        //Write medication to storage
	        try {
				PharmacyInventory.addMedication(med);
			} catch (Exception e) {
				e.printStackTrace();
				showError("Error","There has been an issue accessing Pharmacy Inventory: " + e);
				System.out.println("Unable to add medication because: " + e);
			}
		 }
		 //If medication already exists.
		 else {
			 try {
				med = currentMedication;
				//was it found?
				if (med == null) {
					showError("Error", "The medication " + name + " cannot be found in the inventory. even though it should.");
				}
				med.addSingleBatch(b);
				PharmacyInventory.updateMedication(med);	
			} catch (Exception e) {
				showError("Error","There has been an issue accessing Pharmacy Inventory: " + e);
				e.printStackTrace();
			}
		 }
		 
		 NavigationUtil.loadMainDashboard();  
	 }
	 
	 
	    private void showError(String title, String message) {
	        Alert alert = new Alert(AlertType.ERROR);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.showAndWait();
	    }
	 
	 
	 /*
	  *         //INITIAL MEDICATION TESTING:
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
	 
	 
}
