package UserInterface;

import BackEnd.Employee;
import BackEnd.AccountHandling;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class AccountScreenController {
    
    @FXML private TextField usernameField;
    @FXML private PasswordField currentPasswordField;
    @FXML private PasswordField newPasswordField;
    @FXML private TextField contactField;

    /**
     * Initializes the account update screen by pre-filling the fields with the logged-in user's data.
     * - Retrieves the current user's information from the session.
     * - Pre-fills the username and contact information fields for convenience.
     */
    @FXML 
    private void initialize() {
        // Get the current user from the session
        Employee currentUser = Session.getCurrentUser();
        
        // Populate the username and contact fields with existing data
        usernameField.setText(currentUser.getUsername());
        contactField.setText(currentUser.getContactInfo()); // Assumes getContactInfo() method exists in Employee class
    }

    /**
     * Handles the account update process when the user submits updated information.
     * - Updates the current user's account with new information such as password and contact details.
     * - Retains non-updatable fields like account role, login attempts, and security code pass.
     * - Displays a success message upon successful update or an error message if the update fails.
     *
     * @param event The action event triggered by the update button.
     */
    @FXML
    private void handleUpdateAccount(ActionEvent event) {
        try {
            // Retrieve the current user from the session
            Employee currentUser = Session.getCurrentUser();
            String username = currentUser.getUsername();

            // Create an Employee object with updated information
            Employee updatedEmployee = new Employee();
            updatedEmployee.setUsername(username);
            updatedEmployee.setPassword(newPasswordField.getText()); // Retrieve new password input
            updatedEmployee.setAccountRole(currentUser.getAccountRole()); // Preserve current role
            updatedEmployee.setLoginAttempts(currentUser.getLoginAttempts()); // Preserve login attempt count
            updatedEmployee.setSecCodePass(currentUser.getSecCodePass()); // Preserve security code

            // Update the account in the backend
            AccountHandling.updateEmployeeAccount(username, updatedEmployee);

            // Show a success message
            showInfo("Account Updated", "Your account information has been successfully updated.");
        } catch (Exception e) {
            // Log the exception and display an error alert if the update fails
            e.printStackTrace();
            showError("Error", "Failed to update account: " + e.getMessage());
        }
    }

    /**
     * Handles the action to cancel the current operation and return to the main dashboard.
     * - Navigates the user back to the main dashboard using the `NavigationUtil` utility.
     */
    @FXML
    private void handleCancel() {
        NavigationUtil.loadMainDashboard(); // Return to the main dashboard
    }

    /**
     * Displays an informational alert with a specified title and message.
     * - Used for showing success or general information to the user.
     *
     * @param title   The title of the alert dialog.
     * @param message The message to be displayed in the alert.
     */
    private void showInfo(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an error alert with a specified title and message.
     * - Used for notifying the user about errors or issues encountered during operations.
     *
     * @param title   The title of the alert dialog.
     * @param message The message to be displayed in the alert.
     */
    private void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}