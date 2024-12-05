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
        checkAuthorization();
    }

    private void checkAuthorization() {
        Role currentRole = Session.getCurrentUser().getAccountRole();
        if (currentRole != Role.pharmacyManager && currentRole != Role.pharmacist) {
            showAlert(Alert.AlertType.ERROR, "Unauthorized Access", "You do not have permission to access the Management Interface.");
            //redirect to dashboard or login
            NavigationUtil.loadMainDashboard();
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        Session.clear();
        NavigationUtil.loadLoginScreen(event);
    }
    
    @FXML
    private void handleDeleteAccount(ActionEvent event) {
        // Show a dialog to get the username of the account to be deleted
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Delete Account");
        dialog.setHeaderText("Delete Employee Account");
        dialog.setContentText("Enter the username of the account to delete:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(username -> {
            try {
                // Check if the account exists
                Employee emp = AccountHandling.getEmployeeByUsername(username);
                if (emp == null) {
                    showAlert(Alert.AlertType.ERROR, "Error", "No account found for username: " + username);
                    return;
                }

                // Confirm deletion
                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                confirmationAlert.setTitle("Confirm Deletion");
                confirmationAlert.setHeaderText(null);
                confirmationAlert.setContentText("Are you sure you want to delete the account for username: " + username + "?");
                Optional<ButtonType> confirmation = confirmationAlert.showAndWait();

                if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                    // Perform the deletion
                    AccountHandling.deleteEmployeeAccount(username);
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Account for username: " + username + " deleted successfully.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to delete account: " + e.getMessage());
            }
        });
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void goToDashboard(ActionEvent event) {
        NavigationUtil.loadMainDashboard();
    }

    @FXML
    private void openInventoryManagement(ActionEvent event) {
        NavigationUtil navigationUtil = new NavigationUtil();
        navigationUtil.loadInventoryManagement(event);
    }

    @FXML
    private void openSalesTracking(ActionEvent event) {
        NavigationUtil navigationUtil = new NavigationUtil();
        navigationUtil.loadSalesTracking(event);
    }
    @FXML
    private void generateInventoryReport(ActionEvent event) {
        // Logic to generate inventory report
        showAlert(Alert.AlertType.INFORMATION, "Report Generated", "Inventory report has been generated.");
    }

    @FXML
    private void generateFinancialReport(ActionEvent event) {
        // Logic to generate financial report
        showAlert(Alert.AlertType.INFORMATION, "Report Generated", "Financial report has been generated.");
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}