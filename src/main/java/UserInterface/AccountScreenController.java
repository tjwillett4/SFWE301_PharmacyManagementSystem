package UserInterface;

import BackEnd.Employee;
import BackEnd.AccountHandling;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

public class AccountScreenController {
    
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField currentPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private TextField contactField;

    @FXML
    private void initialize() {
        // Pre-fill the fields with the logged-in user's data
        Employee currentUser = Session.getCurrentUser();
        usernameField.setText(currentUser.getUsername());
        contactField.setText(currentUser.getContactInfo()); // Assuming getContactInfo() exists
    }

    @FXML
    private void handleUpdateAccount(ActionEvent event) {
        try {
            // Get the current user from the session
            Employee currentUser = Session.getCurrentUser();
            String username = currentUser.getUsername();

            // Create a new Employee object with updated information
            Employee updatedEmployee = new Employee();
            updatedEmployee.setUsername(username);
            updatedEmployee.setPassword(newPasswordField.getText()); // Assuming there's a newPasswordField for password input
            updatedEmployee.setAccountRole(currentUser.getAccountRole()); // Retain the same role
            updatedEmployee.setLoginAttempts(currentUser.getLoginAttempts()); // Retain the same login attempts
            updatedEmployee.setSecCodePass(currentUser.getSecCodePass()); // Retain the same security pass

            // Update the account using the correct method
            AccountHandling.updateEmployeeAccount(username, updatedEmployee);

            // Show success message
            showInfo("Account Updated", "Your account information has been successfully updated.");
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error", "Failed to update account: " + e.getMessage());
        }
    }

    @FXML
    private void handleCancel() {
        NavigationUtil.loadMainDashboard(); // Return to the main dashboard
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

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}