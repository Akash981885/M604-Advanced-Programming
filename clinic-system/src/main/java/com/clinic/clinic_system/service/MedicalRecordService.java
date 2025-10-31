package com.clinic.clinic_system.service;

import com.clinic.clinic_system.entity.MedicalRecord;
import com.clinic.clinic_system.entity.Patient;
import com.clinic.clinic_system.repository.MedicalRecordRepository;
import com.clinic.clinic_system.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.time.LocalDateTime;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;

    public MedicalRecordService(MedicalRecordRepository medicalRecordRepository,
                                PatientRepository patientRepository) {
        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
    }

    @Transactional
    public MedicalRecord create(Long patientId, MedicalRecord record) {
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + patientId));
        record.setPatient(patient);
        if (record.getRecordDate() == null) {
            record.setRecordDate(LocalDateTime.now());
        }
        return medicalRecordRepository.save(record);
    }

    @Transactional(readOnly = true)
    public MedicalRecord getById(Long id) {
        return medicalRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Medical record not found: " + id));
    }

    @Transactional(readOnly = true)
    public List<MedicalRecord> listByPatient(Long patientId) {
        return medicalRecordRepository.findByPatient_IdOrderByRecordDateDesc(patientId);
    }

    @Transactional
    public MedicalRecord update(Long id, MedicalRecord updated) {
        MedicalRecord existing = getById(id);

        if (updated.getRecordDate() != null) {
            existing.setRecordDate(updated.getRecordDate());
        }
        if (updated.getDiagnosis() != null) {
            existing.setDiagnosis(updated.getDiagnosis());
        }
        if (updated.getPrescription() != null) {
            existing.setPrescription(updated.getPrescription());
        }
        if (updated.getDescription() != null) {
            existing.setDescription(updated.getDescription());
        }
        if (updated.getType() != null) {
            existing.setType(updated.getType());
        }
        if (updated.getTitle() != null) {
            existing.setTitle(updated.getTitle());
        }
        if (updated.getAllergies() != null) {
            existing.setAllergies(updated.getAllergies());
        }
        if (updated.getDoctor() != null) {
            existing.setDoctor(updated.getDoctor());
        }

        return medicalRecordRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        if (!medicalRecordRepository.existsById(id)) {
            throw new IllegalArgumentException("Medical record not found: " + id);
        }
        medicalRecordRepository.deleteById(id);
    }
}
