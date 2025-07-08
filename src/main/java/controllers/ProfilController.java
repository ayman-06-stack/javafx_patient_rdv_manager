package controllers;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Utilisateur;
import utils.AuthService;
import utils.SessionManager;

public class ProfilController {

    @FXML private Label lblNom;
    @FXML private Label lblEmail;
    @FXML private PasswordField pfAncien;
    @FXML private PasswordField pfNouveau;
    @FXML private PasswordField pfConfirmation;
    @FXML private Label lblMessage;

    private Utilisateur utilisateur;

    @FXML
    public void initialize() {
        utilisateur = SessionManager.getUtilisateurConnecte();

        if (utilisateur != null) {
            lblNom.setText(utilisateur.getNom());
            lblEmail.setText(utilisateur.getEmail());
        }
    }

    @FXML
    private void changerMotDePasse() {
        String ancien = pfAncien.getText();
        String nouveau = pfNouveau.getText();
        String confirmation = pfConfirmation.getText();

        if (!ancien.equals(utilisateur.getMotDePasse())) {
            lblMessage.setText("Ancien mot de passe incorrect.");
            lblMessage.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        if (!nouveau.equals(confirmation)) {
            lblMessage.setText("Les mots de passe ne correspondent pas.");
            lblMessage.setTextFill(javafx.scene.paint.Color.RED);
            return;
        }

        // Mise à jour dans la base
        boolean success = AuthService.mettreAJourMotDePasse(utilisateur.getId(), nouveau);

        if (success) {
            lblMessage.setText("Mot de passe mis à jour avec succès !");
            lblMessage.setTextFill(javafx.scene.paint.Color.GREEN);
            utilisateur.setMotDePasse(nouveau);
        } else {
            lblMessage.setText("Erreur lors de la mise à jour.");
            lblMessage.setTextFill(javafx.scene.paint.Color.RED);
        }
    }
}

