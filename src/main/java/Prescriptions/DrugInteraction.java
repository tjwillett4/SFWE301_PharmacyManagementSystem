package Prescriptions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents a drug or allergy interaction tied to a base medication.
 * Stores information about potential interactions and their side effects.
 */
public class DrugInteraction {
    private String interactionWith; // The drug or allergy causing interaction
    private ArrayList<String> sideEffects; // List of possible side effects

    // Default constructor
    public DrugInteraction() {
        this.interactionWith = "";
        this.sideEffects = new ArrayList<>();
    }

    // Constructor with name and side effects as ArrayList
    public DrugInteraction(String interactionWith, ArrayList<String> sideEffects) {
        if (interactionWith == null || sideEffects == null) {
            throw new IllegalArgumentException("InteractionWith and sideEffects cannot be null.");
        }
        this.interactionWith = interactionWith;
        this.sideEffects = new ArrayList<>(sideEffects);
    }

    // Constructor with name and side effects as Array
    public DrugInteraction(String interactionWith, String[] sideEffects) {
        if (interactionWith == null || sideEffects == null) {
            throw new IllegalArgumentException("InteractionWith and sideEffects cannot be null.");
        }
        this.interactionWith = interactionWith;
        this.sideEffects = new ArrayList<>(Arrays.asList(sideEffects));
    }

    // Constructor with name and side effects as List
    public DrugInteraction(String interactionWith, List<String> sideEffects) {
        if (interactionWith == null || sideEffects == null) {
            throw new IllegalArgumentException("InteractionWith and sideEffects cannot be null.");
        }
        this.interactionWith = interactionWith;
        this.sideEffects = new ArrayList<>(sideEffects);
    }

    // Getter for side effects
    public ArrayList<String> getSideEffects() {
        return new ArrayList<>(sideEffects); // Return a copy for immutability
    }

    // Setter for side effects
    public void setSideEffects(ArrayList<String> sideEffects) {
        if (sideEffects == null) {
            throw new IllegalArgumentException("SideEffects cannot be null.");
        }
        this.sideEffects = new ArrayList<>(sideEffects);
    }

    // Getter for interactionWith
    public String getInteractionWith() {
        return interactionWith;
    }

    // Setter for interactionWith
    public void setInteractionWith(String interactionWith) {
        if (interactionWith == null) {
            throw new IllegalArgumentException("InteractionWith cannot be null.");
        }
        this.interactionWith = interactionWith;
    }

    // Utility method to check if a specific side effect exists
    public boolean hasSideEffect(String sideEffect) {
        return sideEffects.contains(sideEffect);
    }

    // Override toString for better debugging
    @Override
    public String toString() {
        return "DrugInteraction{" +
                "interactionWith='" + interactionWith + '\'' +
                ", sideEffects=" + sideEffects +
                '}';
    }
}