package com.clinic.clinic_system.repository;

import com.clinic.clinic_system.entity.MedicalRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {

    /**
     * Find all medical records for a patient ordered newest first.
     */
    List<MedicalRecord> findByPatient_IdOrderByRecordDateDesc(Long patientId);

    /**
     * Find records for a patient filtered by type, newest first.
     */
    List<MedicalRecord> findByPatient_IdAndTypeOrderByRecordDateDesc(Long patientId, String type);

    /**
     * Convenience: all records ordered by date (admin view).
     */
    List<MedicalRecord> findAllByOrderByRecordDateDesc();
}
