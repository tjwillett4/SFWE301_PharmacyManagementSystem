package UserInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import BackEnd.AccountHandling;
import BackEnd.Customer;
import BackEnd.Employee;
import BackEnd.PrescriptionHandling;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class MainDashboardController {
	
	 // Buttons used in the system for various functionalities, such as managing sales, 
	 // inventory, prescriptions, employee accounts, and customer records.
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



	// Initializes the dashboard by checking user authorization, setting up prescription tracking and customer table functionalities, and loading customer data.
	 @FXML
	 private void initialize() {
	     checkAuthorization(); // Ensures the user has the appropriate permissions to access features.
	     initializePrescriptionTracking(); // Sets up the prescription tracking table.
	     initializeCustomerTable(); // Configures the customer table for display.
	     loadCustomerData(); // Loads customer data into the customer table.
	 }


    /*
     * 	Customer,
	Cashier,
	pharmacyTech,
	pharmacist,
	pharmacyManager,
	doctor
     */
	 
	// Configures the customer table to display customer data and manages UI elements based on the user's role permissions.
	 private void initializeCustomerTable() {
	     customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName")); // Maps the first name property to the table column.
	     emailColumn.setCellValueFactory(new PropertyValueFactory<>("email")); // Maps the email property to the table column.
	     phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNum")); // Maps the phone number property to the table column.

	     customerTable.setItems(customerData); // Sets the customer data to the table view.
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

    // Handles the user logout process by clearing the session and navigating back to the login screen.
    @FXML
    private void handleLogout(ActionEvent event) {
        Session.clear(); // Clear the current user session.
        NavigationUtil.loadLoginScreen(event); // Redirect to the login screen.
    }

    // Handles the application exit process, saving customer data to a file and showing a confirmation dialog.
    @FXML
    private void handleExit(ActionEvent event) {
        try {
            // Save customer data to a file before exiting.
            AccountHandling.saveCustomerData(new ArrayList<>(customerData));
        } catch (Exception e) {
            e.printStackTrace(); // Log any errors during saving.
            showErrorAlert("Error", "Failed to save customer data on exit: " + e.getMessage());
        }

        // Show an exit confirmation dialog.
        Alert exitConfirmation = new Alert(AlertType.CONFIRMATION);
        exitConfirmation.setTitle("Exit Confirmation");
        exitConfirmation.setHeaderText(null);
        exitConfirmation.setContentText("Are you sure you want to exit?");
        exitConfirmation.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                System.exit(0); // Exit the application if confirmed.
            }
        });
    }

    // Handles the unlocking of locked employee accounts by prompting for a username and performing the unlock operation.
    @FXML
    private void handleUnlockAccount(ActionEvent event) {
        // Prompt the user to enter the username of the account to unlock.
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Unlock Account");
        dialog.setHeaderText("Unlock a Locked Account");
        dialog.setContentText("Enter the username of the account to unlock:");

        dialog.showAndWait().ifPresent(username -> {
            try {
                // Attempt to unlock the account with the specified username.
                boolean success = AccountHandling.unlockEmployeeAccount(username, Session.getCurrentUser().getUsername());
                if (success) {
                    showInfo("Success", "Account successfully unlocked for user: " + username);
                } else {
                    showError("Error", "Failed to unlock account. User not found.");
                }
            } catch (Exception e) {
                e.printStackTrace(); // Log any errors during the unlock process.
                showError("Error", "Failed to unlock account: " + e.getMessage());
            }
        });
    }
    
    // Handles the process of completing a sale and displays a success message.
    @FXML
    private void handleProcessSale(ActionEvent event) {
        Alert saleAlert = new Alert(AlertType.INFORMATION); // Create an informational alert.
        saleAlert.setTitle("Process Sale"); // Set the alert title.
        saleAlert.setHeaderText("Processing Sale"); // Set the header text for the alert.
        saleAlert.setContentText("Sale processed successfully!"); // Set the content of the alert.
        saleAlert.showAndWait(); // Display the alert and wait for user acknowledgment.
    }

    // Opens the inventory management interface, displaying the inventory in a TableView.
    @FXML
    private void openInventoryManagement(ActionEvent event) {
        try {
            // Retrieve the inventory data from the system.
            ArrayList<Medication> inventoryList = PharmacyInventory.readPharmacyInventory();
            ObservableList<Medication> observableInventory = FXCollections.observableArrayList(inventoryList);

            // Create a TableView to display the inventory details.
            TableView<Medication> tableView = new TableView<>();
            TableColumn<Medication, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<Medication, String> categoryColumn = new TableColumn<>("Category");
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            TableColumn<Medication, Integer> stockColumn = new TableColumn<>("Stock");
            stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            TableColumn<Medication, String> supplierColumn = new TableColumn<>("Supplier");
            supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));

            // Add columns to the TableView.
            tableView.getColumns().addAll(nameColumn, categoryColumn, stockColumn, supplierColumn);
            tableView.setItems(observableInventory); // Set the inventory data to the TableView.

            // Create and show a new stage for inventory management.
            Stage inventoryStage = new Stage();
            inventoryStage.setTitle("Inventory Management");
            inventoryStage.setScene(new javafx.scene.Scene(tableView, 600, 400)); // Set dimensions for the stage.
            inventoryStage.show(); // Display the stage.
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions that occur.
            showError("Error", "Unable to load inventory management."); // Show an error message.
        }
    }

    // Opens a dialog to add a new inventory item to the system.
    @FXML
    private void addInventory(ActionEvent event) {
        try {
            // Create a dialog for user input.
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Add Inventory"); // Set the dialog title.
            dialog.setHeaderText("Add a New Inventory Item"); // Set the dialog header.
            dialog.setContentText("Enter item details:"); // Set the content text.

            // Show the dialog and wait for user input.
            dialog.showAndWait().ifPresent(itemDetails -> {
                // Logic to handle adding the inventory item (currently a placeholder).
                System.out.println("Item added: " + itemDetails);
                showInfo("Success", "Inventory item added successfully."); // Show success message.
            });
        } catch (Exception e) {
            e.printStackTrace(); // Log any exceptions that occur.
            showError("Error", "Unable to add inventory item."); // Show an error message.
        }
    }
    
 // Handles displaying the inventory in a new TableView window.
    @FXML
    private void handleViewInventory(ActionEvent event) {
        try {
            // Retrieve the inventory list from the system.
            ArrayList<Medication> inventoryList = PharmacyInventory.readPharmacyInventory();
            ObservableList<Medication> observableInventory = FXCollections.observableArrayList(inventoryList);

            // Create a TableView to display inventory details.
            TableView<Medication> tableView = new TableView<>();
            TableColumn<Medication, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            TableColumn<Medication, String> categoryColumn = new TableColumn<>("Category");
            categoryColumn.setCellValueFactory(new PropertyValueFactory<>("category"));
            TableColumn<Medication, Integer> stockColumn = new TableColumn<>("Stock");
            stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
            TableColumn<Medication, String> supplierColumn = new TableColumn<>("Supplier");
            supplierColumn.setCellValueFactory(new PropertyValueFactory<>("supplier"));

            // Add columns to the TableView.
            tableView.getColumns().addAll(nameColumn, categoryColumn, stockColumn, supplierColumn);
            tableView.setItems(observableInventory); // Bind inventory data to the TableView.

            // Create and display a new stage for inventory.
            Stage inventoryStage = new Stage();
            inventoryStage.setTitle("Inventory");
            inventoryStage.setScene(new javafx.scene.Scene(tableView, 600, 400)); // Set stage dimensions.
            inventoryStage.show(); // Display the stage.
        } catch (Exception e) {
            // Handle any exceptions and display an error alert.
            showErrorAlert("Error", "Unable to load inventory: " + e.getMessage());
        }
    }

    // Handles the process of adding a new employee to the system.
    @FXML
    private void handleAddEmployee(ActionEvent event) {
        try {
            // Prompt for the employee's username.
            TextInputDialog usernameDialog = new TextInputDialog();
            usernameDialog.setTitle("Add Employee"); // Set dialog title.
            usernameDialog.setHeaderText("Enter Employee Username:"); // Set header text.
            usernameDialog.setContentText("Username:"); // Set content text.
            Optional<String> usernameResult = usernameDialog.showAndWait();

            if (!usernameResult.isPresent()) return; // Exit if no input.
            String username = usernameResult.get(); // Get the entered username.

            // Prompt for the employee's password.
            TextInputDialog passwordDialog = new TextInputDialog();
            passwordDialog.setTitle("Add Employee");
            passwordDialog.setHeaderText("Enter Employee Password:");
            passwordDialog.setContentText("Password:");
            Optional<String> passwordResult = passwordDialog.showAndWait();

            if (!passwordResult.isPresent()) return; // Exit if no input.
            String password = passwordResult.get(); // Get the entered password.

            // Prompt to select the employee's role.
            ChoiceDialog<Role> roleDialog = new ChoiceDialog<>(Role.pharmacyTech, Role.values());
            roleDialog.setTitle("Add Employee");
            roleDialog.setHeaderText("Select Employee Role:");
            roleDialog.setContentText("Role:");
            Optional<Role> roleResult = roleDialog.showAndWait();

            if (!roleResult.isPresent()) return; // Exit if no selection.
            Role role = roleResult.get(); // Get the selected role.

            // Create a new Employee object and add it to the system.
            Employee newEmployee = new Employee(username, password, role);
            AccountHandling.addEmployeeAccount(newEmployee);

            // Display a success message.
            showInfo("Success", "Employee " + username + " added successfully.");
        } catch (Exception e) {
            // Handle any exceptions and display an error message.
            e.printStackTrace();
            showError("Error", "Failed to add employee: " + e.getMessage());
        }
    }
    
    // Opens the Management Interface, accessible only to managers.
    @FXML
    private void openManagementInterface(ActionEvent event) {
        // Verify the current user's role is 'pharmacyManager'.
        Role currentRole = Session.getCurrentUser().getAccountRole();
        if (currentRole != Role.pharmacyManager) {
            // Show an error message if unauthorized access is attempted.
            showError("Unauthorized Access", "Only managers can access this interface.");
            return;
        }

        try {
            // Load the Management Interface.
            NavigationUtil.loadManagementInterface(event);
        } catch (Exception e) {
            // Handle exceptions and display an error message.
            e.printStackTrace();
            showError("Error", "Unable to open Management Interface.");
        }
    }

    // Handles account deletion, restricted to manager role.
    @FXML
    private void handleDeleteAccount(ActionEvent event) {
        // Ensure the current user's role is 'pharmacyManager'.
        Role currentRole = Session.getCurrentUser().getAccountRole();
        if (currentRole != Role.pharmacyManager) {
            // Show an error alert for unauthorized access.
            showErrorAlert("Unauthorized Access", "Only managers can delete accounts.");
            return;
        }

        // Prompt the manager to input the username of the account to be deleted.
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Account");
        dialog.setHeaderText("Delete Employee Account");
        dialog.setContentText("Enter the username of the account to delete:");

        // Handle the manager's input.
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(username -> {
            try {
                // Check if the account exists in the system.
                Employee emp = AccountHandling.getEmployeeByUsername(username);
                if (emp == null) {
                    // Display an error if the account is not found.
                    showErrorAlert("Error", "No account found for username: " + username);
                    return;
                }

                // Confirm the deletion with the manager.
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirm Deletion");
                confirmationAlert.setHeaderText("Are you sure?");
                confirmationAlert.setContentText("Do you really want to delete the account for username: " + username + "?");

                Optional<ButtonType> confirmation = confirmationAlert.showAndWait();
                if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                    // Perform account deletion upon confirmation.
                    AccountHandling.deleteEmployeeAccount(username);
                    showInfoAlert("Success", "Account for username: " + username + " deleted successfully.");
                }
            } catch (Exception e) {
                // Log the exception and display an error alert.
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
    
    /**
     * Handles resetting an employee's password.
     * - Managers can reset passwords for any employee.
     * - Non-managers can reset only their own password.
     */
    @FXML
    private void handleEmployeePasswordReset(ActionEvent event) {
        Role currentRole = Session.getCurrentUser().getAccountRole();
        Employee currentUser = Session.getCurrentUser();

        if (currentRole == Role.pharmacyManager) {
            // Manager's workflow to reset another employee's password
            TextInputDialog dialogUsername = new TextInputDialog();
            dialogUsername.setTitle("Reset Employee Password");
            dialogUsername.setHeaderText("Resetting an employee password should only be done if the employee forgot their password.");
            dialogUsername.setContentText("Enter the username of the account:");

            dialogUsername.showAndWait().ifPresent(username -> {
                try {
                    Employee emp = AccountHandling.getEmployeeByUsername(username);

                    if (emp == null) {
                        // Show an error if the employee is not found
                        showInfo("Error", "User not found.");
                        return;
                    }

                    // Prompt for the new password
                    TextInputDialog dialogNewPassword = new TextInputDialog();
                    dialogNewPassword.setTitle("Reset Employee Password");
                    dialogNewPassword.setHeaderText("Enter the new password for the account:");
                    dialogNewPassword.setContentText("New Password:");

                    dialogNewPassword.showAndWait().ifPresent(password -> {
                        emp.setPassword(password); // Set the new password
                        try {
                            AccountHandling.changeEmployeeAccount(username, emp); // Update account
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
            // Workflow for non-managers to reset their own password
            TextInputDialog dialogNewPassword = new TextInputDialog();
            dialogNewPassword.setTitle("Reset Your Password");
            dialogNewPassword.setHeaderText("Enter your new password:");
            dialogNewPassword.setContentText("New Password:");

            dialogNewPassword.showAndWait().ifPresent(password -> {
                try {
                    currentUser.setPassword(password); // Update user's own password
                    AccountHandling.changeEmployeeAccount(currentUser.getUsername(), currentUser); // Save changes
                    showInfo("Success", "Your password has been successfully updated.");
                } catch (Exception e) {
                    e.printStackTrace();
                    showError("Error", "Failed to update your password: " + e.getMessage());
                }
            });
        }
    }

    /**
     * Handles updating the contact information of the currently logged-in user.
     */
    @FXML
    private void handleUpdateContactInfo(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Update Contact Information");
        dialog.setHeaderText("Enter your updated contact information:");
        dialog.setContentText("Contact Info:");

        dialog.showAndWait().ifPresent(newContactInfo -> {
            try {
                Employee currentUser = Session.getCurrentUser();
                // Update contact information (requires a setter in Employee class)
                currentUser.setContactInfo(newContactInfo);
                AccountHandling.updateEmployeeAccount(currentUser.getUsername(), currentUser); // Save changes
                showInfo("Success", "Contact information updated successfully.");
            } catch (Exception e) {
                e.printStackTrace();
                showError("Error", "Failed to update contact information: " + e.getMessage());
            }
        });
    }
    
    @FXML
    private void handleCreatePrescription(ActionEvent event) {
        try {
            // Open dialog for creating a prescription
            TextInputDialog customerDialog = new TextInputDialog();
            customerDialog.setTitle("Create Prescription");
            customerDialog.setHeaderText("Enter the customer's name:");
            customerDialog.setContentText("Customer Name:");

            Optional<String> customerNameOpt = customerDialog.showAndWait();
            if (!customerNameOpt.isPresent() || customerNameOpt.get().trim().isEmpty()) {
                showAlert("Customer name is required.", "Invalid Input", Alert.AlertType.WARNING);
                return;
            }

            String customerName = customerNameOpt.get();

            TextInputDialog medicationDialog = new TextInputDialog();
            medicationDialog.setTitle("Create Prescription");
            medicationDialog.setHeaderText("Enter the medication name:");
            medicationDialog.setContentText("Medication:");

            Optional<String> medicationOpt = medicationDialog.showAndWait();
            if (!medicationOpt.isPresent() || medicationOpt.get().trim().isEmpty()) {
                showAlert("Medication name is required.", "Invalid Input", Alert.AlertType.WARNING);
                return;
            }

            String medication = medicationOpt.get();

            // Add the prescription
            Prescription newPrescription = new Prescription(
                    PrescriptionHandling.generateUniqueId(), // Generate a unique ID
                    customerName,
                    medication,
                    "Pending"
            );
            PrescriptionHandling.addPrescription(newPrescription); // Save the new prescription to storage

            prescriptionData.add(newPrescription); // Add it to the observable list
            prescriptionTable.refresh(); // Refresh the prescription table

            // Update customer data after adding the prescription
            loadCustomerData(); // Reload the customer data from storage
        } catch (Exception e) {
            showAlert("Failed to create prescription: " + e.getMessage(), "Error", Alert.AlertType.ERROR);
        }
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
    
    /**
     * Handles viewing the details of a selected customer.
     * Displays a dialog showing the customer's name, email, and phone number.
     * Shows an error alert if no customer is selected.
     */
    @FXML
    private void handleViewCustomerDetails() {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Construct and display customer details
            String details = "Name: " + selected.getFirstName() + "\n" +
                             "Email: " + selected.getEmail() + "\n" +
                             "Phone: " + selected.getPhoneNum();
            showInfoAlert("Customer Details", details);
        } else {
            // Display error alert if no customer is selected
            showErrorAlert("Error", "No customer selected.");
        }
    }

    /**
     * Handles refilling a prescription for a selected customer.
     * Displays a success alert if a customer is selected and the refill is successful.
     * Shows an error alert if no customer is selected.
     */
    @FXML
    private void handleRefillCustomerPrescription() {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Display success alert for refill
            showInfoAlert("Refill Successful", "Prescription refilled for: " + selected.getFirstName());
        } else {
            // Display error alert if no customer is selected
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
    
    /**
     * Handles searching for a medication in the inventory.
     * Prompts the user to enter a medication name through a dialog.
     * Searches the inventory for a medication matching the entered name and displays its details if found.
     * Shows an error alert if an issue occurs during the search or if no matching medication is found.
     */
    @FXML
    private void handleSearchMedications(ActionEvent event) {
        // Display a dialog to prompt the user for a medication name
        TextInputDialog searchDialog = new TextInputDialog();
        searchDialog.setTitle("Search Medications");
        searchDialog.setHeaderText("Search for a Medication");
        searchDialog.setContentText("Enter medication name:");

        // Handle the user input and search the inventory
        searchDialog.showAndWait().ifPresent(searchQuery -> {
            try {
                // Retrieve the inventory list
                ArrayList<Medication> inventoryList = PharmacyInventory.readPharmacyInventory();
                
                // Search for a matching medication
                Medication result = inventoryList.stream()
                        .filter(med -> med.getName().equalsIgnoreCase(searchQuery))
                        .findFirst()
                        .orElse(null);

                // Display the result of the search
                if (result != null) {
                    showInfoAlert("Medication Found", 
                                  "Name: " + result.getName() + "\n" +
                                  "Category: " + result.getCategory() + "\n" +
                                  "Stock: " + result.getStock());
                } else {
                    showInfoAlert("Not Found", 
                                  "No medication found with the name: " + searchQuery);
                }
            } catch (Exception e) {
                // Show an error alert if the search fails
                showErrorAlert("Error", "Unable to search medications: " + e.getMessage());
            }
        });
    }
    
    /**
     * Handles updating the inventory by navigating to the medication screen.
     * Utilizes the `NavigationUtil` class to load the "Medication" screen for inventory management.
     */
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

    /**
     * Handles the refill of a prescription for a customer.
     * - Prompts the user to input customer and medication details.
     * - Checks inventory for the requested medication.
     * - Updates inventory and confirms the refill if stock is available.
     */
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
            ArrayList<Medication> inventoryList = PharmacyInventory.readPharmacyInventory();

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
    
    /**
     * Handles adding a new customer to the system.
     * - Prompts the user for the customer's name, email, and phone number.
     * - Validates that all fields are filled before proceeding.
     * - Creates a new `Customer` object and adds it to the customer data list.
     * - Saves the updated customer data to persistent storage and refreshes the table view.
     * - Displays success or error messages as appropriate.
     */
    @FXML
    private void handleAddCustomer(ActionEvent event) {
        try {
            // Prompt for customer's name
            TextInputDialog NameDialog = new TextInputDialog();
            NameDialog.setTitle("Add Customer");
            NameDialog.setHeaderText("Enter Customer Name:");
            NameDialog.setContentText("Name:");
            Optional<String> NameResult = NameDialog.showAndWait();

            if (!NameResult.isPresent() || NameResult.get().trim().isEmpty()) {
                showError("Error", "Customer Name is required.");
                return;
            }

            // Prompt for customer's email
            TextInputDialog emailDialog = new TextInputDialog();
            emailDialog.setTitle("Add Customer");
            emailDialog.setHeaderText("Enter Customer Email:");
            emailDialog.setContentText("Email:");
            Optional<String> emailResult = emailDialog.showAndWait();

            if (!emailResult.isPresent() || emailResult.get().trim().isEmpty()) {
                showError("Error", "Customer Email is required.");
                return;
            }

            // Prompt for customer's phone number
            TextInputDialog phoneDialog = new TextInputDialog();
            phoneDialog.setTitle("Add Customer");
            phoneDialog.setHeaderText("Enter Customer Phone Number:");
            phoneDialog.setContentText("Phone Number:");
            Optional<String> phoneResult = phoneDialog.showAndWait();

            if (!phoneResult.isPresent() || phoneResult.get().trim().isEmpty()) {
                showError("Error", "Customer Phone Number is required.");
                return;
            }

            // Create a new customer and add to the data list
            Customer newCustomer = new Customer(NameResult.get(), emailResult.get(), phoneResult.get());
            customerData.add(newCustomer);

            // Save updated customer data to persistent storage
            AccountHandling.saveCustomerData(new ArrayList<>(customerData));

            // Refresh the table view
            customerTable.refresh();

            // Display success message
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

        // Prompt for updated first name
        TextInputDialog firstNameDialog = new TextInputDialog(selectedCustomer.getFirstName());
        firstNameDialog.setTitle("Update Customer");
        firstNameDialog.setHeaderText("Update First Name");
        firstNameDialog.setContentText("Enter new first name:");

        Optional<String> newFirstName = firstNameDialog.showAndWait();
        if (!newFirstName.isPresent() || newFirstName.get().trim().isEmpty()) {
            showErrorAlert("Error", "First name cannot be empty.");
            return;
        }

        // Prompt for updated last name
        TextInputDialog lastNameDialog = new TextInputDialog(selectedCustomer.getLastName());
        lastNameDialog.setTitle("Update Customer");
        lastNameDialog.setHeaderText("Update Last Name");
        lastNameDialog.setContentText("Enter new last name:");

        Optional<String> newLastName = lastNameDialog.showAndWait();
        if (!newLastName.isPresent() || newLastName.get().trim().isEmpty()) {
            showErrorAlert("Error", "Last name cannot be empty.");
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
        selectedCustomer.setFirstName(newFirstName.get());
        selectedCustomer.setLastName(newLastName.get());
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
    
    /**
     * Helper methods to display various types of alerts to the user.
     * - `showErrorAlert`: Displays an error alert with a specified title and message.
     * - `showInfo`: Displays an informational alert with a specified title and message.
     * - `showError`: Displays an error alert (alternative to `showErrorAlert`) with a specified title and message.
     * - `showInfoAlert`: Displays an informational alert (alternative to `showInfo`) with a specified title and message.
     * - `showAlert`: Generalized method to display an alert of a specified type, title, and message.
     */
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