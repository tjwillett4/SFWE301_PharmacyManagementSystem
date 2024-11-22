package BackEnd;

public class Employee extends Account{
	private String username;
	private String password;
	private Role accountRole;
	
	//Encryption keys, required for Password based encryption (PBE):
	//personal encryption for password
	private String secCodePass = "";
	private String secCodeAns = "";
	
	//to access databases
	private String accountsPasscode = "";
	
	public Employee() {};
	
	public Employee(String firstName, String user, String p, String secPass) {
		this.setNameFirst(firstName);
		this.setUsername(user);
		this.setPassword(p);
		this.setSecCodePass(p);
	}
	

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

	public String getSecCodePass() {
		return secCodePass;
	}

	public void setSecCodePass(String secCodePass) {
		this.secCodePass = secCodePass;
	}

	public String getSecCodeAns() {
		return secCodeAns;
	}

	public void setSecCodeAns(String secCodeAns) {
		this.secCodeAns = secCodeAns;
	}

	public String getAccountsPasscode() {
		return accountsPasscode;
	}

	public void setAccountsPasscode(String accountsPasscode) {
		this.accountsPasscode = accountsPasscode;
	}
	
	
	
	
}
