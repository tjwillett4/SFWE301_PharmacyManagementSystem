package UserInterface;

import BackEnd.AccountHandling;
import BackEnd.Employee;
import BackEnd.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import java.util.Optional;
import javafx.scene.control.TextInputDialog;

public class ManagementInterfaceController {

	@FXML private TabPane managementTabPane;

	@FXML
	private void initialize() {
	    // Perform role-based authorization check during initialization
	    checkAuthorization();
	}

	private void checkAuthorization() {
	    // Get the current user's role
	    Role currentRole = Session.getCurrentUser().getAccountRole();

	    // Check if the current user is not a pharmacy manager or pharmacist
	    if (currentRole != Role.pharmacyManager && currentRole != Role.pharmacist) {
	        // Show an error alert for unauthorized access
	        showAlert(Alert.AlertType.ERROR, "Unauthorized Access", "You do not have permission to access the Management Interface.");
	        
	        // Redirect the user to the main dashboard
	        NavigationUtil.loadMainDashboard();
	    }
	}

	@FXML
	private void handleLogout(ActionEvent event) {
	    // Clear the current session (logout the user)
	    Session.clear();

	    // Redirect to the login screen
	    NavigationUtil.loadLoginScreen(event);
	}

	@FXML
	private void handleDeleteAccount(ActionEvent event) {
	    // Show a dialog to get the username of the account to be deleted
	    TextInputDialog dialog = new TextInputDialog();
	    dialog.setTitle("Delete Account"); // Set the title of the dialog
	    dialog.setHeaderText("Delete Employee Account"); // Set the header text
	    dialog.setContentText("Enter the username of the account to delete:"); // Set the content text

	    // Wait for the user's input
	    Optional<String> result = dialog.showAndWait();

	    // If the user provided input, proceed
	    result.ifPresent(username -> {
	        try {
	            // Check if the account exists for the provided username
	            Employee emp = AccountHandling.getEmployeeByUsername(username);
	            if (emp == null) {
	                // Show an error alert if no account is found
	                showAlert(Alert.AlertType.ERROR, "Error", "No account found for username: " + username);
	                return;
	            }

	            // Show a confirmation dialog before deleting the account
	            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
	            confirmationAlert.setTitle("Confirm Deletion"); // Set the title
	            confirmationAlert.setHeaderText(null); // No header text
	            confirmationAlert.setContentText("Are you sure you want to delete the account for username: " + username + "?");
	            Optional<ButtonType> confirmation = confirmationAlert.showAndWait();

	            // If the user confirms, proceed with the deletion
	            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
	                // Delete the employee account
	                AccountHandling.deleteEmployeeAccount(username);

	                // Show a success alert after deletion
	                showAlert(Alert.AlertType.INFORMATION, "Success", "Account for username: " + username + " deleted successfully.");
	            }
	        } catch (Exception e) {
	            // Print the exception stack trace for debugging
	            e.printStackTrace();

	            // Show an error alert if the deletion fails
	            showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete account: " + e.getMessage());
	        }
	    });
	}

	@FXML
	private void handleExit(ActionEvent event) {
	    // Exit the application
	    System.exit(0);
	}

	@FXML
	private void goToDashboard(ActionEvent event) {
	    // Navigate to the main dashboard using the navigation utility
	    NavigationUtil.loadMainDashboard();
	}

	@FXML
	private void openInventoryManagement(ActionEvent event) {
	    // Create an instance of NavigationUtil
	    NavigationUtil navigationUtil = new NavigationUtil();

	    // Load the inventory management screen
	    navigationUtil.loadInventoryManagement(event);
	}

	@FXML
	private void openSalesTracking(ActionEvent event) {
	    // Create an instance of NavigationUtil
	    NavigationUtil navigationUtil = new NavigationUtil();

	    // Load the sales tracking screen
	    navigationUtil.loadSalesTracking(event);
	}

	@FXML
	private void generateInventoryReport(ActionEvent event) {
	    // Logic to generate an inventory report
	    // Show an informational alert indicating the report has been generated
	    showAlert(Alert.AlertType.INFORMATION, "Report Generated", "Inventory report has been generated.");
	}

	@FXML
	private void generateFinancialReport(ActionEvent event) {
	    // Logic to generate a financial report
	    // Show an informational alert indicating the report has been generated
	    showAlert(Alert.AlertType.INFORMATION, "Report Generated", "Financial report has been generated.");
	}

	private void showAlert(Alert.AlertType type, String title, String message) {
	    // Create an alert of the specified type
	    Alert alert = new Alert(type);

	    // Set the title of the alert
	    alert.setTitle(title);

	    // Set the header text to null (no header)
	    alert.setHeaderText(null);

	    // Set the content text to the provided message
	    alert.setContentText(message);

	    // Display the alert and wait for user interaction
	    alert.showAndWait();
	}
}