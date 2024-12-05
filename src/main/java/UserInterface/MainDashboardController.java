package UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import InventoryControl.BatchMedication;
import Prescriptions.Medication;
import BackEnd.Role;
import BackEnd.AccountHandling;
import BackEnd.Employee;
import BackEnd.FileHelper;
import UserInterface.Session;
import InventoryControl.PharmacyInventory;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;

public class MainDashboardController {

    @FXML private TabPane managementTabPane;
    @FXML private Tab inventoryTab;
    @FXML private Tab reportsTab;

    @FXML
    private void initialize() {
        // Restrict access to management features based on role
        checkAuthorization();
    }

    private void checkAuthorization() {
        Role currentRole = Session.getCurrentUser().getAccountRole();
        boolean hasManagementAccess = currentRole == Role.pharmacyManager || currentRole == Role.pharmacist;

        // Disable management tabs for unauthorized users
        if (!hasManagementAccess) {
            inventoryTab.setDisable(true);
            reportsTab.setDisable(true);
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        Session.clear();
        NavigationUtil.loadLoginScreen(event);
    }

    @FXML
    private void handleExit(ActionEvent event) {
        Alert exitConfirmation = new Alert(AlertType.CONFIRMATION);
        exitConfirmation.setTitle("Exit Confirmation");
        exitConfirmation.setHeaderText(null);
        exitConfirmation.setContentText("Are you sure you want to exit?");
        exitConfirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.exit(0);
            }
        });
    }
    
    @FXML
    private void handleUnlockAccount(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Unlock Account");
        dialog.setHeaderText("Unlock a Locked Account");
        dialog.setContentText("Enter the username of the account to unlock:");

        dialog.showAndWait().ifPresent(username -> {
            try {
                boolean success = AccountHandling.unlockEmployeeAccount(username, Session.getCurrentUser().getUsername());
                if (success) {
                    showInfo("Success", "Account successfully unlocked for user: " + username);
                } else {
                    showError("Error", "Failed to unlock account. User not found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showError("Error", "Failed to unlock account: " + e.getMessage());
            }
        });
    }
    
    @FXML
    private void handleProcessSale(ActionEvent event) {
        Alert saleAlert = new Alert(AlertType.INFORMATION);
        saleAlert.setTitle("Process Sale");
        saleAlert.setHeaderText("Processing Sale");
        saleAlert.setContentText("Sale processed successfully!");
        saleAlert.showAndWait();
    }
    
    @FXML
    private void openInventoryManagement(ActionEvent event) {
        try {
            // Retrieve inventory
            Path inventoryFile = FileHelper.findPharmacyInventoryFile();
            ArrayList<Medication> inventoryList = PharmacyInventory.readPharmacyInventory(inventoryFile);
            ObservableList<Medication> observableInventory = FXCollections.observableArrayList(inventoryList);

            // Create TableView for Inventory Management
            TableView<Medication> tableView = new TableView<>();
            TableColumn<Medication, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<Medication, String> categoryColumn = new TableColumn<>("Category");
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            TableColumn<Medication, Integer> stockColumn = new TableColumn<>("Stock");
            stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            TableColumn<Medication, String> supplierColumn = new TableColumn<>("Supplier");
            supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));

            tableView.getColumns().addAll(nameColumn, categoryColumn, stockColumn, supplierColumn);
            tableView.setItems(observableInventory);

            // Show TableView in a new Stage
            Stage inventoryStage = new Stage();
            inventoryStage.setTitle("Inventory Management");
            inventoryStage.setScene(new javafx.scene.Scene(tableView, 600, 400));
            inventoryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error", "Unable to load inventory management.");
        }
    }
    
    @FXML
    private void addInventory(ActionEvent event) {
        try {
            // Open a dialog or form for adding a new inventory item
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Inventory");
            dialog.setHeaderText("Add a New Inventory Item");
            dialog.setContentText("Enter item details:");

            // Show the dialog and wait for input
            dialog.showAndWait().ifPresent(itemDetails -> {
                // Logic to add the item to the inventory
                System.out.println("Item added: " + itemDetails);
                showInfo("Success", "Inventory item added successfully.");
            });
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error", "Unable to add inventory item.");
        }
    }
    
    @FXML
    private void handleViewInventory(ActionEvent event) {
        try {
            Path inventoryFile = FileHelper.findPharmacyInventoryFile();
            ArrayList<Medication> inventoryList = PharmacyInventory.readPharmacyInventory(inventoryFile);
            ObservableList<Medication> observableInventory = FXCollections.observableArrayList(inventoryList);

            // Create TableView
            TableView<Medication> tableView = new TableView<>();
            TableColumn<Medication, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<Medication, String> categoryColumn = new TableColumn<>("Category");
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            TableColumn<Medication, Integer> stockColumn = new TableColumn<>("Stock");
            stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            TableColumn<Medication, String> supplierColumn = new TableColumn<>("Supplier");
            supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));

            tableView.getColumns().addAll(nameColumn, categoryColumn, stockColumn, supplierColumn);
            tableView.setItems(observableInventory);

            // Display in a new stage
            Stage inventoryStage = new Stage();
            inventoryStage.setTitle("Inventory");
            inventoryStage.setScene(new javafx.scene.Scene(tableView, 600, 400));
            inventoryStage.show();
        } catch (Exception e) {
            showErrorAlert("Error", "Unable to load inventory: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleAddEmployee(ActionEvent event) {
        try {
            // Prompt for employee details
            TextInputDialog usernameDialog = new TextInputDialog();
            usernameDialog.setTitle("Add Employee");
            usernameDialog.setHeaderText("Enter Employee Username:");
            usernameDialog.setContentText("Username:");
            Optional<String> usernameResult = usernameDialog.showAndWait();

            if (!usernameResult.isPresent()) return; // Exit if canceled
            String username = usernameResult.get();

            TextInputDialog passwordDialog = new TextInputDialog();
            passwordDialog.setTitle("Add Employee");
            passwordDialog.setHeaderText("Enter Employee Password:");
            passwordDialog.setContentText("Password:");
            Optional<String> passwordResult = passwordDialog.showAndWait();

            if (!passwordResult.isPresent()) return; // Exit if canceled
            String password = passwordResult.get();

            ChoiceDialog<Role> roleDialog = new ChoiceDialog<>(Role.pharmacyTech, Role.values());
            roleDialog.setTitle("Add Employee");
            roleDialog.setHeaderText("Select Employee Role:");
            roleDialog.setContentText("Role:");
            Optional<Role> roleResult = roleDialog.showAndWait();

            if (!roleResult.isPresent()) return; // Exit if canceled
            Role role = roleResult.get();

            // Create the new employee and add to the system
            Employee newEmployee = new Employee(username, password, role);
            AccountHandling.addEmployeeAccount(newEmployee);

            showInfo("Success", "Employee " + username + " added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error", "Failed to add employee: " + e.getMessage());
        }
    }
    
    @FXML
    private void openManagementInterface(ActionEvent event) {
        Role currentRole = Session.getCurrentUser().getAccountRole();
        if (currentRole != Role.pharmacyManager) {
            showError("Unauthorized Access", "Only managers can access this interface.");
            return;
        }

        try {
            NavigationUtil.loadManagementInterface(event);
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error", "Unable to open Management Interface.");
        }
    }
    
    @FXML
    private void handleUpdateAccount(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Password");
        dialog.setHeaderText("Enter your new password:");
        dialog.setContentText("New Password:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newPassword -> {
            try {
                Employee currentUser = Session.getCurrentUser();
                currentUser.setPassword(newPassword);
                AccountHandling.updateEmployeeAccount(currentUser.getUsername(), currentUser);
                showInfo("Success", "Password updated successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                showError("Error", "Failed to update password: " + e.getMessage());
            }
        });
    }
    
    @FXML
    private void handleUpdateContactInfo(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Contact Information");
        dialog.setHeaderText("Enter your updated contact information:");
        dialog.setContentText("Contact Info:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newContactInfo -> {
            try {
                Employee currentUser = Session.getCurrentUser();
                // Assuming there is a method to update contact information
                currentUser.setContactInfo(newContactInfo); // Add this setter in Employee class
                AccountHandling.updateEmployeeAccount(currentUser.getUsername(), currentUser);
                showInfo("Success", "Contact information updated successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                showError("Error", "Failed to update contact information: " + e.getMessage());
            }
        });
    }

    // Method to handle generating reports
    @FXML
    private void handleGenerateReports(ActionEvent event) {
        try {
            // This can be customized to generate different reports
            // For simplicity, we'll display a placeholder message
            showAlert("Reports", "Reports generated successfully!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Error generating reports: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    
    @FXML
    private void handleSearchMedications(ActionEvent event) {
        TextInputDialog searchDialog = new TextInputDialog();
        searchDialog.setTitle("Search Medications");
        searchDialog.setHeaderText("Search for a Medication");
        searchDialog.setContentText("Enter medication name:");

        searchDialog.showAndWait().ifPresent(searchQuery -> {
            try {
                Path inventoryFile = FileHelper.findPharmacyInventoryFile();
                ArrayList<Medication> inventoryList = PharmacyInventory.readPharmacyInventory(inventoryFile);
                Medication result = inventoryList.stream()
                        .filter(med -> med.getName().equalsIgnoreCase(searchQuery))
                        .findFirst()
                        .orElse(null);

                if (result != null) {
                    showInfoAlert("Medication Found", "Name: " + result.getName() + "\nCategory: " + result.getCategory() + "\nStock: " + result.getStock());
                } else {
                    showInfoAlert("Not Found", "No medication found with the name: " + searchQuery);
                }
            } catch (Exception e) {
                showErrorAlert("Error", "Unable to search medications: " + e.getMessage());
            }
        });
    }
    
    @FXML
    private void handleUpdateInventory(ActionEvent event) {
        try {
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Update Inventory");
            dialog.setHeaderText("Enter Inventory Update Details");
            dialog.setContentText("Format: medicationName,stockToAdd (e.g., Ibuprofen,100)");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String[] details = result.get().split(",");
                if (details.length != 2) {
                    throw new IllegalArgumentException("Invalid format. Please provide medication name and stock.");
                }

                String medicationName = details[0];
                int stockToAdd = Integer.parseInt(details[1]);

                // Get the inventory file path
                Path inventoryFilePath = BackEnd.FileHelper.findPharmacyInventoryFile();

                // Call the updated method with the Path argument
                InventoryControl.PharmacyInventory.updateStock(medicationName, stockToAdd, inventoryFilePath);

                showAlert("Success", "Inventory updated successfully!", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            showErrorAlert("Error Updating Inventory", e.getMessage());
        }
    }

    @FXML
    private void handleProcessPrescription(ActionEvent event) {
        showInfo("Processing Prescription", "Prescription is being processed.");
    }

    @FXML
    private void handleRefillPrescription(ActionEvent event) {
        try {
            // Step 1: Prompt for customer information
            TextInputDialog customerDialog = new TextInputDialog();
            customerDialog.setTitle("Refill Prescription");
            customerDialog.setHeaderText("Enter Customer Information");
            customerDialog.setContentText("Enter customer name:");

            Optional<String> customerName = customerDialog.showAndWait();
            if (!customerName.isPresent() || customerName.get().trim().isEmpty()) {
                showErrorAlert("Error", "Customer information is required.");
                return;
            }

            // Step 2: Prompt for prescribed medication details
            TextInputDialog medicationDialog = new TextInputDialog();
            medicationDialog.setTitle("Refill Prescription");
            medicationDialog.setHeaderText("Enter Medication Details");
            medicationDialog.setContentText("Enter medication name:");

            Optional<String> medicationName = medicationDialog.showAndWait();
            if (!medicationName.isPresent() || medicationName.get().trim().isEmpty()) {
                showErrorAlert("Error", "Medication information is required.");
                return;
            }

            // Step 3: Check inventory for the medication
            Path inventoryFile = FileHelper.findPharmacyInventoryFile();
            ArrayList<Medication> inventoryList = PharmacyInventory.readPharmacyInventory(inventoryFile);

            Medication medication = inventoryList.stream()
                    .filter(med -> med.getName().equalsIgnoreCase(medicationName.get()))
                    .findFirst()
                    .orElse(null);

            if (medication == null) {
                showErrorAlert("Error", "Medication not found in inventory.");
                return;
            }

            // Step 4: Process the refill
            if (medication.getStock() > 0) {
                medication.setStock(medication.getStock() - 1);
                PharmacyInventory.updateMedication(medication);
                showInfoAlert("Refill Successful", "Prescription refilled for " + customerName.get() + ":\n" +
                        "Medication: " + medication.getName() + "\nRemaining Stock: " + medication.getStock());
            } else {
                showErrorAlert("Error", "Insufficient stock for " + medication.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error", "Unable to process refill: " + e.getMessage());
        }
    }
    
 // Helper method to show error alerts
    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
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
    
    private void showInfoAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}