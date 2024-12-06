package UserInterface;

import java.util.ArrayList;
import java.util.List;

import Prescriptions.DrugInteraction;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class PrescriptionController {

    @FXML private VBox prescriptionContainer;
    @FXML private TextField medicationNameField;
    @FXML private TextField interactionNameField;
    @FXML private TextArea sideEffectsField;
    @FXML private ListView<String> interactionListView;

    private List<DrugInteraction> drugInteractions = new ArrayList<>();

    @FXML
    private void initialize() {
        //Initialize ListView with existing interactions
        updateInteractionListView();
    }

    
    @FXML
    private void addDrugInteraction() {
        try {
        	//Get the interaction name from the input field and trim any extra spaces
            String interactionWith = interactionNameField.getText().trim();
            
            // Split the side effects input by commas into an array
            String[] sideEffectsArray = sideEffectsField.getText().split(",");
            
            // Validate that both the interaction name and side effects are provided
            if (interactionWith.isEmpty() || sideEffectsArray.length == 0) {
            	// Show an error alert if the inputs are invalid
                showAlert("Invalid Input", "Interaction name and side effects are required.", Alert.AlertType.ERROR);
                return;
            }

            //Create a new DrugInteraction object
            DrugInteraction newInteraction = new DrugInteraction(interactionWith, sideEffectsArray);
            drugInteractions.add(newInteraction);

            //Clear input fields
            interactionNameField.clear();
            sideEffectsField.clear();

            //Update ListView
            updateInteractionListView();
            
            //Show a success alert indicating the interaction was added successfully
            showAlert("Success", "Drug interaction added successfully!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
        	//Log the exception and show an error alert with the exception message
            e.printStackTrace();
            showAlert("Error", "An error occurred while adding the interaction: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void checkForInteractions() {
        try {
        	// Get the medication name from the input field and trim any extra spaces
            String medicationName = medicationNameField.getText().trim();
            // Validate that the medication name is provided
            if (medicationName.isEmpty()) {
                showAlert("Invalid Input", "Medication name is required to check interactions.", Alert.AlertType.ERROR);
                return;
            }

            // Initialize a list to store matching interactions
            List<DrugInteraction> matchingInteractions = new ArrayList<>();
            // Iterate through the list of drug interactions
            for (DrugInteraction interaction : drugInteractions) {
            	// Check if the interaction name matches the input medication name (case-insensitive)
                if (interaction.getInteractionWith().equalsIgnoreCase(medicationName)) {
                    matchingInteractions.add(interaction);
                }
            }
            // Check if any matching interactions were found
            if (matchingInteractions.isEmpty()) {
            	// Show an informational alert if no interactions are found
                showAlert("No Interactions", "No interactions found for " + medicationName, Alert.AlertType.INFORMATION);
            } else {
            	// Build a message with the details of all matching interactions
                StringBuilder message = new StringBuilder("Interactions found for " + medicationName + ":\n");
                for (DrugInteraction interaction : matchingInteractions) {
                    message.append("- Interaction with: ").append(interaction.getInteractionWith()).append("\n");
                    message.append("  Side Effects: ").append(String.join(", ", interaction.getSideEffects())).append("\n");
                }
                // Show a warning alert with the interaction details
                showAlert("Interactions Found", message.toString(), Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
        	// Log the exception and show an error alert with the exception message
            e.printStackTrace();
            showAlert("Error", "An error occurred while checking interactions: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

 // Updates the ListView to display the current list of drug interactions
    private void updateInteractionListView() {
        // Create a new list to store summaries of interactions
        List<String> interactionSummaries = new ArrayList<>();
        
        // Iterate through each DrugInteraction in the list
        for (DrugInteraction interaction : drugInteractions) {
            // Format the interaction details as "InteractionWith - Side Effects: [side effects]"
            interactionSummaries.add(interaction.getInteractionWith() + " - Side Effects: " + String.join(", ", interaction.getSideEffects()));
        }

        // Set the formatted summaries to the ListView
        interactionListView.getItems().setAll(interactionSummaries);
    }

 // Displays an alert dialog with the specified title, message, and alert type
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        // Create a new Alert dialog of the specified type
        Alert alert = new Alert(alertType);
        
        // Set the title of the dialog
        alert.setTitle(title);
        
        // Set the header text to null (no header)
        alert.setHeaderText(null);
        
        // Set the content text to the provided message
        alert.setContentText(message);
        
        // Show the alert and wait for user interaction
        alert.showAndWait();
    }
}