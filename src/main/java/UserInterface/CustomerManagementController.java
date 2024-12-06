package UserInterface;

import BackEnd.AccountHandling;
import BackEnd.Customer;
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

    /**
     * Initializes the customer table and loads customer data.
     * - Fetches customer data from storage and handles any potential errors during loading.
     * - Binds table columns to customer object properties for display.
     */
    @FXML
    private void initialize() {
        customerList = FXCollections.observableArrayList();
        try {
            // Load customers from storage
            loadCustomers();
        } catch (Exception e) {
            // Show an error alert if customer data fails to load
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load customer records: " + e.getMessage());
        }

        // Bind customer properties to table columns
        firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNameFirst()));
        lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNameLast()));
        phoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNum()));
        addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));

        // Set customer data to the table
        customerTable.setItems(customerList);
    }

    /**
     * Loads customer data from storage and populates the customer list.
     *
     * @throws Exception If there is an error while reading customer data from storage.
     */
    private void loadCustomers() throws Exception {
        customerList.setAll(AccountHandling.readCustomerStorage());
    }

    /**
     * Handles the addition of a new customer.
     * - This method could be extended to open a dialog or form for adding customer details.
     */
    @FXML
    private void handleAddCustomer() {
        // Placeholder logic for adding a customer
        System.out.println("Add Customer button clicked.");
    }

    /**
     * Handles editing of the selected customer in the table.
     * - Opens a form or dialog for editing the customer's details.
     * - Displays a warning if no customer is selected.
     */
    @FXML
    private void handleEditCustomer() {
        // Get the selected customer from the table
        Customer selected = customerTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            // Placeholder logic for editing the customer
            System.out.println("Editing customer: " + selected.getNameFirst() + " " + selected.getNameLast());
        } else {
            // Show a warning alert if no customer is selected
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a customer to edit.");
        }
    }

    /**
     * Handles the removal of a selected customer from the table and storage.
     * - Prompts the user to select a customer for removal.
     * - Removes the customer from the displayed list and updates storage.
     * - Displays an error alert if the removal fails or no customer is selected.
     */
    @FXML
    private void handleRemoveCustomer() {
        // Get the selected customer from the table
        Customer selected = customerTable.getSelectionModel().getSelectedItem();

        if (selected != null) {
            try {
                // Remove the customer from storage
                AccountHandling.removeCustomer(selected);

                // Remove the customer from the table list
                customerList.remove(selected);
            } catch (Exception e) {
                // Show an error alert if the removal process fails
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to remove customer: " + e.getMessage());
            }
        } else {
            // Show a warning alert if no customer is selected
            showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a customer to remove.");
        }
    }
    
    public void refreshCustomerTable() {
        try {
            customerList.setAll(AccountHandling.readCustomerStorage());
            customerTable.refresh();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to refresh customer data: " + e.getMessage());
        }
    }

    /**
     * Helper method to display alerts with a specific type, title, and message.
     *
     * @param type    The type of alert (e.g., ERROR, INFORMATION).
     * @param title   The title of the alert dialog.
     * @param message The content message to display in the alert.
     */
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}