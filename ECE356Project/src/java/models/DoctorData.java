package models;

import ece356.*;
import java.util.ArrayList;
import java.util.List;
import models.Patient;

public class DoctorData {

    ArrayList<Patient> patients = new ArrayList<Patient>();

    public ArrayList<Patient> getPatients() {
        return patients;
    }

    public void addPatient(Patient value) {
        patients.add(value);
    }
}