package UserInterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationUtil {

    public static void loadMainDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource("/MainDashboard.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadLoginScreen(ActionEvent event) {
    	System.out.println("Navigating to Login Screen...");
        try {
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource("/Login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void loadResetPasswordScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource("/ResetPassword.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Reset Password");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void loadInventoryManagement(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/InventoryManagement.fxml")); // Ensure the path is correct
            Stage stage = new Stage();
            stage.setTitle("Inventory Management");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadSalesTracking(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/SalesTracking.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Sales Tracking");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadManagementInterface(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource("/ManagementInterface.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Management Interface");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error: Unable to load Management Interface.");
        }
    }

	public static void loadPrescriptionTracking(ActionEvent event) {
	    try {
	        FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource("/PrescriptionTracking.fxml"));
	        Scene scene = new Scene(loader.load());
	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        stage.setScene(scene);
	        stage.setTitle("Prescription Tracking");
	    } catch (Exception e) {
	        e.printStackTrace();
	        System.err.println("Error: Unable to load Prescription Tracking.");
	    }
	}
}