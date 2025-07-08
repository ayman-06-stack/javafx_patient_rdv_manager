package dao;

import models.RendezVous;

import java.util.List;

public interface RendezVousDAO {
    void ajouterRdv(RendezVous r);
    void modifierRdv(RendezVous r);
    void supprimerRdv(int id);
    List<RendezVous> listerRdvParPatient(int patientId);
    RendezVous trouverRdvParId(int id);
}
