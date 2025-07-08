package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import models.Utilisateur;
import utils.AuthService;
import utils.SessionManager;

import java.io.IOException;

public class LoginController {

    @FXML private TextField tfEmail;
    @FXML private PasswordField pfPassword;
    @FXML private Label lblErreur;

    @FXML
    private void seConnecter() throws IOException {
        String email = tfEmail.getText();
        String password = pfPassword.getText();

        Utilisateur user = AuthService.login(email, password);

        if (user != null) {
            SessionManager.connecter(user); // 🟢 Sauvegarder l'utilisateur

            Parent root = FXMLLoader.load(getClass().getResource("/views/layout.fxml"));
            Stage stage = (Stage) tfEmail.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Clinique - Tableau de bord");
        } else {
            lblErreur.setText("Email ou mot de passe incorrect.");
        }
    }


    @FXML
    private void allerVersInscription() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/register.fxml"));
            Stage stage = (Stage) tfEmail.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Créer un compte");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
