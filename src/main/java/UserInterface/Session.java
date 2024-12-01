package UserInterface;

import BackEnd.Employee;

public class Session {
    private static Employee currentUser;

    public static Employee getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(Employee user) {
        currentUser = user;
    }

    public static void clear() {
        currentUser = null;
    }
}