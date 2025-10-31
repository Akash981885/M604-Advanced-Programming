package com.clinic.clinic_system.service;

import com.clinic.clinic_system.entity.Patient;
import com.clinic.clinic_system.repository.PatientRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // ===== CREATE =====
    @Transactional
    public Patient create(Patient p) {
        if (p.getEmail() != null && patientRepository.existsByEmail(p.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + p.getEmail());
        }
        return patientRepository.save(p);
    }

    // ===== READ BY ID =====
    @Transactional(readOnly = true)
    public Patient getById(Long id) {
        return patientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + id));
    }

    // ===== UPDATE =====
    @Transactional
    public Patient update(Long id, Patient updated) {
        Patient existing = getById(id);

        // Email uniqueness check for update
        if (updated.getEmail() != null &&
                !updated.getEmail().equals(existing.getEmail()) &&
                patientRepository.existsByEmail(updated.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + updated.getEmail());
        }

        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setDob(updated.getDob());
        existing.setGender(updated.getGender());
        existing.setPhone(updated.getPhone());
        existing.setEmail(updated.getEmail());
        existing.setAddress(updated.getAddress());

        return patientRepository.save(existing);
    }

    // ===== DELETE =====
    @Transactional
    public void delete(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new IllegalArgumentException("Patient not found: " + id);
        }
        patientRepository.deleteById(id);
    }

    // ===== SEARCH + PAGINATION =====
    @Transactional(readOnly = true)
    public Page<Patient> search(String q, Pageable pageable) {
        if (q == null || q.isBlank()) {
            return patientRepository.findAll(pageable);
        }
        return patientRepository
                .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(q, q, pageable);
    }
}
