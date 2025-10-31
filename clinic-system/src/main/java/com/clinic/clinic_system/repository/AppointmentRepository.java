package com.clinic.clinic_system.repository;

import com.clinic.clinic_system.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    /**
     * Find all appointments for a specific patient, ordered by most recent first.
     */
    List<Appointment> findByPatient_IdOrderByAppointmentDateTimeDesc(Long patientId);

    /**
     * Find all appointments for a specific doctor between a given time range.
     * Useful for dashboards or schedule views.
     */
    List<Appointment> findByDoctor_IdAndAppointmentDateTimeBetweenOrderByAppointmentDateTimeAsc(
            Long doctorId, LocalDateTime from, LocalDateTime to
    );

    /**
     * Basic overlap check for a doctor's schedule (prevents double-booking).
     */
    @Query("""
           SELECT a FROM Appointment a
           WHERE a.doctor.id = :doctorId
             AND a.appointmentDateTime BETWEEN :from AND :to
           """)
    List<Appointment> findOverlappingForDoctor(Long doctorId, LocalDateTime from, LocalDateTime to);

    /**
     * Fetch top 20 upcoming appointments (for dashboard view).
     */
    List<Appointment> findTop20ByOrderByAppointmentDateTimeAsc();

    /**
     * Fetch appointments scheduled for today (used in dashboard stats).
     */
    @Query("""
           SELECT a FROM Appointment a
           WHERE DATE(a.appointmentDateTime) = CURRENT_DATE
           ORDER BY a.appointmentDateTime ASC
           """)
    List<Appointment> findTodayAppointments();

}
