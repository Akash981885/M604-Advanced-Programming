package com.clinic.clinic_system.controller.web;

import com.clinic.clinic_system.repository.AppointmentRepository;
import com.clinic.clinic_system.repository.DoctorRepository;
import com.clinic.clinic_system.repository.MedicalRecordRepository;
import com.clinic.clinic_system.repository.PatientRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
public class WebController {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final AppointmentRepository appointmentRepository;

    public WebController(PatientRepository patientRepository,
                         DoctorRepository doctorRepository,
                         MedicalRecordRepository medicalRecordRepository,
                         AppointmentRepository appointmentRepository) {
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("patientCount", patientRepository.count());
        model.addAttribute("doctorCount", doctorRepository.count());
        return "index";
    }

    @GetMapping("/patients")
    public String patients(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        return "patients";
    }

    @GetMapping("/doctors")
    public String doctors(Model model) {
        model.addAttribute("doctors", doctorRepository.findAll());
        return "doctors";
    }

    @GetMapping("/appointments")
    public String appointments(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        return "appointments";
    }

    @GetMapping("/patient")
    public String patientProfile(@RequestParam("id") Long id, Model model) {
        Optional.ofNullable(patientRepository.findById(id)).ifPresent(p -> model.addAttribute("patient", p.get()));
        model.addAttribute("records", medicalRecordRepository.findByPatient_IdOrderByRecordDateDesc(id));
        return "patient_profile";
    }

    @GetMapping("/medical-records")
    public String medicalRecords(Model model) {
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("records", medicalRecordRepository.findAll());
        return "medical_records";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("totalPatients", patientRepository.count());
        model.addAttribute("totalDoctors", doctorRepository.count());
        model.addAttribute("upcomingAppointments", appointmentRepository.findTop20ByOrderByAppointmentDateTimeAsc());
        model.addAttribute("patients", patientRepository.findAll());
        model.addAttribute("doctors", doctorRepository.findAll());
        return "dashboard";
    }

    // --- NEW: Reports page ---
    @GetMapping("/reports")
    public String reports(Model model) {
        model.addAttribute("totalPatients", patientRepository.count());
        model.addAttribute("totalDoctors", doctorRepository.count());
        model.addAttribute("totalRecords", medicalRecordRepository.count());
        return "reports"; // templates/reports.html
    }
}
