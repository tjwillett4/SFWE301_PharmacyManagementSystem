package com.sfwe.pharmacy;

import BackEnd.Employee;
import BackEnd.AccountHandling;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        
        // INITIAL ACCOUNT comment out after initial attempt. 
        //String firstName, String user, String p, String secPass
        /*Employee test = new Employee("Bob", "bob", "pass", "pass");
        
        try {
        	//add account the database. 
			AccountHandling.addEmployeeAccount(test);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to add employee because: " + e);
			e.printStackTrace();
		}*/
        
        //CHECK TO SEE IF IT EXISTS:
		System.out.println("Does Bob now exist in the system?");
		try {
			if (AccountHandling.employeeExists("bob")) {
				System.out.println("Bob does exist in the new system");
				
			}
			else
				System.out.println("Bob does NOT exist in the system.");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to check employee database because: " + e);
			e.printStackTrace();
		}
        
        
        //CHECK IF INITIAL ACCOUNT WAS CREATED BY LOGGING IN AND PRINTING INFO. 
		
		try {
			Employee emp = AccountHandling.logIn("bob", "pass");
			
			System.out.println("Just retrieved Bob's account from the database!");
			System.out.println(emp.getNameFirst() + ", " + emp.getUsername() + ", " + emp.getPassword());
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Unable to check log in because: " + e);
			e.printStackTrace();
		}
        
    }
}
