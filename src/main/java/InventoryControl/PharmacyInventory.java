package InventoryControl;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InaccessibleObjectException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import BackEnd.FileHelper;
import Prescriptions.Medication;

public class PharmacyInventory {

    /* 
     * ADD NEW MEDICATION 
     */
    public static void addMedication(Medication med) throws Exception {
        writeMedication(med);
    }

    /* 
     * RETRIEVE MEDICATION 
     */
    public static Medication retrieveMedication(String name) throws Exception {
        ArrayList<Medication> medList = readPharmacyInventory();
        for (Medication med : medList) {
            if (med.getName().equals(name)) {
                return med;
            }
        }
        return null;
    }

    /* 
     * UPDATE INVENTORY 
     */
    public static void updateStock(String medicationName, int stockToAdd) throws Exception {
        ArrayList<Medication> medications = readPharmacyInventory();

        boolean medicationFound = false;
        for (Medication med : medications) {
            if (med.getName().equalsIgnoreCase(medicationName)) {
                BatchMedication newBatch = new BatchMedication(medicationName, stockToAdd, LocalDate.now().plusMonths(6));
                med.setSingleBatch(newBatch); // Adds or updates the batch
                medicationFound = true;
                break;
            }
        }

        if (!medicationFound) {
            throw new Exception("Medication not found in inventory: " + medicationName);
        }

        writeMedication(medications);
    }

    /* 
     * MODIFY EXISTING MEDICATION
     */
    public static void updateMedication(Medication med) throws Exception {
        ArrayList<Medication> medList = readPharmacyInventory();

        for (Medication m : medList) {
            if (m.getName().equals(med.getName())) {
                medList.set(medList.indexOf(m), med);
                break;
            }
        }
        writeMedication(medList);
    }

    /* 
     * FILE READING AND WRITING 
     */
    private static void writeMedication(Medication med) throws Exception {
        Path p = FileHelper.findPharmacyInventoryFile();
        ObjectMapper mapper = new XmlMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        ArrayList<Medication> medications = readPharmacyInventory();
        medications.add(med);

        mapper.writeValue(Files.newOutputStream(p), medications);
    }

    private static void writeMedication(ArrayList<Medication> medications) throws Exception {
        Path p = FileHelper.findPharmacyInventoryFile();
        ObjectMapper mapper = new XmlMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

        mapper.writeValue(Files.newOutputStream(p), medications);
    }

    public static ArrayList<Medication> readPharmacyInventory() throws Exception {
    	Path p = FileHelper.findPharmacyInventoryFile();
        if (!Files.exists(p)) {
            throw new Exception("Cannot load settings because the settings file path could not be loaded.");
        }

        if (p.toFile().length() == 0) {
            return new ArrayList<>();
        }

        try {
            ObjectMapper mapper = new XmlMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            TypeReference<ArrayList<Medication>> typeRef = new TypeReference<>() {};
            return mapper.readValue(Files.newInputStream(p), typeRef);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new Exception("Failed to find the settings file.");
        } catch (UnrecognizedPropertyException e) {
            e.printStackTrace();
            throw new Exception("Unrecognized properties found or missing in the selected settings file.");
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Failed to open or close the desired settings file." + e);
        } catch (InaccessibleObjectException e) {
            throw new Exception("Tried to load inaccessible object from settings... " + e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong while trying to load the selected settings file.");
        }
    }
}