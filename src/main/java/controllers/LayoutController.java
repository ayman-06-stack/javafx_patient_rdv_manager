package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.Node;
import javafx.stage.Stage;
import utils.SessionManager;

import java.io.IOException;

public class LayoutController {

    @FXML
    private AnchorPane mainContent;

    @FXML
    private void initialize() {
        goDashboard(); // page par défaut
    }

    @FXML
    private void goDashboard() {
        System.out.println("Chargement Dashboard...");
        setContent("/views/dashboard.fxml");
    }

    @FXML
    private void goPatients() {
        setContent("/views/patients.fxml");
    }

    @FXML
    private void goRendezVous() {
        setContent("/views/rendezvous.fxml");
    }


    @FXML
    private void logout() throws IOException {
        SessionManager.deconnecter();

        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        Stage stage = (Stage) mainContent.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle("Connexion");
    }


    private void setContent(String fxmlPath) {
        try {
            Node content = FXMLLoader.load(getClass().getResource(fxmlPath));
            mainContent.getChildren().setAll(content);

            // Bien ancrer tous les bords pour qu'il prenne toute la place
            AnchorPane.setTopAnchor(content, 0.0);
            AnchorPane.setBottomAnchor(content, 0.0);
            AnchorPane.setLeftAnchor(content, 0.0);
            AnchorPane.setRightAnchor(content, 0.0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goProfil() {
        setContent("/views/profil.fxml");
    }


}

