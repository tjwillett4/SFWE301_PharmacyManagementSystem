package BackEnd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignore any unrecognized fields
public class Employee extends Account {
    private String username = "";
    private String password = "";
    private String contactInfo = "";
    private Role accountRole;
    private int loginAttempts = 0;
    private String secCodePass = "";

    // Default constructor
    public Employee() {
        this.loginAttempts = 0; // Initialize login attempts to 0
    }
    public Employee(String username) {
    	this.username = username;
    }
    
    // Constructor with required fields
    public Employee(String username, String password, Role accountRole) {
        this.username = username;
        this.password = password;
        this.accountRole = accountRole;
        this.loginAttempts = 0; // Initialize login attempts to 0
        try {
			this.secCodePass = Serializer.generateSerializerString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //String used for password encryption. 
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return username.equals(employee.username);
    }

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getAccountRole() {
        return accountRole;
    }

    public void setAccountRole(Role accountRole) {
        this.accountRole = accountRole;
    }
    
	public static Role roleFromString(String r) {
		if (r.equals(Role.Customer.toString()))
	    	return Role.Customer;
		if (r.equals(Role.Cashier.toString()))
    		return Role.Cashier;
		if (r.equals(Role.pharmacyTech.toString()))
    		return Role.pharmacyTech;
		if (r.equals(Role.pharmacist.toString()))
    		return Role.pharmacist;
		if (r.equals(Role.pharmacyManager.toString()))
    		return Role.pharmacyManager;
		if (r.equals(Role.doctor.toString()))
    		return Role.doctor;
		else 
    		return Role.Cashier;

    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

	public void setSecCodePass(String secCodePass) {
		this.secCodePass = secCodePass;
		
	}
	
	public String getContactInfo() {
	    return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
	    this.contactInfo = contactInfo;
	}

	public String getSecCodePass() {
		return secCodePass;
	}
}