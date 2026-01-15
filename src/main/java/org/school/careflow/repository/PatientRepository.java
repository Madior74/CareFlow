package org.school.careflow.repository;

import org.school.careflow.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PatientRepository extends JpaRepository<Patient,Long>{

    boolean existsByEmail(String email);

    
}
