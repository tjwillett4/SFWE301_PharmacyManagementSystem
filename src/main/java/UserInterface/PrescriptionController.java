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

    @FXML
    private VBox prescriptionContainer;

    @FXML
    private TextField medicationNameField;

    @FXML
    private TextField interactionNameField;

    @FXML
    private TextArea sideEffectsField;

    @FXML
    private ListView<String> interactionListView;

    private List<DrugInteraction> drugInteractions = new ArrayList<>();

    @FXML
    private void initialize() {
        // Initialize ListView with existing interactions
        updateInteractionListView();
    }

    @FXML
    private void addDrugInteraction() {
        try {
            String interactionWith = interactionNameField.getText().trim();
            String[] sideEffectsArray = sideEffectsField.getText().split(",");

            if (interactionWith.isEmpty() || sideEffectsArray.length == 0) {
                showAlert("Invalid Input", "Interaction name and side effects are required.", Alert.AlertType.ERROR);
                return;
            }

            // Create a new DrugInteraction object
            DrugInteraction newInteraction = new DrugInteraction(interactionWith, sideEffectsArray);
            drugInteractions.add(newInteraction);

            // Clear input fields
            interactionNameField.clear();
            sideEffectsField.clear();

            // Update ListView
            updateInteractionListView();

            showAlert("Success", "Drug interaction added successfully!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while adding the interaction: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void checkForInteractions() {
        try {
            String medicationName = medicationNameField.getText().trim();
            if (medicationName.isEmpty()) {
                showAlert("Invalid Input", "Medication name is required to check interactions.", Alert.AlertType.ERROR);
                return;
            }

            // Example logic for checking interactions (customize as needed)
            List<DrugInteraction> matchingInteractions = new ArrayList<>();
            for (DrugInteraction interaction : drugInteractions) {
                if (interaction.getInteractionWith().equalsIgnoreCase(medicationName)) {
                    matchingInteractions.add(interaction);
                }
            }

            if (matchingInteractions.isEmpty()) {
                showAlert("No Interactions", "No interactions found for " + medicationName, Alert.AlertType.INFORMATION);
            } else {
                StringBuilder message = new StringBuilder("Interactions found for " + medicationName + ":\n");
                for (DrugInteraction interaction : matchingInteractions) {
                    message.append("- Interaction with: ").append(interaction.getInteractionWith()).append("\n");
                    message.append("  Side Effects: ").append(String.join(", ", interaction.getSideEffects())).append("\n");
                }
                showAlert("Interactions Found", message.toString(), Alert.AlertType.WARNING);
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error", "An error occurred while checking interactions: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void updateInteractionListView() {
        List<String> interactionSummaries = new ArrayList<>();
        for (DrugInteraction interaction : drugInteractions) {
            interactionSummaries.add(interaction.getInteractionWith() + " - Side Effects: " + String.join(", ", interaction.getSideEffects()));
        }
        interactionListView.getItems().setAll(interactionSummaries);
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}