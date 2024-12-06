package UserInterface;

import javafx.application.Application;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
    	
    	NavigationUtil.loadLoginScreen();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
