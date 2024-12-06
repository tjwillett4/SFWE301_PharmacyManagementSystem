package UserInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import InventoryControl.BatchMedication;

import java.time.LocalDate;

public class InventoryManagementController {

    @FXML private TableView<BatchMedication> inventoryTable;
    @FXML private TableColumn<BatchMedication, String> medNameColumn;
    @FXML private TableColumn<BatchMedication, Integer> quantityColumn;
    @FXML private TableColumn<BatchMedication, LocalDate> expirationDateColumn;

    @FXML private TextField searchField;
    @FXML private Button searchButton;
    
    private ObservableList<BatchMedication> medicationList;

    /**
     * Initializes the inventory management view.
     * - Loads the initial inventory data.
     * - Binds the table columns to the properties of the `BatchMedication` class.
     * - Sets the inventory data to be displayed in the table.
     */
    @FXML
    private void initialize() {
        // Initialize the observable list for medication inventory
        medicationList = FXCollections.observableArrayList();

        // Load mock inventory data (replace with actual data retrieval)
        loadInventory();

        // Bind table columns to corresponding properties in the BatchMedication class
        medNameColumn.setCellValueFactory(cellData -> cellData.getValue().medicationNameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        expirationDateColumn.setCellValueFactory(cellData -> cellData.getValue().expirationDateProperty());

        // Set the inventory data to the table
        inventoryTable.setItems(medicationList);
    }

    /**
     * Loads the mock inventory data into the `medicationList`.
     * - Replace this method with actual data retrieval logic in a production environment.
     */
    private void loadInventory() {
        medicationList.add(new BatchMedication("Paracetamol", 50, LocalDate.now().plusMonths(6)));
        medicationList.add(new BatchMedication("Ibuprofen", 30, LocalDate.now().plusMonths(4)));
        medicationList.add(new BatchMedication("Amoxicillin", 20, LocalDate.now().plusMonths(2)));
    }

    /**
     * Handles the search functionality for the inventory table.
     * - Filters the inventory based on the keyword entered in the search field.
     * - Updates the table to display only the filtered results.
     *
     * @param event The ActionEvent triggered by the search button.
     */
    @FXML
    private void handleSearch(ActionEvent event) {
        // Get the keyword entered in the search field
        String keyword = searchField.getText().toLowerCase();

        // Filter the inventory based on the search keyword
        ObservableList<BatchMedication> filteredList = FXCollections.observableArrayList(
            medicationList.filtered(medication -> medication.getMedicationName().toLowerCase().contains(keyword))
        );

        // Update the table to display the filtered list
        inventoryTable.setItems(filteredList);
    }

    /**
     * Handles the action for adding a new medication to the inventory.
     * - Opens a dialog or form for entering the details of the new medication.
     * - Currently logs the action to the console (replace with actual implementation).
     *
     * @param event The ActionEvent triggered by clicking the "Add Medication" button.
     */
    @FXML
    private void handleAddMedication(ActionEvent event) {
        // Placeholder logic for adding medication
        System.out.println("Add Medication Button Clicked");
    }

    /**
     * Handles the action for editing an existing medication in the inventory.
     * - Opens a dialog or form for modifying the details of the selected medication.
     * - Displays a warning alert if no medication is selected.
     *
     * @param event The ActionEvent triggered by clicking the "Edit Medication" button.
     */
    @FXML
    private void handleEditMedication(ActionEvent event) {
        // Get the selected medication from the inventory table
        BatchMedication selectedMedication = inventoryTable.getSelectionModel().getSelectedItem();

        if (selectedMedication != null) {
            // Placeholder logic for editing the selected medication
            System.out.println("Edit Medication: " + selectedMedication.getMedicationName());
        } else {
            // Show a warning if no medication is selected
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a medication to edit.");
        }
    }

    /**
     * Handles the removal of a selected medication from the inventory.
     * - Checks if a medication is selected in the inventory table.
     * - If a medication is selected, removes it from the medication list.
     * - Displays a warning if no medication is selected.
     *
     * @param event The ActionEvent triggered when the "Remove Medication" button is clicked.
     */
    @FXML
    private void handleRemoveMedication(ActionEvent event) {
        // Get the selected medication from the inventory table
        BatchMedication selectedMedication = inventoryTable.getSelectionModel().getSelectedItem();

        if (selectedMedication != null) {
            // Remove the selected medication from the list
            medicationList.remove(selectedMedication);
        } else {
            // Show a warning alert if no medication is selected
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a medication to remove.");
        }
    }

    /**
     * Displays an alert dialog with the specified type, title, and message.
     *
     * @param type    The type of the alert (e.g., WARNING, INFORMATION, ERROR).
     * @param title   The title of the alert dialog.
     * @param message The message to be displayed in the alert dialog.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}