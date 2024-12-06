package UserInterface;

import java.util.ArrayList;

import BackEnd.FileHelper;
import Prescriptions.Medication;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class MedicationSearchController {

    @FXML private TextField searchField;
    @FXML private ComboBox<String> categoryComboBox;
    @FXML private ComboBox<String> supplierComboBox;
    @FXML private CheckBox inStockCheckBox;

    @FXML private TableView<Medication> resultsTable;
    @FXML private TableColumn<Medication, String> nameColumn;
    @FXML private TableColumn<Medication, String> categoryColumn;
    @FXML private TableColumn<Medication, String> supplierColumn;
    @FXML private TableColumn<Medication, Integer> stockColumn;

    private ObservableList<Medication> medicationList;

    @FXML
    private void initialize() {
        // Initialize the medication list as an observable list for UI binding
        medicationList = FXCollections.observableArrayList();

        try {
            // Load filter options for the category and supplier ComboBoxes
            loadFilters();
        } catch (Exception e) {
            // Display an error alert if filters fail to load
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load filters: " + e.getMessage());
        }

        // Bind the columns of the table to medication properties
        bindTableColumns();

        // Set the medication list as the data source for the results table
        resultsTable.setItems(medicationList);
    }

    private void loadFilters() throws Exception {
        // Fetch all medications from storage
        ArrayList<Medication> medications = FileHelper.getAllMedications();

        if (medications != null && !medications.isEmpty()) {
            // Populate the category ComboBox with distinct categories from medications
            categoryComboBox.setItems(FXCollections.observableArrayList(
                medications.stream().map(Medication::getCategory).distinct().toList()
            ));

            // Populate the supplier ComboBox with distinct suppliers from medications
            supplierComboBox.setItems(FXCollections.observableArrayList(
                medications.stream().map(Medication::getSupplier).distinct().toList()
            ));
        } else {
            // If no medications are available, set empty ComboBox options
            categoryComboBox.setItems(FXCollections.observableArrayList());
            supplierComboBox.setItems(FXCollections.observableArrayList());
        }
    }

    private void bindTableColumns() {
        // Bind the name column to the "name" property of Medication objects
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));

        // Bind the category column to the "category" property of Medication objects
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));

        // Bind the supplier column to the "supplier" property of Medication objects
        supplierColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSupplier()));

        // Bind the stock column to the "stock" property of Medication objects as an integer
        stockColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
    }

    @FXML
    private void handleSearch() {
        // Retrieve the search keyword from the search field
        String keyword = searchField.getText();

        // Get the selected category and supplier from the ComboBoxes
        String category = categoryComboBox.getValue();
        String supplier = supplierComboBox.getValue();

        // Check if the "In Stock Only" checkbox is selected
        boolean inStockOnly = inStockCheckBox.isSelected();

        try {
            // Perform a search using the provided criteria and fetch matching medications
            ArrayList<Medication> results = FileHelper.searchMedications(keyword, category, supplier, inStockOnly);

            // Update the medication list with the search results
            medicationList.setAll(results);
        } catch (Exception e) {
            // Display an error alert if the search operation fails
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to perform search: " + e.getMessage());
        }
    }

    @FXML
    private void handleClear() {
        // Clear the search field text
        searchField.clear();

        // Reset the selected values of the category and supplier ComboBoxes
        categoryComboBox.setValue(null);
        supplierComboBox.setValue(null);

        // Uncheck the "In Stock Only" checkbox
        inStockCheckBox.setSelected(false);

        // Clear the current list of medications
        medicationList.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        // Create and configure an alert dialog with the given type, title, and message
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null); // No header text
        alert.setContentText(message); // Set the content text
        alert.showAndWait(); // Display the alert and wait for the user to dismiss it
    }
}