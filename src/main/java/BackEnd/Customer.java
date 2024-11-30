package BackEnd;

import java.util.List;

public class Customer extends Account{
	private String phoneNum;
	private String address;
	
	//pharmacy specific account info. 
	private List<String> allergies;
	//List<Medications> medications
	//Insurance insuranceInfo
	//

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

	
	
}
