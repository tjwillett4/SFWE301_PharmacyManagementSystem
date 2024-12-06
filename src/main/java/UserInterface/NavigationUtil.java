package UserInterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationUtil {
	
	// Static variable to hold the current session
	private static Session curSession = null;

	// Loads an FXML file, sets up the stage, and optionally closes the previous window
	private static void stageLoaderFunction(String fxmlFile, ActionEvent event, String title) {
	    // If the current session is null, initialize it
	    if (curSession == null) 
	        curSession = new Session();
	    
	    try {
	        // Load the FXML file using FXMLLoader
	        FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(fxmlFile));
	        Scene scene = new Scene(loader.load());
	        
	        // Determine which stage to use: a new stage or the stage from the event
	        Stage stage = event == null ? 
	                new Stage() : 
	                ((Stage) ((Node) event.getSource()).getScene().getWindow());
	        
	        // Set the scene and title for the stage
	        stage.setScene(scene);
	        stage.setTitle(title);
	        
	        // Close the previous window if an event is provided
	        if (event != null) {
	            // Hide the current window from the event source
	            ((Node) (event.getSource())).getScene().getWindow().hide();
	        }
	        
	        // Close the current session's stage if it exists
	        if (curSession.getCurrentStage() != null) {
	            System.out.println("Closing current stage!!");
	            curSession.closeCurrentStage();
	        }

	        // Set the new stage in the current session
	        curSession.setCurrentStage(stage);
	        
	        // Show the new stage
	        stage.show();
	    } catch (Exception e) {
	        // Print the stack trace if an error occurs
	        e.printStackTrace();
	    }
	}

	// Loads an FXML file, sets up a new stage, but does not close the previous stage
	private static void stageLoaderFunctionDontClosePrev(String fxmlFile, String title) {
	    try {
	        // Load the FXML file using FXMLLoader
	        FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(fxmlFile));
	        Scene scene = new Scene(loader.load());
	        
	        // Create a new stage for the scene
	        Stage stage = new Stage();

	        // Set the scene and title for the new stage
	        stage.setScene(scene);
	        stage.setTitle(title);
	        
	        // Show the new stage without closing the previous one
	        stage.show();
	    } catch (Exception e) {
	        // Print the stack trace if an error occurs
	        e.printStackTrace();
	    }
	}
	

	// Loads the main dashboard screen for the application
	public static void loadMainDashboard() {
	    // Calls the utility method to load the "MainDashboard.fxml" file
	    stageLoaderFunction("/MainDashboard.fxml", null, "Pharmacy Management System - Dashboard");
	}

	// Loads the login screen when triggered by an event
	public static void loadLoginScreen(ActionEvent event) {
	    // Calls the utility method to load the "Login.fxml" file with an event context
	    stageLoaderFunction("/Login.fxml", event, "Pharmacy Management System - Login");
	}

	// Loads the login screen without any associated event
	public static void loadLoginScreen() {
	    // Calls the utility method to load the "Login.fxml" file without an event context
	    stageLoaderFunction("/Login.fxml", null, "Pharmacy Management System - Login");
	}

	// Loads the medication inventory screen, without closing the previous stage
	public static void loadMedicationScreen(ActionEvent event) {
	    // Calls the utility method to load the "MedicationInventory.fxml" file without closing the previous stage
	    stageLoaderFunctionDontClosePrev("/MedicationInventory.fxml", "Medication");
	}

	// Loads the reset password screen for password recovery
	public static void loadResetPasswordScreen(ActionEvent event) {
	    // Calls the utility method to load the "ResetPassword.fxml" file
	    stageLoaderFunction("/ResetPassword.fxml", event, "Reset Password");
	}

	// Loads the inventory management screen
	public void loadInventoryManagement(ActionEvent event) {
	    // Calls the utility method to load the "InventoryManagement.fxml" file
	    stageLoaderFunction("/InventoryManagement.fxml", event, "Inventory Management");
	}

	// Loads the sales tracking screen
	public void loadSalesTracking(ActionEvent event) {
	    // Calls the utility method to load the "SalesTracking.fxml" file
	    stageLoaderFunction("/SalesTracking.fxml", event, "Sales Tracking");
	}

	// Loads the management interface for managers
	public static void loadManagementInterface(ActionEvent event) {
	    // Calls the utility method to load the "ManagementInterface.fxml" file
	    stageLoaderFunction("/ManagementInterface.fxml", event, "Management Interface");
	}

	// Loads the tech dashboard for pharmacy technicians
	public static void loadTechDashboard(ActionEvent event) {
	    // Calls the utility method to load the "TechDashboard.fxml" file
	    stageLoaderFunction("/TechDashboard.fxml", event, "Tech Dashboard");
	}

	// Loads the cashier dashboard for cashiers
	public static void loadCashierDashboard(ActionEvent event) {
	    // Calls the utility method to load the "CashierDashboard.fxml" file
	    stageLoaderFunction("/CashierDashboard.fxml", event, "Cashier Dashboard");
	}

	// Loads the prescription tracking screen
	public static void loadPrescriptionTracking(ActionEvent event) {
	    // Calls the utility method to load the "PrescriptionTracking.fxml" file
	    stageLoaderFunction("/PrescriptionTracking.fxml", event, "Prescription Tracking");
	}
}