package com.clinic.clinic_system.service;

import com.clinic.clinic_system.entity.Doctor;
import com.clinic.clinic_system.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public Doctor create(Doctor d) {
        if (d.getEmail() != null && doctorRepository.existsByEmail(d.getEmail())) {
            throw new IllegalArgumentException("Email already in use: " + d.getEmail());
        }
        return doctorRepository.save(d);
    }

    @Transactional(readOnly = true)
    public Doctor getById(Long id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found: " + id));
    }

    @Transactional
    public Doctor update(Long id, Doctor updated) {
        Doctor existing = getById(id);
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setSpeciality(updated.getSpeciality());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setActive(updated.getActive());
        return doctorRepository.save(existing);
    }

    @Transactional
    public void delete(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new IllegalArgumentException("Doctor not found: " + id);
        }
        doctorRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<Doctor> listActive() {
        return doctorRepository.findByActiveTrue();
    }

    @Transactional(readOnly = true)
    public List<Doctor> findBySpeciality(String speciality) {
        return doctorRepository.findBySpecialityIgnoreCase(speciality);
    }
}
