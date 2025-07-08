package utils;

import models.Utilisateur;
import java.sql.*;

public class AuthService {

    public static boolean register(Utilisateur u) {
        if (emailExists(u.getEmail())) {
            return false;
        }

        String sql = "INSERT INTO utilisateurs (nom, email, mot_de_passe) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, u.getNom());
            stmt.setString(2, u.getEmail());
            stmt.setString(3, u.getMotDePasse());  // Le mot de passe est déjà haché dans RegisterController

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        u.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Erreur d'inscription : " + e.getMessage());
        }
        return false;
    }

    public static Utilisateur login(String email, String motDePasse) {
        System.out.println("Tentative de connexion avec: " + email);

        String sql = "SELECT * FROM utilisateurs WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("Utilisateur trouvé dans la base de données");
                String storedPassword = rs.getString("mot_de_passe");

                // Utilisez PasswordUtils pour hasher le mot de passe saisi et comparer
                String hashedPassword = PasswordUtils.hashPassword(motDePasse);
                System.out.println("Mot de passe stocké: " + storedPassword);
                System.out.println("Mot de passe saisi haché: " + hashedPassword);

                if (hashedPassword.equals(storedPassword)) {
                    System.out.println("Mots de passe correspondent - Connexion réussie");

                    Utilisateur u = new Utilisateur();
                    u.setId(rs.getInt("id"));
                    u.setNom(rs.getString("nom"));
                    u.setEmail(rs.getString("email"));
                    u.setMotDePasse(storedPassword);
                    return u;
                } else {
                    System.out.println("Mots de passe ne correspondent pas");
                }
            } else {
                System.out.println("Aucun utilisateur trouvé avec cet email");
            }

        } catch (SQLException e) {
            System.out.println("Erreur SQL lors de la connexion: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public static boolean mettreAJourMotDePasse(int idUtilisateur, String nouveauMotDePasse) {
        String sql = "UPDATE utilisateurs SET mot_de_passe = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Ajouter le hash pour le nouveau mot de passe
            stmt.setString(1, PasswordUtils.hashPassword(nouveauMotDePasse));
            stmt.setInt(2, idUtilisateur);
            int rows = stmt.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.out.println("Erreur update mot de passe : " + e.getMessage());
            return false;
        }
    }

    public static boolean emailExists(String email) {
        String sql = "SELECT COUNT(*) FROM utilisateurs WHERE email = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}