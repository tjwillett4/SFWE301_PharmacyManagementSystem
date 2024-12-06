package UserInterface;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import InventoryControl.BatchMedication;
import InventoryControl.PharmacyInventory;
import Prescriptions.DrugInteraction;
import Prescriptions.Medication;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;

/*
 * File used for Medication inventory GUI. Parsing user input into the inventory. 
 */
public class MedicationInventory {
	//Variables
	 private Boolean existingMedication = false;
	 Medication currentMedication = null;
	
	//FXML GUI components
	 @FXML private VBox newMedicationDrugInteraction;
	 @FXML private VBox newMedicationInfo;
	 
	 @FXML private TextField medicationName;
	 @FXML private TextField medicationCategory;
	 @FXML private TextField medicationStock;
	 @FXML private TextField medicationBrandNames;
	 @FXML private TextField medicationSupplier;
	 @FXML private TextField medicationExpirationDate;
	 @FXML private TextField interactionDrugName;
	
	
	//Initialize the medication homescreen.  
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
	            		return;
	            	}
	            	currentMedication = PharmacyInventory.retrieveMedication(medName);
	         

	            	initializeMedicationUI(currentMedication != null);
	            	
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        });
	 }
	 
	 //Initialize the medication
	 private void initializeMedicationUI(Boolean existingMedication) {
		 if (existingMedication) {
			 newMedicationDrugInteraction.setVisible(false);
			 newMedicationInfo.setVisible(false);
		 }
	 }
	 
	 //Upon submission of the new medication form. 
	 @FXML
	 private void handleMedicationSubmission(ActionEvent event) {
		 //Get text from all the fields. 
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
			 //create new medication and set it's batch and brands. 
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
	 
	//GUI function to prompt the user of an issue. 
	private void showError(String title, String message) {
	    Alert alert = new Alert(AlertType.ERROR);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	} 
}
