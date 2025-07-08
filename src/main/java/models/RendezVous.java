package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class RendezVous {
    private Integer id;
    private Integer patientId;
    private LocalDate dateRdv;
    private LocalTime heureRdv;
    private String description;
    private String nomPatient;
    private String prenomPatient;

    public RendezVous() {
    }


    public String getNomPatient() { return nomPatient; }
    public void setNomPatient(String nomPatient) { this.nomPatient = nomPatient; }

    public String getPrenomPatient() { return prenomPatient; }
    public void setPrenomPatient(String prenomPatient) { this.prenomPatient = prenomPatient; }


    public RendezVous(Integer id, Integer patientId, LocalDate dateRdv, LocalTime heureRdv, String description) {
        this.id = id;
        this.patientId = patientId;
        this.dateRdv = dateRdv;
        this.heureRdv = heureRdv;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public LocalDate getDateRdv() {
        return dateRdv;
    }

    public void setDateRdv(LocalDate dateRdv) {
        this.dateRdv = dateRdv;
    }

    public LocalTime getHeureRdv() {
        return heureRdv;
    }

    public void setHeureRdv(LocalTime heureRdv) {
        this.heureRdv = heureRdv;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}