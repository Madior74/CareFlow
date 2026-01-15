package org.school.careflow.repository;

import java.util.Optional;

import org.school.careflow.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StaffRepository extends JpaRepository<Staff,Long>{

     Optional<Staff> findByUserId(Long userId);
    
}
