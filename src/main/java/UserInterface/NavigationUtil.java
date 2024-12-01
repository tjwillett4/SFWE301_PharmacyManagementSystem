package UserInterface;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NavigationUtil {

    public static void loadMainDashboard(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource("/fxml/MainDashboard.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadLoginScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource("/fxml/Login.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void loadResetPasswordScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource("/fxml/ResetPassword.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Reset Password");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}