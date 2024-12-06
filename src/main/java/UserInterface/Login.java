package UserInterface;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {

	/**
	 * Main entry point for the Pharmacy Management System application.
	 * - Starts the JavaFX application and launches the login screen.
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
	    // Load the login screen as the initial interface
	    NavigationUtil.loadLoginScreen();
	}

	/**
	 * Main method to launch the application.
	 * - Calls the JavaFX launch method to initialize and start the GUI.
	 */
	public static void main(String[] args) {
	    launch(args);
	}
}
