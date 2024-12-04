package BackEnd;

//account info object.

/*
 * The system shall hold account information for any role in the company, customer, pharmacy tech, pharmacist, etc. 
 * The type of role may be inherited with additional permissions, security measures, and information, 
 * but the basis is the "Account" object. (Stored on backend, accessed via company security measures) 
 */

public class Account {
	//basic account info. 
	private String nameFirst, nameMid, nameLast;

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
	
	
	
}
