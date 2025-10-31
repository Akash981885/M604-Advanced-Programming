package com.clinic.clinic_system.controller.api;

import com.clinic.clinic_system.repository.AppointmentRepository;
import com.clinic.clinic_system.repository.DoctorRepository;
import com.clinic.clinic_system.repository.MedicalRecordRepository;
import com.clinic.clinic_system.repository.PatientRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;

    public StatsController(PatientRepository patientRepository,
                           DoctorRepository doctorRepository,
                           MedicalRecordRepository medicalRecordRepository,
                           AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/summary")
    public Map<String, Object> summary() {
        return Map.of(
                "totalPatients", patientRepository.count(),
                "totalDoctors", doctorRepository.count(),
                "totalRecords", medicalRecordRepository.count(),
                "upcomingAppointments", appointmentRepository.findTop20ByOrderByAppointmentDateTimeAsc().size()
        );
    }

    @GetMapping("/todayAppointments")
    public Object todayAppointments() {
        return appointmentRepository.findTodayAppointments(); // make sure this method exists in repo
    }
}
