package org.school.careflow.repository;

import org.school.careflow.model.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    boolean existsByAppointmentId(Long appointmentId);
}