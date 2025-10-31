package com.clinic.clinic_system.repository;

import com.clinic.clinic_system.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {

    List<Doctor> findByActiveTrue();

    List<Doctor> findBySpecialityIgnoreCase(String speciality);

    boolean existsByEmail(String email);
}
