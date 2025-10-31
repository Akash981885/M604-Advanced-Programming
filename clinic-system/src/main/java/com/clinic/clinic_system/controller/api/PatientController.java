package com.clinic.clinic_system.controller.api;

import com.clinic.clinic_system.entity.Patient;
import com.clinic.clinic_system.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients")
@CrossOrigin
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // ===== CREATE via JSON =====
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Patient> createJson(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.create(patient));
    }

    // ===== CREATE via HTML FORM (Thymeleaf) =====
    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Patient> createForm(Patient patient) {
        return ResponseEntity.ok(patientService.create(patient));
    }

    // ===== GET BY ID =====
    @GetMapping("/{id}")
    public ResponseEntity<Patient> getById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getById(id));
    }

    // ===== LIST / SEARCH (with pagination) =====
    @GetMapping
    public ResponseEntity<Page<Patient>> search(
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(patientService.search(q, PageRequest.of(page, size)));
    }

    // ===== UPDATE =====
    @PutMapping("/{id}")
    public ResponseEntity<Patient> update(@PathVariable Long id, @RequestBody Patient updated) {
        return ResponseEntity.ok(patientService.update(id, updated));
    }

    // ===== DELETE =====
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        patientService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
