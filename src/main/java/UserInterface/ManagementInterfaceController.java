package UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import BackEnd.Role;
import UserInterface.Session;

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
            NavigationUtil.loadMainDashboard(null);
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        Session.clear();
        NavigationUtil.loadLoginScreen(event);
    }

    @FXML
    private void handleExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    private void goToDashboard(ActionEvent event) {
        NavigationUtil.loadMainDashboard(event);
    }

    @FXML
    private void openInventoryManagement(ActionEvent event) {
        NavigationUtil.loadInventoryManagement(event);
    }

    @FXML
    private void openSalesTracking(ActionEvent event) {
        NavigationUtil.loadSalesTracking(event);
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