package UserInterface;

import BackEnd.Employee;
import javafx.stage.Stage;

public class Session {
    private static Employee currentUser;
    private static Stage currentStage;
    
    public Session() {}

    public static Employee getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Employee user) {
        currentUser = user;
    }

    public static void clear() {
        currentUser = null;
    }

	public static Stage getCurrentStage() {
		return currentStage;
	}

	public static void setCurrentStage(Stage stage) {
		currentStage = stage;
	}
    
	public static void closeCurrentStage() {
		currentStage.close();
	}
    
}