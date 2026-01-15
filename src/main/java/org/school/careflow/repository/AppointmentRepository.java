package org.school.careflow.repository;

import java.time.LocalDateTime;

import org.school.careflow.model.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
      Page<Appointment> findByDoctorId(Long doctorId, Pageable pageable);

    Page<Appointment> findByPatientId(Long patientId, Pageable pageable);

    boolean existsByDoctorIdAndAppointmentTime(
        Long doctorId,
        LocalDateTime appointmentTime
    );
    
}
