package UserInterface;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import BackEnd.AccountHandling;
import BackEnd.Customer;
import BackEnd.Employee;
import BackEnd.FileHelper;
import BackEnd.Role;
import InventoryControl.PharmacyInventory;
import Prescriptions.Medication;
import Prescriptions.Prescription;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainDashboardController {

	 @FXML private Button processSale;
	 @FXML private Button refillPrescription;
	 @FXML private Button createPrescription;
	 @FXML private Button viewInventory;
	 @FXML private Button searchMedications;
	 @FXML private Button addEmployee;
	 @FXML private Button unlockAccount;
	 @FXML private Button reportsTab;
	 @FXML private Button inventoryTab;
	 @FXML private Button resetEmployeePassword;
	 @FXML private Button addCustomerButton;
	 @FXML private Button removeCustomerButton;
	 @FXML private Button deleteAccountButton;
	 @FXML private Button updateCustomerButton;
	 @FXML private Button approvePrescription;
	 @FXML private Button cancelPrescription;
	 

	 // Prescription Table
	 @FXML private TableView<Prescription> prescriptionTable;
	 @FXML private TableColumn<Prescription, Integer> idColumn;
	 @FXML private TableColumn<Prescription, String> customerColumn;
	 @FXML private TableColumn<Prescription, String> medicationColumn;
	 @FXML private TableColumn<Prescription, String> statusColumn;

	 // Customer Table
	 @FXML private TableView<Customer> customerTable;
	 @FXML private TableColumn<Customer, String> customerNameColumn;
	 @FXML private TableColumn<Customer, String> emailColumn;
	 @FXML private TableColumn<Customer, String> phoneColumn;

	 private ObservableList<Prescription> prescriptionData;
	 private ObservableList<Customer> customerData = FXCollections.observableArrayList();



	 @FXML
	 private void initialize() {
		 checkAuthorization();
		    initializePrescriptionTracking();
		    initializeCustomerTable();
		    loadCustomerData();
	 }


    /*
     * 	Customer,
	Cashier,
	pharmacyTech,
	pharmacist,
	pharmacyManager,
	doctor
     */
	 
	 private void initializeCustomerTable() {
		    customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		    emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		    phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));

		    customerTable.setItems(customerData);
		}
    //Show or hide UI based on the permissions of the user. 
    private void checkAuthorization() {
        Role currentRole = Session.getCurrentUser().getAccountRole();

        switch(currentRole) {
	        case Customer:
	        	//No permissions
	            processSale.setDisable(true);
	            refillPrescription.setDisable(true);
	            createPrescription.setDisable(true);
	            viewInventory.setDisable(true);
	            searchMedications.setDisable(true);
	            addEmployee.setDisable(true);
	            unlockAccount.setDisable(true);
	            reportsTab.setDisable(true);
	            inventoryTab.setDisable(true);
	            deleteAccountButton.setDisable(true);
	            updateCustomerButton.setDisable(true);
	            removeCustomerButton.setDisable(true);
	            addCustomerButton.setDisable(true);
	            approvePrescription.setDisable(true);
	            cancelPrescription.setDisable(true);
	        	break;
	        case Cashier:
	        	//Only sales permissions
	            refillPrescription.setDisable(true);
	            createPrescription.setDisable(true);
	            viewInventory.setDisable(true);
	            searchMedications.setDisable(true);
	            addEmployee.setDisable(true);
	            unlockAccount.setDisable(true);
	            reportsTab.setDisable(true);
	            inventoryTab.setDisable(true);
	            deleteAccountButton.setDisable(true);
	            updateCustomerButton.setDisable(true);
	            removeCustomerButton.setDisable(true);
	            addCustomerButton.setDisable(true);
	            approvePrescription.setDisable(true);
	            cancelPrescription.setDisable(true);
	        	break;
	        case pharmacyTech:
	        	//Sale plus prescription/medication info. Cannot create prescriptions, only fulfill. 
	            refillPrescription.setDisable(true);
	            createPrescription.setDisable(true);
	            addEmployee.setDisable(true);
	            unlockAccount.setDisable(true);
	            reportsTab.setDisable(true);
	            inventoryTab.setDisable(true);
	            deleteAccountButton.setDisable(true);
	            removeCustomerButton.setDisable(true);
	            addCustomerButton.setDisable(true);
	            approvePrescription.setDisable(true);
	            cancelPrescription.setDisable(true);
	        	break;
	        case pharmacist:
	        	//Sale plus prescription/medication info. No management actions. 
	            addEmployee.setDisable(true);
	            unlockAccount.setDisable(true);
	            reportsTab.setDisable(true);
	            inventoryTab.setDisable(true);
	            deleteAccountButton.setDisable(true);
	        	break;
	        case pharmacyManager:
	        	//All permissions enabled. 
	        	break;
	        case doctor:
	        	//Unknown permissions. Will disable all. 
	        	//No permissions
	            processSale.setDisable(true);
	            refillPrescription.setDisable(true);
	            createPrescription.setDisable(true);
	            viewInventory.setDisable(true);
	            searchMedications.setDisable(true);
	            addEmployee.setDisable(true);
	            unlockAccount.setDisable(true);
	            reportsTab.setDisable(true);
	            inventoryTab.setDisable(true);
	            deleteAccountButton.setDisable(true);
	            updateCustomerButton.setDisable(true);
	            removeCustomerButton.setDisable(true);
	            addCustomerButton.setDisable(true);
	            approvePrescription.setDisable(true);
	            cancelPrescription.setDisable(true);
	        	break;
        	default:
            	//No permissions
                processSale.setDisable(true);
                refillPrescription.setDisable(true);
                createPrescription.setDisable(true);
                viewInventory.setDisable(true);
                searchMedications.setDisable(true);
                addEmployee.setDisable(true);
                unlockAccount.setDisable(true);
                reportsTab.setDisable(true);
                inventoryTab.setDisable(true);
                deleteAccountButton.setDisable(true);
                updateCustomerButton.setDisable(true);
                removeCustomerButton.setDisable(true);
                addCustomerButton.setDisable(true);
                approvePrescription.setDisable(true);
	            cancelPrescription.setDisable(true);
                break;
        		
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        Session.clear();
        NavigationUtil.loadLoginScreen(event);
    }

    @FXML
    private void handleExit(ActionEvent event) {
        try {
            AccountHandling.saveCustomerData(new ArrayList<>(customerData)); // Save data to file
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error", "Failed to save customer data on exit: " + e.getMessage());
        }

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
    private void handleDeleteAccount(ActionEvent event) {
        // Ensure only managers can delete accounts
        Role currentRole = Session.getCurrentUser().getAccountRole();
        if (currentRole != Role.pharmacyManager) {
            showErrorAlert("Unauthorized Access", "Only managers can delete accounts.");
            return;
        }

        // Prompt the manager to enter the username of the account to delete
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Account");
        dialog.setHeaderText("Delete Employee Account");
        dialog.setContentText("Enter the username of the account to delete:");

        // Handle the input from the dialog
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(username -> {
            try {
                // Check if the account exists
                Employee emp = AccountHandling.getEmployeeByUsername(username);
                if (emp == null) {
                    showErrorAlert("Error", "No account found for username: " + username);
                    return;
                }

                // Confirm deletion
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirm Deletion");
                confirmationAlert.setHeaderText("Are you sure?");
                confirmationAlert.setContentText("Do you really want to delete the account for username: " + username + "?");

                Optional<ButtonType> confirmation = confirmationAlert.showAndWait();
                if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                    // Perform the deletion
                    AccountHandling.deleteEmployeeAccount(username);
                    showInfoAlert("Success", "Account for username: " + username + " deleted successfully.");
                }
            } catch (Exception e) {
                // Log the exception and show an error alert
                e.printStackTrace();
                showErrorAlert("Error", "Failed to delete account: " + e.getMessage());
            }
        });
    }
    
    @FXML
    private void handleUpdateAccount(ActionEvent event) {
        // Get the current user's role and username
        Role currentRole = Session.getCurrentUser().getAccountRole();
        String currentUsername = Session.getCurrentUser().getUsername();

        String targetUsername;

        // Determine if the manager is updating another user's password or if the user is updating their own
        if (currentRole == Role.pharmacyManager) {
            // Manager is allowed to update any user's password
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Update Password");
            dialog.setHeaderText("Enter the username of the account to update:");
            dialog.setContentText("Username:");
            Optional<String> result = dialog.showAndWait();

            if (result.isEmpty() || result.get().trim().isEmpty()) {
                showErrorAlert("Error", "Username cannot be empty.");
                return;
            }
            targetUsername = result.get();
        } else {
            // Non-manager users can only update their own password
            targetUsername = currentUsername;
        }

        // Prompt for the new password
        TextInputDialog passwordDialog = new TextInputDialog();
        passwordDialog.setTitle("Update Password");
        passwordDialog.setHeaderText("Enter the new password:");
        passwordDialog.setContentText("New Password:");
        Optional<String> passwordResult = passwordDialog.showAndWait();

        if (passwordResult.isEmpty() || passwordResult.get().trim().isEmpty()) {
            showErrorAlert("Error", "Password cannot be empty.");
            return;
        }

        String newPassword = passwordResult.get();

        try {
            // Retrieve the employee account
            Employee emp = AccountHandling.getEmployeeByUsername(targetUsername);
            if (emp == null) {
                showErrorAlert("Error", "Account with the username '" + targetUsername + "' not found.");
                return;
            }

            // Update the password
            emp.setPassword(newPassword);
            AccountHandling.changeEmployeeAccount(targetUsername, emp);

            // Inform the user of success
            if (currentRole == Role.pharmacyManager) {
                showInfoAlert("Success", "Password updated successfully for user: " + targetUsername);
            } else {
                showInfoAlert("Success", "Your password has been updated successfully.");
            }
        } catch (Exception e) {
            // Handle any exceptions during the process
            e.printStackTrace();
            showErrorAlert("Error", "Failed to update password: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleEmployeePasswordReset(ActionEvent event) {
        Role currentRole = Session.getCurrentUser().getAccountRole();
        Employee currentUser = Session.getCurrentUser();

        if (currentRole == Role.pharmacyManager) {
            // If the current user is a manager, they can reset any employee's password
            TextInputDialog dialogUsername = new TextInputDialog();
            dialogUsername.setTitle("Reset Employee Password");
            dialogUsername.setHeaderText("Resetting an employee password should only be done if the employee forgot their password.");
            dialogUsername.setContentText("Enter the username of the account:");

            dialogUsername.showAndWait().ifPresent(username -> {
                try {
                    Employee emp = AccountHandling.getEmployeeByUsername(username);

                    if (emp == null) {
                        showInfo("Error", "User not found.");
                        return;
                    }

                    TextInputDialog dialogNewPassword = new TextInputDialog();
                    dialogNewPassword.setTitle("Reset Employee Password");
                    dialogNewPassword.setHeaderText("Enter the new password for the account:");
                    dialogNewPassword.setContentText("New Password:");

                    dialogNewPassword.showAndWait().ifPresent(password -> {
                        emp.setPassword(password);
                        try {
                            AccountHandling.changeEmployeeAccount(username, emp);
                            showInfo("Success", "Account password successfully changed for user: " + username);
                        } catch (Exception e) {
                            e.printStackTrace();
                            showError("Error", "Failed to update account password: " + e.getMessage());
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                    showError("Error", "Failed to retrieve or update account: " + e.getMessage());
                }
            });
        } else {
            // If the current user is not a manager, they can only reset their own password
            TextInputDialog dialogNewPassword = new TextInputDialog();
            dialogNewPassword.setTitle("Reset Your Password");
            dialogNewPassword.setHeaderText("Enter your new password:");
            dialogNewPassword.setContentText("New Password:");

            dialogNewPassword.showAndWait().ifPresent(password -> {
                try {
                    currentUser.setPassword(password);
                    AccountHandling.changeEmployeeAccount(currentUser.getUsername(), currentUser);
                    showInfo("Success", "Your password has been successfully updated.");
                } catch (Exception e) {
                    e.printStackTrace();
                    showError("Error", "Failed to update your password: " + e.getMessage());
                }
            });
        }
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

 // Initialize the prescription table
    private void initializePrescriptionTracking() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        customerColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        medicationColumn.setCellValueFactory(new PropertyValueFactory<>("medicationName"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        loadPrescriptionData();
    }

    private void loadPrescriptionData() {
        prescriptionData = FXCollections.observableArrayList(
            new Prescription(1, "John Doe", "Ibuprofen", "Pending"),
            new Prescription(2, "Jane Smith", "Paracetamol", "Approved")
        );
        prescriptionTable.setItems(prescriptionData);
    }
    
    private void loadCustomerData() {
        try {
            List<Customer> customers = AccountHandling.loadCustomerData();
            customerData = FXCollections.observableArrayList(customers);
            customerTable.setItems(customerData);
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error", "Failed to load customer data: " + e.getMessage());
        }
    }
    
    @FXML
    private void handleApprovePrescription(ActionEvent event) {
        // Check role-based authorization
        Role currentRole = Session.getCurrentUser().getAccountRole();
        if (currentRole == Role.Cashier) {
            showErrorAlert("Unauthorized Access", "You do not have permission to approve prescriptions.");
            return;
        }

        // Check if a prescription is selected
        Prescription selected = prescriptionTable.getSelectionModel().getSelectedItem();
        if (selected != null && "Pending".equalsIgnoreCase(selected.getStatus())) {
            selected.setStatus("Approved");
            prescriptionTable.refresh();
            showInfoAlert("Success", "Prescription approved.");
        } else {
            showErrorAlert("Error", "Select a valid pending prescription.");
        }
    }
    
    @FXML
    private void handleCancelPrescription(ActionEvent event) {
        // Check role-based authorization
        Role currentRole = Session.getCurrentUser().getAccountRole();
        if (currentRole == Role.Cashier) {
            showErrorAlert("Unauthorized Access", "You do not have permission to cancel prescriptions.");
            return;
        }

        // Check if a prescription is selected
        Prescription selected = prescriptionTable.getSelectionModel().getSelectedItem();
        if (selected != null && "Pending".equalsIgnoreCase(selected.getStatus())) {
            selected.setStatus("Canceled");
            prescriptionTable.refresh();
            showInfoAlert("Success", "Prescription canceled.");
        } else {
            showErrorAlert("Error", "Select a valid pending prescription.");
        }
    }
    
    @FXML
    private void handleViewCustomerDetails() {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            String details = "Name: " + selected.getFirstName() + "\n" +
                    "Email: " + selected.getEmail() + "\n" +
                    "Phone: " + selected.getPhoneNum();
            showInfoAlert("Customer Details", details);
        } else {
            showErrorAlert("Error", "No customer selected.");
        }
    }
    
    @FXML
    private void handleRefillCustomerPrescription() {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            showInfoAlert("Refill Successful", "Prescription refilled for: " + selected.getFirstName());
        } else {
            showErrorAlert("Error", "Select a customer to refill.");
        }
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
    	NavigationUtil.loadMedicationScreen(event);
    	/*
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
        }*/
    }

    @FXML
    private void handleProcessPrescription(ActionEvent event) {
        showInfo("Processing Prescription", "Prescription is being processed.");
    }

    @FXML void handlePrescriptionCreation(ActionEvent event) {
    	//TODO: Create the prescriptions. 
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
    
    @FXML
    private void handleAddCustomer(ActionEvent event) {
        try {
            TextInputDialog NameDialog = new TextInputDialog();
            NameDialog.setTitle("Add Customer");
            NameDialog.setHeaderText("Enter Customer First Name:");
            NameDialog.setContentText("Name:");
            Optional<String> NameResult = NameDialog.showAndWait();

            if (!NameResult.isPresent() || NameResult.get().trim().isEmpty()) {
                showError("Error", "Customer Name is required.");
                return;
            }

            TextInputDialog emailDialog = new TextInputDialog();
            emailDialog.setTitle("Add Customer");
            emailDialog.setHeaderText("Enter Customer Email:");
            emailDialog.setContentText("Email:");
            Optional<String> emailResult = emailDialog.showAndWait();

            if (!emailResult.isPresent() || emailResult.get().trim().isEmpty()) {
                showError("Error", "Customer Email is required.");
                return;
            }

            TextInputDialog phoneDialog = new TextInputDialog();
            phoneDialog.setTitle("Add Customer");
            phoneDialog.setHeaderText("Enter Customer Phone Number:");
            phoneDialog.setContentText("Phone Number:");
            Optional<String> phoneResult = phoneDialog.showAndWait();

            if (!phoneResult.isPresent() || phoneResult.get().trim().isEmpty()) {
                showError("Error", "Customer Phone Number is required.");
                return;
            }

            Customer newCustomer = new Customer(NameResult.get(), emailResult.get(), phoneResult.get());
            customerData.add(newCustomer);
            AccountHandling.saveCustomerData(new ArrayList<>(customerData)); // Save data to file
            customerTable.refresh();

            showInfo("Success", "Customer added successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error", "Unable to add customer: " + e.getMessage());
        }
    }
    

    /**
     * Handles removing a selected customer.
     */
    @FXML
    private void handleRemoveCustomer(ActionEvent event) {
        // Check role-based authorization
        Role currentRole = Session.getCurrentUser().getAccountRole();
        if (currentRole != Role.pharmacyManager && currentRole != Role.pharmacist) {
            showErrorAlert("Unauthorized Access", "You do not have permission to delete customer accounts.");
            return;
        }

        // Check if a customer is selected
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer != null) {
            Alert confirmationAlert = new Alert(AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Remove Customer");
            confirmationAlert.setHeaderText(null);
            confirmationAlert.setContentText("Are you sure you want to remove the selected customer?");
            Optional<ButtonType> result = confirmationAlert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                customerData.remove(selectedCustomer);
                try {
                    AccountHandling.saveCustomerData(new ArrayList<>(customerData)); // Save data to file
                    customerTable.refresh();
                    showInfo("Success", "Customer removed successfully.");
                } catch (Exception e) {
                    e.printStackTrace();
                    showError("Error", "Failed to save customer data: " + e.getMessage());
                }
            }
        } else {
            showError("Error", "No customer selected to remove.");
        }
    }
    
    @FXML
    private void handleUpdateCustomer(ActionEvent event) {
        // Check role-based authorization
        Role currentRole = Session.getCurrentUser().getAccountRole();
        if (currentRole != Role.pharmacyTech && currentRole != Role.pharmacyManager && currentRole != Role.pharmacist) {
            showErrorAlert("Unauthorized Access", "You do not have permission to update customer accounts.");
            return;
        }

        // Check if a customer is selected
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            showErrorAlert("Error", "No customer selected to update.");
            return;
        }

        // Prompt for updated name
        TextInputDialog NameDialog = new TextInputDialog(selectedCustomer.getFirstName());
        NameDialog.setTitle("Update Customer");
        NameDialog.setHeaderText("Update Name");
        NameDialog.setContentText("Enter new name:");

        Optional<String> newName = NameDialog.showAndWait();
        if (!newName.isPresent() || newName.get().trim().isEmpty()) {
            showErrorAlert("Error", "Name cannot be empty.");
            return;
        }
        
        // Prompt for updated email
        TextInputDialog emailDialog = new TextInputDialog(selectedCustomer.getEmail());
        emailDialog.setTitle("Update Customer");
        emailDialog.setHeaderText("Update Email");
        emailDialog.setContentText("Enter new email:");
        Optional<String> newEmail = emailDialog.showAndWait();
        if (!newEmail.isPresent() || newEmail.get().trim().isEmpty()) {
            showErrorAlert("Error", "Email cannot be empty.");
            return;
        }
        
        // Prompt for updated phone number
        TextInputDialog phoneDialog = new TextInputDialog(selectedCustomer.getPhoneNum());
        phoneDialog.setTitle("Update Customer");
        phoneDialog.setHeaderText("Update Phone Number");
        phoneDialog.setContentText("Enter new phone number:");
        Optional<String> newPhone = phoneDialog.showAndWait();
        if (!newPhone.isPresent() || newPhone.get().trim().isEmpty()) {
            showErrorAlert("Error", "Phone number cannot be empty.");
            return;
        }

        // Update the customer object
        selectedCustomer.setName(newName.get());
        selectedCustomer.setEmail(newEmail.get());
        selectedCustomer.setPhoneNum(newPhone.get());
        // Save updated data
        try {
            AccountHandling.saveCustomerData(new ArrayList<>(customerData)); // Save data to file
        } catch (Exception e) {
            e.printStackTrace();
            showErrorAlert("Error", "Failed to save updated customer data: " + e.getMessage());
            return;
        }

        // Refresh the table to reflect the changes
        customerTable.refresh();
        showInfo("Success", "Customer information updated successfully.");
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