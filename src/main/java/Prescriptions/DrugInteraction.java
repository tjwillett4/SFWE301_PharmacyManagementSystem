package Prescriptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	ArrayList<String> sideEffects;
	
	public DrugInteraction() {}
	public DrugInteraction(String name, ArrayList<String> sideEffects) {
		this.interactionWith = name;
		this.sideEffects = sideEffects;
	}
	public DrugInteraction(String name, String[] sideEffects) {
		this.interactionWith = name;
		this.sideEffects.addAll(Arrays.asList(sideEffects));
	}
	public DrugInteraction(String name, List<String> sideEffects) {
		this.interactionWith = name;
		this.sideEffects = new ArrayList<String>();
		this.sideEffects.addAll(sideEffects);
	}
	
	

	public ArrayList<String> getSideEffects() {
		return sideEffects;
	}
	public void setSideEffects(ArrayList<String> sideEffects) {
		this.sideEffects = sideEffects;
	}

	public String getInteractionWith() {
		return interactionWith;
	}


	public void setInteractionWith(String interactionWith) {
		this.interactionWith = interactionWith;
	}
}
