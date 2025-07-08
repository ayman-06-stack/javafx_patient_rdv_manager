package controllers;

import daoImpl.PatientDAOImpl;
import daoImpl.RendezVousDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.RendezVous;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class RendezVousController {
    @FXML private TableColumn<RendezVous, String> colNom;
    @FXML private TableColumn<RendezVous, String> colPrenom;
    @FXML private TextField tfHeure;
    @FXML private DatePicker dpDate;
    @FXML private TextArea taDescription;
    @FXML private TextField tfPatientId;
    @FXML private TableView<RendezVous> tableRdv;
    @FXML private TableColumn<RendezVous, String> colDate;
    @FXML private TableColumn<RendezVous, String> colHeure;
    @FXML private TableColumn<RendezVous, String> colDescription;
    @FXML private TableColumn<RendezVous, String> colNomPrenom;
    @FXML private Label lblErreur; // Ajoute un label dans le FXML si besoin

    private final RendezVousDAOImpl rdvDAO = new RendezVousDAOImpl();
    private ObservableList<RendezVous> rdvList;

    @FXML
    public void initialize() {
        colNom.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNomPatient()));
        colPrenom.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getPrenomPatient()));
        colDate.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDateRdv().toString()));
        colHeure.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getHeureRdv().toString()));
        colDescription.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDescription()));
        colNomPrenom.setCellValueFactory(data -> {
            int patientId = data.getValue().getPatientId();
            PatientDAOImpl dao = new PatientDAOImpl();
            String nomComplet = "";
            try {
                var p = dao.trouverPatientParId(patientId);
                if (p != null) {
                    nomComplet = p.getNom() + " " + p.getPrenom();
                }
            } catch (Exception e) {
                nomComplet = "Inconnu";
            }
            return new javafx.beans.property.SimpleStringProperty(nomComplet);
        });

        // Charger tous les RDV au démarrage
        chargerTousLesRendezVous();

        tableRdv.setOnMouseClicked(e -> remplirChampsDepuisSelection());
    }


    private void chargerRdv(int patientId) {
        List<RendezVous> rdvs = rdvDAO.listerRdvParPatient(patientId);
        rdvList = FXCollections.observableArrayList(rdvs);
        tableRdv.setItems(rdvList);
    }

    @FXML
    private void rechercherRdv() {
        try {
            int patientId = Integer.parseInt(tfPatientId.getText());
            chargerRdv(patientId);
        } catch (NumberFormatException e) {
            afficherErreur("ID patient invalide.");
        }
    }

    @FXML
    private void ajouterRdv() {
        try {
            int patientId = Integer.parseInt(tfPatientId.getText());
            LocalDate date = dpDate.getValue();
            String heureTexte = tfHeure.getText().trim();
            if (!heureTexte.contains(":")) {
                heureTexte += ":00";
            }
            LocalTime heure = LocalTime.parse(heureTexte);
            String description = taDescription.getText();

            RendezVous r = new RendezVous();
            r.setPatientId(patientId);
            r.setDateRdv(date);
            r.setHeureRdv(heure);
            r.setDescription(description);

            rdvDAO.ajouterRdv(r);
            chargerRdv(patientId);
            viderChamps();
            afficherErreur("Rendez-vous ajouté avec succès.");
        } catch (Exception e) {
            e.printStackTrace();
            afficherErreur("Erreur lors de l'ajout du rendez-vous.");
        }
    }

    @FXML
    private void modifierRdv() {
        try {
            RendezVous r = tableRdv.getSelectionModel().getSelectedItem();
            if (r != null) {
                String heureTexte = tfHeure.getText().trim();
                if (!heureTexte.contains(":")) {
                    heureTexte += ":00";
                }
                r.setDateRdv(dpDate.getValue());
                r.setHeureRdv(LocalTime.parse(heureTexte));
                r.setDescription(taDescription.getText());

                rdvDAO.modifierRdv(r);
                chargerRdv(r.getPatientId());
                viderChamps();
                afficherErreur("Rendez-vous modifié.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            afficherErreur("Erreur lors de la modification.");
        }
    }

    @FXML
    private void supprimerRdv() {
        try {
            RendezVous r = tableRdv.getSelectionModel().getSelectedItem();
            if (r != null) {
                rdvDAO.supprimerRdv(r.getId());
                chargerRdv(r.getPatientId());
                viderChamps();
                afficherErreur("Rendez-vous supprimé.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            afficherErreur("Erreur lors de la suppression.");
        }
    }

    private void remplirChampsDepuisSelection() {
        RendezVous r = tableRdv.getSelectionModel().getSelectedItem();
        if (r != null) {
            tfPatientId.setText(String.valueOf(r.getPatientId()));
            dpDate.setValue(r.getDateRdv());
            tfHeure.setText(r.getHeureRdv().toString());
            taDescription.setText(r.getDescription());
        }
    }

    private void viderChamps() {
        dpDate.setValue(null);
        tfHeure.clear();
        taDescription.clear();
        lblErreur.setText("");
    }

    private void afficherErreur(String message) {
        if (lblErreur != null) {
            lblErreur.setText(message);
        } else {
            System.out.println(message);
        }
    }

    private void chargerTousLesRendezVous() {
        List<RendezVous> rdvs = rdvDAO.listerTous();
        rdvList = FXCollections.observableArrayList(rdvs);
        tableRdv.setItems(rdvList);
    }

}
