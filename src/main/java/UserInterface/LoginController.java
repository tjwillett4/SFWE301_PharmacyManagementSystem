package UserInterface;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import java.nio.file.Path;
import java.util.ArrayList;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import BackEnd.FileHelper;
import BackEnd.Employee;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label errorMessage;

    @FXML
    private void handleLogin(ActionEvent event) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        try {
            Path employeeFile = FileHelper.findEmployeeFile();
            ArrayList<Employee> employees = readEmployees(employeeFile);

            Employee authenticatedEmployee = authenticate(username, password, employees);
            if (authenticatedEmployee != null) {
                //redirect to the main dashboard
                NavigationUtil.loadMainDashboard(event);
            } else {
                FileHelper.errorMessage("Login Failed", "Invalid username or password.");
            }
        } catch (Exception e) {
            FileHelper.errorMessage("Error", "An error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private ArrayList<Employee> readEmployees(Path employeeFile) throws Exception {
        if (employeeFile.toFile().length() == 0) {
            return new ArrayList<>(); //no employees exist
        }

        ObjectMapper mapper = new XmlMapper();
        return mapper.readValue(employeeFile.toFile(), new TypeReference<ArrayList<Employee>>() {});
    }

    private Employee authenticate(String username, String password, ArrayList<Employee> employees) {
        for (Employee employee : employees) {
            if (employee.getUsername().equals(username) && employee.getPassword().equals(password)) {
                return employee; //authentication successful
            }
        }
        return null; //authentication failed
    }
}