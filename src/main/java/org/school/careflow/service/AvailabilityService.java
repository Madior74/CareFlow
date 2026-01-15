package org.school.careflow.service;

import java.util.List;

import org.school.careflow.dto.AvailabilityResponse;
import org.school.careflow.dto.CreateAvailabilityRequest;
import org.school.careflow.dto.UpdateAvailabilityRequest;
import org.school.careflow.exception.ResourceNotFoundException;
import org.school.careflow.model.Availability;
import org.school.careflow.model.Doctor;
import org.school.careflow.repository.AvailabilityRepository;
import org.school.careflow.repository.DoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final DoctorRepository doctorRepository;


    //create availability
    @Transactional
    public AvailabilityResponse createAvailability(
            Long doctorId,
            CreateAvailabilityRequest request) {

        if (request.getStartTime().isAfter(request.getEndTime())
                || request.getStartTime().isEqual(request.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor", "id", doctorId));

        boolean overlapExists =
                availabilityRepository. existsByDoctorIdAndStartTimeLessThanAndEndTimeGreaterThan(
                        doctorId,
                        request.getEndTime(),
                        request.getStartTime()
                );

        if (overlapExists) {
            throw new ResourceNotFoundException("Availability overlaps with existing schedule");
        }

        Availability availability = new Availability();
        availability.setDoctor(doctor);
        availability.setStartTime(request.getStartTime());
        availability.setEndTime(request.getEndTime());

        Availability saved = availabilityRepository.save(availability);

        return mapToResponse(saved);
    }

    //get availabilities by doctor id
    @Transactional(readOnly = true)
    public List<AvailabilityResponse> getDoctorAvailabilities(Long doctorId) {

        if (!doctorRepository.existsById(doctorId)) {
            throw new ResourceNotFoundException("Doctor", "id", doctorId);
        }

        return availabilityRepository.findByDoctorId(doctorId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // update availability
    @Transactional
    public AvailabilityResponse updateAvailability(Long availabilityId, UpdateAvailabilityRequest request) {
        Availability availability = availabilityRepository.findById(availabilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Availability", "id", availabilityId.toString()));

        if (request.getStartTime() != null) {
            availability.setStartTime(request.getStartTime());
        }
        if (request.getEndTime() != null) {
            availability.setEndTime(request.getEndTime());
        }

        // Validate that start time is before end time
        if (availability.getStartTime().isAfter(availability.getEndTime()) ||
            availability.getStartTime().isEqual(availability.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        Availability updated = availabilityRepository.save(availability);
        return mapToResponse(updated);
    }

    // delete availability by id
    @Transactional
    public void deleteAvailabilityById(Long availabilityId) {
        if (!availabilityRepository.existsById(availabilityId)) {
            throw new ResourceNotFoundException("Availability", "id", availabilityId);
        }
        availabilityRepository.deleteById(availabilityId);
        
    }



    //map to response
    private AvailabilityResponse mapToResponse(Availability availability) {
        AvailabilityResponse response = new AvailabilityResponse();
        response.setId(availability.getId());
        response.setStartTime(availability.getStartTime());
        response.setEndTime(availability.getEndTime());
        response.setCreatedAt(availability.getCreatedAt());
        response.setUpdatedAt(availability.getUpdatedAt());
        
        // Mapper les informations du médecin
        if (availability.getDoctor() != null) {
            response.setDoctorId(availability.getDoctor().getId());
            response.setDoctorFullName(
                availability.getDoctor().getFirstname() + " " + 
                availability.getDoctor().getLastname()
            );
        }
        
        return response;
    }
}
