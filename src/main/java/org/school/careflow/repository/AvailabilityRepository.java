package org.school.careflow.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.school.careflow.model.Availability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface AvailabilityRepository  extends JpaRepository<Availability, Long> {

    @Query("SELECT a FROM Availability a JOIN FETCH a.doctor WHERE a.doctor.id = :doctorId")
    List<Availability> findByDoctorId(@Param("doctorId") Long doctorId);

    //bloquer les chevauchements
      boolean existsByDoctorIdAndStartTimeLessThanAndEndTimeGreaterThan(
        Long doctorId,
        LocalDateTime end,
        LocalDateTime start
    );

      boolean existsByDoctorIdAndStartTimeLessThanEqualAndEndTimeGreaterThan(Long id, LocalDateTime appointmentTime,
          LocalDateTime appointmentTime2);

      @Query("SELECT CASE WHEN COUNT(a) > 0 THEN true ELSE false END FROM Availability a " +
             "WHERE a.doctor.id = :doctorId AND a.startTime <= :appointmentTime AND a.endTime >= :appointmentTime")
      boolean existsByDoctorIdAndTimeBetween(@Param("doctorId") Long doctorId, 
                                              @Param("appointmentTime") LocalDateTime appointmentTime);
    
}
