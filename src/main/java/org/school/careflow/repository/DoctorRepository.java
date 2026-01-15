package org.school.careflow.repository;

import java.util.Optional;

import org.school.careflow.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository  extends JpaRepository<Doctor,Long> {


       Optional<Doctor> findByUserId(Long userId);
}
