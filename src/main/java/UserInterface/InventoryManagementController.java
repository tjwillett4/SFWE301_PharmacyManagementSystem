package UserInterface;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import InventoryControl.BatchMedication;

import java.time.LocalDate;

public class InventoryManagementController {

    @FXML
    private TableView<BatchMedication> inventoryTable;
    @FXML
    private TableColumn<BatchMedication, String> medNameColumn;
    @FXML
    private TableColumn<BatchMedication, Integer> quantityColumn;
    @FXML
    private TableColumn<BatchMedication, LocalDate> expirationDateColumn;

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;

    private ObservableList<BatchMedication> medicationList;

    @FXML
    private void initialize() {
        medicationList = FXCollections.observableArrayList();
        loadInventory();

        // Bind table columns to BatchMedication properties
        medNameColumn.setCellValueFactory(cellData -> cellData.getValue().medicationNameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        expirationDateColumn.setCellValueFactory(cellData -> cellData.getValue().expirationDateProperty());

        inventoryTable.setItems(medicationList);
    }

    private void loadInventory() {
        // Mock inventory data (replace this with actual data retrieval)
        medicationList.add(new BatchMedication("Paracetamol", 50, LocalDate.now().plusMonths(6)));
        medicationList.add(new BatchMedication("Ibuprofen", 30, LocalDate.now().plusMonths(4)));
        medicationList.add(new BatchMedication("Amoxicillin", 20, LocalDate.now().plusMonths(2)));
    }

    @FXML
    private void handleSearch(ActionEvent event) {
        String keyword = searchField.getText().toLowerCase();

        // Filter inventory based on the search keyword
        ObservableList<BatchMedication> filteredList = FXCollections.observableArrayList(
                medicationList.filtered(medication -> medication.getMedicationName().toLowerCase().contains(keyword))
        );

        inventoryTable.setItems(filteredList);
    }

    @FXML
    private void handleAddMedication(ActionEvent event) {
        // Logic to open a dialog/form for adding a new medication
        System.out.println("Add Medication Button Clicked");
    }

    @FXML
    private void handleEditMedication(ActionEvent event) {
        BatchMedication selectedMedication = inventoryTable.getSelectionModel().getSelectedItem();
        if (selectedMedication != null) {
            // Logic to open a dialog/form for editing the selected medication
            System.out.println("Edit Medication: " + selectedMedication.getMedicationName());
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a medication to edit.");
        }
    }

    @FXML
    private void handleRemoveMedication(ActionEvent event) {
        BatchMedication selectedMedication = inventoryTable.getSelectionModel().getSelectedItem();
        if (selectedMedication != null) {
            medicationList.remove(selectedMedication);
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a medication to remove.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}