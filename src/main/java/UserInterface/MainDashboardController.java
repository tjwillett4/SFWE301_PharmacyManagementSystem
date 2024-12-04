package UserInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import Prescriptions.Medication;
import BackEnd.Role;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

import BackEnd.Employee;
import BackEnd.FileHelper;
import UserInterface.Session;
import InventoryControl.PharmacyInventory;

public class MainDashboardController {

	@FXML
	private void handleLogout(ActionEvent event) {
	    NavigationUtil.loadLoginScreen(event);
	}

	 @FXML
	    private void handleExit(ActionEvent event) {
	        // Show confirmation dialog
	        Alert exitConfirmation = new Alert(AlertType.CONFIRMATION);
	        exitConfirmation.setTitle("Exit Confirmation");
	        exitConfirmation.setHeaderText(null);
	        exitConfirmation.setContentText("Are you sure you want to exit?");
	        exitConfirmation.showAndWait().ifPresent(response -> {
	            if (response == javafx.scene.control.ButtonType.OK) {
	                System.exit(0);
	            }
	        });
	    }

	 @FXML
	    private void openInventoryManagement(ActionEvent event) {
	        try {
	            // Retrieve the inventory file path
	            Path inventoryFile = FileHelper.findPharmacyInventoryFile();

	            // Read the inventory from the file
	            ArrayList<Medication> inventoryList = PharmacyInventory.readPharmacyInventory(inventoryFile);

	            // Convert to ObservableList for TableView
	            ObservableList<Medication> observableInventory = FXCollections.observableArrayList(inventoryList);

	            // Create a TableView to display the inventory
	            TableView<Medication> tableView = new TableView<>();

	            // Define table columns
	            TableColumn<Medication, String> nameColumn = new TableColumn<>("Name");
	            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

	            TableColumn<Medication, String> categoryColumn = new TableColumn<>("Category");
	            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));

	            TableColumn<Medication, Integer> stockColumn = new TableColumn<>("Stock");
	            stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

	            TableColumn<Medication, String> supplierColumn = new TableColumn<>("Supplier");
	            supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));

	            // Add columns to TableView
	            tableView.getColumns().addAll(nameColumn, categoryColumn, stockColumn, supplierColumn);

	            // Set data to TableView
	            tableView.setItems(observableInventory);

	            // Create a new Stage (window) to display the inventory
	            Stage inventoryStage = new Stage();
	            inventoryStage.setTitle("Inventory Management");
	            inventoryStage.setScene(new Scene(tableView, 600, 400));
	            inventoryStage.show();
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.err.println("Error: Unable to load inventory management screen.");
	        }
	    }

	 @FXML
	 private void handleTrackPrescriptions(ActionEvent event) {
	     try {
	         // Open Prescription Tracking functionality
	         NavigationUtil.loadPrescriptionTracking(event);
	     } catch (Exception e) {
	         e.printStackTrace();
	         showError("Error", "Unable to open Prescription Tracking.");
	     }
	 }

	 @FXML
	 private void openManagementInterface(ActionEvent event) {
	     try {
	         // Open Management Interface functionality
	         NavigationUtil.loadManagementInterface(event);
	     } catch (Exception e) {
	         e.printStackTrace();
	         showError("Error", "Unable to open Management Interface.");
	     }
	 }

    @FXML
    private void handleProcessPrescription(ActionEvent event) {
        System.out.println("Processing Prescription");
    }

    @FXML
    private void handleRefillPrescription(ActionEvent event) {
        try {
            // Retrieve inventory from PharmacyInventory
            Path inventoryFile = FileHelper.findPharmacyInventoryFile();
            ArrayList<Medication> inventoryList = PharmacyInventory.readPharmacyInventory(inventoryFile);

            // Create a TableView for the user to select a medication to refill
            TableView<Medication> tableView = new TableView<>();
            TableColumn<Medication, String> nameColumn = new TableColumn<>("Medication Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<Medication, Integer> stockColumn = new TableColumn<>("Available Stock");
            stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

            tableView.getColumns().addAll(nameColumn, stockColumn);
            tableView.setItems(FXCollections.observableArrayList(inventoryList));

            // Add selection and refill functionality
            tableView.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2) { // Double-click to refill
                    Medication selectedMedication = tableView.getSelectionModel().getSelectedItem();
                    if (selectedMedication != null) {
                        try {
                            // Deduct stock for the refill
                            refillMedication(selectedMedication);
                            // Refresh the table to show updated stock
                            tableView.setItems(FXCollections.observableArrayList(PharmacyInventory.readPharmacyInventory(inventoryFile)));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            showError("Error", "Failed to refill the medication.");
                        }
                    }
                }
            });

            // Show the TableView in a new Stage
            Stage refillStage = new Stage();
            refillStage.setTitle("Refill Prescription");
            refillStage.setScene(new Scene(tableView, 400, 300));
            refillStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error", "Unable to load the refill prescription screen.");
        }
    }

    private void refillMedication(Medication medication) throws Exception {
        // Deduct stock (simulate a refill)
        if (medication.getStock() > 0) {
            medication.setStock(medication.getStock() - 1); // Deduct 1 unit
            PharmacyInventory.updateMedication(medication);
            showInfo("Success", "Refilled prescription for " + medication.getName());
        } else {
            showError("Error", "Insufficient stock to refill the prescription for " + medication.getName());
        }
    }
    
    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}