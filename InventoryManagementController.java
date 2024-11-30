package UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import InventoryControl.BatchMedication;
import java.time.LocalDate;

public class InventoryManagementController {

    @FXML private TableView<BatchMedication> inventoryTable;
    @FXML private TableColumn<BatchMedication, String> medNameColumn;
    @FXML private TableColumn<BatchMedication, Integer> quantityColumn;
    @FXML private TableColumn<BatchMedication, LocalDate> expirationDateColumn;

    private ObservableList<BatchMedication> medicationList;

    public InventoryManagementController() {
        this.medicationList = FXCollections.observableArrayList();
    }

    @FXML
    private void initialize() {
        medNameColumn.setCellValueFactory(cellData -> cellData.getValue().medicationNameProperty());
        quantityColumn.setCellValueFactory(cellData -> cellData.getValue().quantityProperty().asObject());
        expirationDateColumn.setCellValueFactory(cellData -> cellData.getValue().expirationDateProperty());

        inventoryTable.setItems(medicationList);
    }

    @FXML
    private void handleAddMedication(ActionEvent event) {
        // Example addition
        BatchMedication newBatch = new BatchMedication(LocalDate.now(), LocalDate.now().plusMonths(6), 100);
        medicationList.add(newBatch);
    }

    @FXML
    private void handleRemoveMedication(ActionEvent event) {
        BatchMedication selected = inventoryTable.getSelectionModel().getSelectedItem();
        if (selected != null) {
            medicationList.remove(selected);
        }
    }
}