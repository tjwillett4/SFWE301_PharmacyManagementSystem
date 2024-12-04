package UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import BackEnd.AccountHandling;
import BackEnd.Employee;

public class ResetPasswordController {

    @FXML private TextField usernameField;
    @FXML private PasswordField newPasswordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label errorMessage;

    @FXML
    private void handleResetPassword(ActionEvent event) {
        String username = usernameField.getText();
        String newPassword = newPasswordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        if (username.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            errorMessage.setText("All fields are required.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            errorMessage.setText("Passwords do not match.");
            return;
        }

        try {
            boolean userExists = AccountHandling.employeeExists(username);
            if (userExists) {
                // Retrieve the employee and update the password
                Employee employee = AccountHandling.getEmployeeByUsername(username);
                if (employee != null) {
                    employee.setPassword(newPassword);
                    AccountHandling.changeEmployeeAccount(username, employee);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Password reset successfully!");
                } else {
                    errorMessage.setText("User retrieval failed.");
                }
            } else {
                errorMessage.setText("Username not found.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while resetting the password.");
            e.printStackTrace();
        }
    }

    // Method to display alerts
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}