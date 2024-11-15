package BackEnd;

import java.util.List;

//account info object.

/*
 * The system shall hold account information for any role in the company, customer, pharmacy tech, pharmacist, etc. 
 * The type of role may be inherited with additional permissions, security measures, and information, 
 * but the basis is the "Account" object. (Stored on backend, accessed via company security measures) 
 */

public class Account {
	//basic account info. 
	private String username;
	private String nameFirst, nameMid, nameLast;
	private String phoneNum;
	private String address;
	private String password;
	private Role accountRole;
	
	//Encryption keys, required for Password based encryption (PBE):
	//certain roles will have this filled. gives read permissions.  
	private String accountsPasscode = "";
	
	
	
	//pharmacy specific account info. 
	private List<String> allergies;
	//List<Medications> medications
	//Insurance insuranceInfo
	//
	
	public Account() {}
	

	
	public String getNameFirst() {
		return nameFirst;
	}

	public void setNameFirst(String nameFirst) {
		this.nameFirst = nameFirst;
	}

	public String getNameMid() {
		return nameMid;
	}

	public void setNameMid(String nameMid) {
		this.nameMid = nameMid;
	}

	public String getNameLast() {
		return nameLast;
	}

	public void setNameLast(String nameLast) {
		this.nameLast = nameLast;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<String> getAllergies() {
		return allergies;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
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
	
	
	
	
	
}

