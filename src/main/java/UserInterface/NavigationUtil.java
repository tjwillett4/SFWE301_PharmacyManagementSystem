package UserInterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationUtil {
	
	private static Session curSession = null;
	
	private static void stageLoaderFunction(String fxmlFile, ActionEvent event, String title) {
    	if (curSession == null) 
    		curSession = new Session();
		try {
	        FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(fxmlFile));
	        Scene scene = new Scene(loader.load());
	        Stage stage = event == null? 
	        		new Stage() : 
	        			((Stage) ((Node) event.getSource()).getScene().getWindow());
	        stage.setScene(scene);
	        stage.setTitle(title);
	        
	        //two methods to close previous windows
	        if (event != null) {
	        	((Node)(event.getSource())).getScene().getWindow().hide();
	        }
	        if (curSession.getCurrentStage() != null) {
	        	System.out.println("Clossing current stage!!");
	        	curSession.closeCurrentStage();
	        }

	        curSession.setCurrentStage(stage);
	        stage.show();
		} catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	
	private static void stageLoaderFunctionDontClosePrev(String fxmlFile, String title) {
		try {
	        FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(fxmlFile));
	        Scene scene = new Scene(loader.load());
	        Stage stage = new Stage();

	        stage.setScene(scene);
	        stage.setTitle(title);
	       
	        stage.show();
		} catch (Exception e) {
	        e.printStackTrace();
	    }
	}
	

	public static void loadMainDashboard() {
		stageLoaderFunction("/MainDashboard.fxml", null, "Pharmacy Management System - Dashboard");
	}

    public static void loadLoginScreen(ActionEvent event) {
    	stageLoaderFunction("/Login.fxml", event, "Pharmacy Management System - Login");
    }
    public static void loadLoginScreen() {
    	stageLoaderFunction("/Login.fxml", null, "Pharmacy Management System - Login");
    }
    
    public static void loadMedicationScreen(ActionEvent event) {
    	stageLoaderFunctionDontClosePrev("/MedicationInventory.fxml", "Medication");
    }
    
    public static void loadResetPasswordScreen(ActionEvent event) {
    	stageLoaderFunction("/ResetPassword.fxml", event, "Reset Password");
    }
    
    public void loadInventoryManagement(ActionEvent event) {
    	stageLoaderFunction("/InventoryManagement.fxml", event, "Inventory Management");
    }

    public void loadSalesTracking(ActionEvent event) {
    	stageLoaderFunction("/SalesTracking.fxml", event, "Sales Tracking");
    }

    public static void loadManagementInterface(ActionEvent event) {
    	stageLoaderFunction("/ManagementInterface.fxml", event, "Management Interface");
    }
    
    public static void loadTechDashboard(ActionEvent event) {
    	stageLoaderFunction("/TechDashboard.fxml", event, "Tech Dashboard");
    }

    public static void loadCashierDashboard(ActionEvent event) {
    	stageLoaderFunction("/CashierDashboard.fxml", event, "Cashier Dashboard");
    }

	public static void loadPrescriptionTracking(ActionEvent event) {
		stageLoaderFunction("/PrescriptionTracking.fxml", event, "Prescription Tracking");
	}
}