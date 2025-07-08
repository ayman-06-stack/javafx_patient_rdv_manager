package dao;

import models.Patient;

import java.util.List;

public interface PatientDAO {
    void ajouterPatient(Patient p);
    void modifierPatient(Patient p);
    void supprimerPatient(int id);
    List<Patient> listerPatients();
    Patient trouverPatientParId(int id);


}
