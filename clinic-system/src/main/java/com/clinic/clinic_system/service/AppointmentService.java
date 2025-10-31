package com.clinic.clinic_system.service;

import com.clinic.clinic_system.dto.CreateAppointmentRequest;
import com.clinic.clinic_system.entity.Appointment;
import com.clinic.clinic_system.entity.Doctor;
import com.clinic.clinic_system.entity.Patient;
import com.clinic.clinic_system.repository.AppointmentRepository;
import com.clinic.clinic_system.repository.DoctorRepository;
import com.clinic.clinic_system.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public AppointmentService(AppointmentRepository appointmentRepository,
                              PatientRepository patientRepository,
                              DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    // ===== CREATE =====
    @Transactional
    public Appointment createAppointment(CreateAppointmentRequest req) {
        if (req.getAppointmentDateTime() == null || req.getAppointmentDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Appointment time must be in the future");
        }

        Patient patient = patientRepository.findById(req.getPatientId())
                .orElseThrow(() -> new IllegalArgumentException("Patient not found: " + req.getPatientId()));

        Doctor doctor = doctorRepository.findById(req.getDoctorId())
                .orElseThrow(() -> new IllegalArgumentException("Doctor not found: " + req.getDoctorId()));

        // Prevent double-booking
        var conflicts = appointmentRepository.findOverlappingForDoctor(
                doctor.getId(), req.getAppointmentDateTime(), req.getAppointmentDateTime()
        );
        if (!conflicts.isEmpty()) {
            throw new IllegalStateException("Doctor already has an appointment at this time");
        }

        Appointment a = new Appointment();
        a.setPatient(patient);
        a.setDoctor(doctor);
        a.setAppointmentDateTime(req.getAppointmentDateTime());
        a.setNotes(req.getNotes());
        a.setStatus("PENDING");
        return appointmentRepository.save(a);
    }

    // ===== READ SINGLE =====
    @Transactional(readOnly = true)
    public Appointment getById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found: " + id));
    }

    // ===== READ LIST (FILTER) =====
    @Transactional(readOnly = true)
    public List<Appointment> listAppointments(Long doctorId, LocalDateTime from, LocalDateTime to) {
        LocalDateTime start = (from != null) ? from : LocalDateTime.now().minusMonths(1);
        LocalDateTime end   = (to != null) ? to   : LocalDateTime.now().plusMonths(1);
        return appointmentRepository
                .findByDoctor_IdAndAppointmentDateTimeBetweenOrderByAppointmentDateTimeAsc(doctorId, start, end);
    }

    // ===== UPDATE STATUS =====
    @Transactional
    public Appointment updateStatus(Long id, String status) {
        Appointment a = getById(id);
        a.setStatus(status == null ? "PENDING" : status.toUpperCase());
        return appointmentRepository.save(a);
    }

    // ===== DELETE =====
    @Transactional
    public void delete(Long id) {
        if (!appointmentRepository.existsById(id)) {
            throw new IllegalArgumentException("Appointment not found: " + id);
        }
        appointmentRepository.deleteById(id);
    }
}
