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
        medicationList = FXCollections.observableArrayList();
        try {
            loadFilters();
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load filters: " + e.getMessage());
        }
        bindTableColumns();
        resultsTable.setItems(medicationList);
    }

    private void loadFilters() throws Exception {
        ArrayList<Medication> medications = FileHelper.getAllMedications();
        if (medications != null && !medications.isEmpty()) {
            categoryComboBox.setItems(FXCollections.observableArrayList(
                medications.stream().map(Medication::getCategory).distinct().toList()
            ));
            supplierComboBox.setItems(FXCollections.observableArrayList(
                medications.stream().map(Medication::getSupplier).distinct().toList()
            ));
        } else {
            categoryComboBox.setItems(FXCollections.observableArrayList());
            supplierComboBox.setItems(FXCollections.observableArrayList());
        }
    }

    private void bindTableColumns() {
        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        categoryColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCategory()));
        supplierColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSupplier()));
        stockColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
    }

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();
        String category = categoryComboBox.getValue();
        String supplier = supplierComboBox.getValue();
        boolean inStockOnly = inStockCheckBox.isSelected();

        try {
            ArrayList<Medication> results = FileHelper.searchMedications(keyword, category, supplier, inStockOnly);
            medicationList.setAll(results);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to perform search: " + e.getMessage());
        }
    }

    @FXML
    private void handleClear() {
        searchField.clear();
        categoryComboBox.setValue(null);
        supplierComboBox.setValue(null);
        inStockCheckBox.setSelected(false);
        medicationList.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}