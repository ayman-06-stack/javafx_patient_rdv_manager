package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.event.ActionEvent;

public class DashboardController {

    @FXML
    private void ouvrirGestionPatients(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/patients.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestion des Patients");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ouvrirGestionRendezVous(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/rendezvous.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Gestion des Rendez-vous");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void seDeconnecter(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Connexion");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
