package UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import Prescriptions.Medication;
import BackEnd.Role;
import BackEnd.Employee;
import UserInterface.Session;

public class MainDashboardController {

    @FXML private MenuItem inventoryMenuItem;
    @FXML private MenuItem salesMenuItem;
    @FXML private MenuItem reportsMenuItem;

    @FXML private TableView<Medication> inventoryOverviewTable;
    @FXML private TableColumn<Medication, String> medNameColumn;
    @FXML private TableColumn<Medication, Integer> quantityColumn;

    private Employee currentUser;
    private ObservableList<Medication> inventoryList;

    @FXML
    private void initialize() {
        currentUser = Session.getCurrentUser();
        adjustUIForRole();
        loadRealTimeInventory();
    }

    private void adjustUIForRole() {
        Role role = currentUser.getAccountRole();
        if (role == Role.pharmacyManager) {
            inventoryMenuItem.setDisable(false);
            salesMenuItem.setDisable(false);
            reportsMenuItem.setDisable(false);
        } else if (role == Role.pharmacist) {
            inventoryMenuItem.setDisable(false);
            salesMenuItem.setDisable(true);
            reportsMenuItem.setDisable(false);
        } else {
            inventoryMenuItem.setDisable(true);
            salesMenuItem.setDisable(true);
            reportsMenuItem.setDisable(true);
        }
    }

    private void loadRealTimeInventory() {
        inventoryList = FXCollections.observableArrayList();
        inventoryOverviewTable.setItems(inventoryList);
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
}