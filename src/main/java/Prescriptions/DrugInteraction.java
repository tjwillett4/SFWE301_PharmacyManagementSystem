package Prescriptions;

/*
 * This object will store drug or allergy interactions, tied to the base medication. 
 * "interactionWith" is the drug or allergy to be concerned with.
 * "sideEffects" lists the side effect. 
 * 
 * PHARMT5-PRES-3
 * The system shall use medication database to automatically find any interaction between drugs that a customer 
 * is taking or has been prescribed previously. Also checks for allergies and other areas of worry regarding 
 * the customer. 
 */

public class DrugInteraction {
	String interactionWith;
	String sideEffects;
	
	public DrugInteraction(String interactionWith, String sideEffects) {
		this.interactionWith = interactionWith;
		this.sideEffects = sideEffects;
	}

	public String getInteractionWith() {
		return interactionWith;
	}

	public void setInteractionWith(String interactionWith) {
		this.interactionWith = interactionWith;
	}

	public String getSideEffects() {
		return sideEffects;
	}

	public void setSideEffects(String sideEffects) {
		this.sideEffects = sideEffects;
	}
	
	
	
}
