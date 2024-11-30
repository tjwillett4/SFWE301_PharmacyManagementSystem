package UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import BackEnd.AccountHandling;
import BackEnd.Employee;
import UserInterface.NavigationUtil;
import UserInterface.Session;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorMessage;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            Employee employee = AccountHandling.logIn(username, password);
            if (employee != null) {
                Session.setCurrentUser(employee);
                NavigationUtil.loadMainDashboard(event);
            } else {
                errorMessage.setText("Invalid username or password.");
            }
        } catch (Exception e) {
            errorMessage.setText("An error occurred during login.");
            e.printStackTrace();
        }
    }
}