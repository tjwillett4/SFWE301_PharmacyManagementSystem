package UserInterface;

import BackEnd.AccountHandling;
import BackEnd.Customer;
import BackEnd.FileHelper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class CustomerManagementController {

    @FXML private TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> firstNameColumn;
    @FXML private TableColumn<Customer, String> lastNameColumn;
    @FXML private TableColumn<Customer, String> phoneNumberColumn;
    @FXML private TableColumn<Customer, String> addressColumn;

    private ObservableList<Customer> customerList;

    @FXML
    private void initialize() {
        customerList = FXCollections.observableArrayList();
        try {
            loadCustomers(); // Ensure exceptions are handled here
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load customer records: " + e.getMessage());
        }

        // Bind table columns to customer properties
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNameFirst()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNameLast()));
        phoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNum()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

        customerTable.setItems(customerList);
    }

    private void loadCustomers() throws Exception {
        customerList.setAll(AccountHandling.readCustomerStorage());
    }

    @FXML
    private void handleAddCustomer() {
        // Logic for adding a customer (e.g., open a form dialog)
        System.out.println("Add Customer button clicked.");
    }

    @FXML
    private void handleEditCustomer() {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            // Logic for editing the selected customer (e.g., open a form dialog)
            System.out.println("Editing customer: " + selected.getNameFirst() + " " + selected.getNameLast());
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a customer to edit.");
        }
    }

    @FXML
    private void handleRemoveCustomer() {
        Customer selected = customerTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            try {
                AccountHandling.removeCustomer(selected);
                customerList.remove(selected);
            } catch (Exception e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to remove customer: " + e.getMessage());
            }
        } else {
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a customer to remove.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}