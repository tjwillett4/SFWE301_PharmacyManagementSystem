package BackEnd;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true) // Ignore any unrecognized fields
public class Employee extends Account {
    private String username;
    private String password;
    private String contactInfo;
    private Role accountRole;
    private int loginAttempts;

    // Default constructor
    public Employee() {
        this.loginAttempts = 0; // Initialize login attempts to 0
    }

    // Constructor with required fields
    public Employee(String username, String password, Role accountRole) {
        this.username = username;
        this.password = password;
        this.accountRole = accountRole;
        this.loginAttempts = 0; // Initialize login attempts to 0
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

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

	public void setSecCodePass(String password2) {
		// TODO Auto-generated method stub
		
	}
	
	public String getContactInfo() {
	    return contactInfo;
	}

	public void setContactInfo(String contactInfo) {
	    this.contactInfo = contactInfo;
	}

	public String getSecCodePass() {
		// TODO Auto-generated method stub
		return null;
	}
}