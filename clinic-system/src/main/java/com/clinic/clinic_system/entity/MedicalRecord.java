package com.clinic.clinic_system.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "medical_records")
public class MedicalRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to patient (required)
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    // Optional authoring doctor
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    // Date/time of the record
    @Column(name = "record_date", nullable = false)
    private LocalDateTime recordDate = LocalDateTime.now();

    // Classification / type of record (e.g., "Visit", "Prescription", "Lab")
    private String type;

    // Short title or summary
    private String title;

    // Full textual description (long)
    @Column(length = 2000)
    private String description;

    // Diagnosis text
    private String diagnosis;

    // Prescription text (single field)
    private String prescription;

    // Any reported allergies
    private String allergies;

    // Constructors
    public MedicalRecord() {}

    public MedicalRecord(Long id, Patient patient, Doctor doctor, LocalDateTime recordDate, String type,
                         String title, String description, String diagnosis, String prescription, String allergies) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.recordDate = recordDate;
        this.type = type;
        this.title = title;
        this.description = description;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
        this.allergies = allergies;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Patient getPatient() { return patient; }
    public void setPatient(Patient patient) { this.patient = patient; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public LocalDateTime getRecordDate() { return recordDate; }
    public void setRecordDate(LocalDateTime recordDate) { this.recordDate = recordDate; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDiagnosis() { return diagnosis; }
    public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

    public String getPrescription() { return prescription; }
    public void setPrescription(String prescription) { this.prescription = prescription; }

    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }
}
