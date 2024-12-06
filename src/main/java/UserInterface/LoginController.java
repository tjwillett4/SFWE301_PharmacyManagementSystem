package UserInterface;

import BackEnd.AccountHandling;
import BackEnd.Employee;
import BackEnd.Role;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorMessage;
    
    
    /**
     * Handles the login process for the user.
     * - Validates the username and password entered in the login form.
     * - Checks for various scenarios like empty passwords, invalid roles, and null usernames.
     * - Navigates to the appropriate dashboard on successful login.
     * - Displays appropriate error alerts on login failure.
     */
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            Employee authenticatedEmployee = AccountHandling.logIn(username, password);

            // Handle login issues related to empty passwords or missing roles
            if (authenticatedEmployee.getPassword().length() == 0 || authenticatedEmployee.getAccountRole() == null) {
                System.out.println(authenticatedEmployee.getUsername());
                showAlert(Alert.AlertType.ERROR, "Issue Logging In", authenticatedEmployee.getUsername());
            } 
            // Handle invalid login attempts (e.g., null username)
            else if (authenticatedEmployee.getUsername() == null) {
                System.out.println("Invalid login attempt for user: " + username);
                handleFailedLogin(username);
            } 
            // Handle successful login
            else {
                System.out.println("Login successful for user: " + username);
                Session.setCurrentUser(authenticatedEmployee);
                NavigationUtil.loadMainDashboard();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred during login.");
        }
    }

    /**
     * Redirects the authenticated user to the appropriate dashboard based on their role.
     * - Full access for pharmacy managers.
     * - Limited dashboards for pharmacy technicians, cashiers, and pharmacists.
     * - Displays an error alert for unsupported roles.
     */
    private void redirectToDashboard(Employee employee, ActionEvent event) {
        Role role = employee.getAccountRole();

        switch (role) {
            case pharmacyManager:
                NavigationUtil.loadMainDashboard(); // Full access for managers
                break;
            case pharmacyTech:
                NavigationUtil.loadMainDashboard(); // Limited dashboard for pharmacy technicians
                break;
            case Cashier:
                NavigationUtil.loadMainDashboard(); // Simplified dashboard for cashiers
                break;
            case pharmacist:
                NavigationUtil.loadMainDashboard(); // Dashboard for pharmacists
                break;
            default:
                showAlert(Alert.AlertType.ERROR, "Unauthorized", "Your role is not supported in the system.");
        }
    }
    
    /**
     * Handles failed login attempts by updating the backend and notifying the user.
     * - Increments login attempts for the given username.
     * - Checks if the account is locked after the failed attempt.
     * - Displays an error alert if the account is locked or login fails.
     */
    private void handleFailedLogin(String username) {
        try {
            // Increment login attempts in the backend
            AccountHandling.incrementLoginAttempts(username);

            // Check if the account is now locked
            boolean isLocked = AccountHandling.isAccountLocked(username);
            if (isLocked) {
                showAlert(Alert.AlertType.ERROR, "Account Locked", "Your account is now locked. Please contact an administrator.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid username or password.");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to update login attempts. " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Displays a placeholder message for the "Forgot Password" functionality.
     * Currently, the functionality is not implemented.
     */
    @FXML
    private void handleForgotPassword() {
        errorMessage.setText("Password recovery is not implemented yet.");
    }

    /**
     * Exits the application when the "Exit" button is clicked.
     */
    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }

    /**
     * Helper method to display an alert with the given type, title, and message.
     * - Supports various types of alerts (e.g., ERROR, INFORMATION).
     * - The alert is shown as a modal dialog.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}