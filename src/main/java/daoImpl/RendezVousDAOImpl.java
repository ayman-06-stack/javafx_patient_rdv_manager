package daoImpl;

import dao.RendezVousDAO;
import models.RendezVous;
import utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RendezVousDAOImpl implements RendezVousDAO {

    @Override
    public void ajouterRdv(RendezVous r) {
        String sql = "INSERT INTO rendezvous (patient_id, date_rdv, heure_rdv, description) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, r.getPatientId());
            stmt.setDate(2, Date.valueOf(r.getDateRdv()));
            stmt.setTime(3, Time.valueOf(r.getHeureRdv()));
            stmt.setString(4, r.getDescription());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modifierRdv(RendezVous r) {
        String sql = "UPDATE rendezvous SET date_rdv = ?, heure_rdv = ?, description = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(r.getDateRdv()));
            stmt.setTime(2, Time.valueOf(r.getHeureRdv()));
            stmt.setString(3, r.getDescription());
            stmt.setInt(4, r.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerRdv(int id) {
        String sql = "DELETE FROM rendezvous WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<RendezVous> listerRdvParPatient(int patientId) {
        List<RendezVous> list = new ArrayList<>();
        String sql = """
                SELECT r.*, p.nom AS nom_patient, p.prenom AS prenom_patient
                FROM rendezvous r
                JOIN patients p ON r.patient_id = p.id
                WHERE r.patient_id = ?
            """;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, patientId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                RendezVous r = new RendezVous();
                r.setNomPatient(rs.getString("nom_patient"));
                r.setPrenomPatient(rs.getString("prenom_patient"));
                r.setId(rs.getInt("id"));
                r.setPatientId(rs.getInt("patient_id"));
                r.setDateRdv(rs.getDate("date_rdv").toLocalDate());
                r.setHeureRdv(rs.getTime("heure_rdv").toLocalTime());
                r.setDescription(rs.getString("description"));
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public RendezVous trouverRdvParId(int id) {
        String sql = "SELECT * FROM rendezvous WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                RendezVous r = new RendezVous();
                r.setId(rs.getInt("id"));
                r.setPatientId(rs.getInt("patient_id"));
                r.setDateRdv(rs.getDate("date_rdv").toLocalDate());
                r.setHeureRdv(rs.getTime("heure_rdv").toLocalTime());
                r.setDescription(rs.getString("description"));
                return r;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<RendezVous> listerTous() {
        List<RendezVous> list = new ArrayList<>();
        String sql = """
        SELECT r.*, p.nom AS nom_patient, p.prenom AS prenom_patient
        FROM rendezvous r
        JOIN patients p ON r.patient_id = p.id
        ORDER BY r.date_rdv DESC
    """;
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RendezVous r = new RendezVous();
                r.setId(rs.getInt("id"));
                r.setPatientId(rs.getInt("patient_id"));
                r.setDateRdv(rs.getDate("date_rdv").toLocalDate());
                r.setHeureRdv(rs.getTime("heure_rdv").toLocalTime());
                r.setDescription(rs.getString("description"));
                r.setNomPatient(rs.getString("nom_patient"));
                r.setPrenomPatient(rs.getString("prenom_patient"));
                list.add(r);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
