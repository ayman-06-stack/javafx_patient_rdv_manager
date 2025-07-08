package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import models.Utilisateur;
import utils.AuthService;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import utils.PasswordUtils;

import java.awt.*;

import static java.awt.Color.*;

public class RegisterController {

    @FXML private TextField tfNom;
    @FXML private TextField tfEmail;
    @FXML private PasswordField pfPassword;
    @FXML private Label lblInfo;

    @FXML
    private void sInscrire() {
        String nom = tfNom.getText().trim();
        String email = tfEmail.getText().trim();
        String password = pfPassword.getText().trim();

        if (nom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            lblInfo.setText("Veuillez remplir tous les champs");
            return;
        }

        Utilisateur u = new Utilisateur();
        u.setNom(nom);
        u.setEmail(email);
        u.setMotDePasse(PasswordUtils.hashPassword(password));

        boolean success = AuthService.register(u);

        if (success) {
            lblInfo.setText("Inscription réussie. Veuillez vous connecter.");
        } else {
            lblInfo.setText("Erreur : Email déjà utilisé ou autre problème.");
        }
    }


    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void retourConnexion() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
            Stage stage = (Stage) tfEmail.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Connexion");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

