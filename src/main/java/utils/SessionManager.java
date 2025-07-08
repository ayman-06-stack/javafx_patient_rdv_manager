package utils;

import models.Utilisateur;

public class SessionManager {

    private static Utilisateur utilisateurActif;

    public static void connecter(Utilisateur utilisateur) {
        SessionManager.utilisateurActif = utilisateur;
    }

    public static Utilisateur getUtilisateurConnecte() {
        return utilisateurActif;
    }

    public static void deconnecter() {
        utilisateurActif = null;
    }

    public static boolean estConnecte() {
        return utilisateurActif != null;
    }
}
