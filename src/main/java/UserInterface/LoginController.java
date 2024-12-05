package UserInterface;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import BackEnd.FileHelper;
import BackEnd.Role;
import BackEnd.Employee;
import BackEnd.AccountHandling;
import UserInterface.Session;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorMessage;
    
    //testing default login
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            Employee authenticatedEmployee = AccountHandling.logIn(username, password);

            //if password length is zero or it doesn't have a role, Issue Logging in
            if (authenticatedEmployee.getPassword().length() == 0 || authenticatedEmployee.getAccountRole() == null) {
                System.out.println(authenticatedEmployee.getUsername());
                showAlert(Alert.AlertType.ERROR, "Issue Logging In", authenticatedEmployee.getUsername());
            } 
            //If the username is null, invalid log in attempt, for some reason. 
            else if (authenticatedEmployee.getUsername() == null) {
                System.out.println("Invalid login attempt for user: " + username);
                handleFailedLogin(username);
            } 
            //Successful login attempt. 
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
    
    private void redirectToDashboard(Employee employee, ActionEvent event) {
        Role role = employee.getAccountRole();

        switch (role) {
            case pharmacyManager:
                NavigationUtil.loadMainDashboard(); // Full access for managers
                break;
            case pharmacyTech:
                NavigationUtil.loadMainDashboard(); // Limited dashboard for tech
                break;
            case Cashier:
                NavigationUtil.loadMainDashboard(); // Simplified dashboard for cashier
                break;
            default:
                showAlert(Alert.AlertType.ERROR, "Unauthorized", "Your role is not supported in the system.");
        }
    }
    /*
    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            // Attempt to log in using AccountHandling
            Employee authenticatedEmployee = AccountHandling.logIn(username, password);

            if (authenticatedEmployee == null) {
                // Account is locked
                showAlert(Alert.AlertType.ERROR, "Account Locked", "Your account is locked. Please contact an administrator.");
                return;
            }

            if (authenticatedEmployee.getUsername() == null) {
                // Invalid username or password
                handleFailedLogin(username);
            } else {
                // Successful login
                Session.setCurrentUser(authenticatedEmployee);

                // Navigate to MainDashboard.fxml
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/MainDashboard.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    Stage stage = (Stage) usernameField.getScene().getWindow(); // Get the current stage
                    stage.setScene(scene);
                    stage.setTitle("Pharmacy Management System - Main Dashboard");
                } catch (IOException e) {
                    e.printStackTrace();
                    showAlert(Alert.AlertType.ERROR, "Error", "Failed to load the dashboard.");
                }
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred: " + e.getMessage());
            e.printStackTrace();}
        }
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
    
    @FXML
    private void handleForgotPassword() {
        errorMessage.setText("Password recovery is not implemented yet.");
    }
    
    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}