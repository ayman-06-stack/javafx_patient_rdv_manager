package controllers;


import daoImpl.PatientDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Patient;

import java.time.LocalDate;
import java.util.List;

public class PatientController {

    @FXML private TextField tfNom;
    @FXML private TextField tfPrenom;
    @FXML private DatePicker dpDateNaissance;
    @FXML private TextField tfTelephone;
    @FXML private TextField tfEmail;
    @FXML private TableView<Patient> tablePatients;
    @FXML private TableColumn<Patient, String> colId;
    @FXML private TableColumn<Patient, String> colNom;
    @FXML private TableColumn<Patient, String> colPrenom;
    @FXML private TableColumn<Patient, String> colTelephone;
    @FXML private TableColumn<Patient, String> colEmail;

    private final PatientDAOImpl patientDAO = new PatientDAOImpl();
    private ObservableList<Patient> patientList;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(String.valueOf(data.getValue().getId())));
        colNom.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNom()));
        colPrenom.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPrenom()));
        colTelephone.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTelephone()));
        colEmail.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getEmail()));

        chargerPatients();

        tablePatients.setOnMouseClicked(e -> remplirChampsDepuisSelection());
    }

    private void chargerPatients() {
        List<Patient> patients = patientDAO.listerPatients();
        patientList = FXCollections.observableArrayList(patients);
        tablePatients.setItems(patientList);
    }

    @FXML
    private void ajouterPatient() {
        Patient p = new Patient();
        p.setNom(tfNom.getText());
        p.setPrenom(tfPrenom.getText());
        p.setDateNaissance(dpDateNaissance.getValue());
        p.setTelephone(tfTelephone.getText());
        p.setEmail(tfEmail.getText());

        patientDAO.ajouterPatient(p);
        chargerPatients();
        viderChamps();
    }

    @FXML
    private void modifierPatient() {
        Patient p = tablePatients.getSelectionModel().getSelectedItem();
        if (p != null) {
            p.setNom(tfNom.getText());
            p.setPrenom(tfPrenom.getText());
            p.setDateNaissance(dpDateNaissance.getValue());
            p.setTelephone(tfTelephone.getText());
            p.setEmail(tfEmail.getText());

            patientDAO.modifierPatient(p);
            chargerPatients();
            viderChamps();
        }
    }

    @FXML
    private void supprimerPatient() {
        Patient p = tablePatients.getSelectionModel().getSelectedItem();
        if (p != null) {
            patientDAO.supprimerPatient(p.getId());
            chargerPatients();
            viderChamps();
        }
    }

    private void remplirChampsDepuisSelection() {
        Patient p = tablePatients.getSelectionModel().getSelectedItem();
        if (p != null) {
            tfNom.setText(p.getNom());
            tfPrenom.setText(p.getPrenom());
            dpDateNaissance.setValue(p.getDateNaissance());
            tfTelephone.setText(p.getTelephone());
            tfEmail.setText(p.getEmail());
        }
    }

    private void viderChamps() {
        tfNom.clear();
        tfPrenom.clear();
        dpDateNaissance.setValue(null);
        tfTelephone.clear();
        tfEmail.clear();
    }

    @FXML private TextField tfRecherche;

    @FXML
    private void rechercherPatients() {
        String motCle = tfRecherche.getText().toLowerCase();
        List<Patient> patients = patientDAO.listerPatients(); // Tu peux optimiser si tu veux

        List<Patient> filtres = patients.stream()
                .filter(p -> p.getNom().toLowerCase().contains(motCle) || p.getPrenom().toLowerCase().contains(motCle))
                .toList();

        patientList.setAll(filtres);
    }

}

