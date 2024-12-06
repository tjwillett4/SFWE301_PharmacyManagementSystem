package BackEnd;

import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import Prescriptions.Prescription;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class PrescriptionHandling {

    /**
     * Reads all prescriptions from storage.
     *
     * @return A list of all prescriptions.
     * @throws Exception If there's an error reading the data.
     */
    public static ArrayList<Prescription> readPrescriptions() throws Exception {
        Path prescriptionFile = FileHelper.findPrescriptionFile();

        if (!Files.exists(prescriptionFile) || prescriptionFile.toFile().length() == 0) {
            return new ArrayList<>();
        }

        XmlMapper mapper = FileHelper.createReadMapper();

        return mapper.readValue(
                Files.newInputStream(prescriptionFile),
                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, Prescription.class)
        );
    }

    /**
     * Writes all prescriptions to storage.
     *
     * @param prescriptions The list of prescriptions to save.
     * @throws Exception If there's an error writing the data.
     */
    public static void writePrescriptions(ArrayList<Prescription> prescriptions) throws Exception {
        Path prescriptionFile = FileHelper.findPrescriptionFile();

        XmlMapper mapper = FileHelper.createWriteMapper();
        mapper.writeValue(Files.newOutputStream(prescriptionFile), prescriptions);
    }

    /**
     * Adds a new prescription to the system.
     *
     * @param prescription The prescription to add.
     * @throws Exception If there's an error saving the data.
     */
    public static void addPrescription(Prescription prescription) throws Exception {
        ArrayList<Prescription> prescriptions = readPrescriptions();
        prescriptions.add(prescription);
        writePrescriptions(prescriptions);
    }

    /**
     * Updates an existing prescription in storage.
     *
     * @param prescription The updated prescription.
     * @throws Exception If there's an error updating the data.
     */
    public static void updatePrescription(Prescription prescription) throws Exception {
        ArrayList<Prescription> prescriptions = readPrescriptions();

        for (int i = 0; i < prescriptions.size(); i++) {
            if (prescriptions.get(i).getId() == prescription.getId()) {
                prescriptions.set(i, prescription);
                break;
            }
        }

        writePrescriptions(prescriptions);
    }

    public static int generateUniqueId() {
        // Replace this with actual logic to generate a unique ID
        return (int) (Math.random() * 100000);
    }
}