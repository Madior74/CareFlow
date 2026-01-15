package org.school.careflow.controller;

import java.util.List;

import org.school.careflow.dto.AvailabilityResponse;
import org.school.careflow.dto.CreateAvailabilityRequest;
import org.school.careflow.dto.UpdateAvailabilityRequest;
import org.school.careflow.service.ActivityLogService;
import org.school.careflow.service.AvailabilityService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/doctors/{doctorId}/availabilities")
@RequiredArgsConstructor
@Tag(name = "Availabilities", description = "Gestion des disponibilités médecins")
public class AvailabilityController {

    private final AvailabilityService availabilityService;
    private final ActivityLogService activityLogService;

    // create availability
    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvailabilityResponse createAvailability(
            @PathVariable Long doctorId,
            @Valid @RequestBody CreateAvailabilityRequest request) {

        AvailabilityResponse availability = availabilityService.createAvailability(doctorId, request);
        activityLogService.log("AVAILABILITY: Doctor ID " + doctorId,
                "created availability from " + request.getStartTime() + " to " + request.getEndTime() + "by :"
                        + SecurityContextHolder.getContext().getAuthentication().getName());
        return availability;
    }

    @PreAuthorize("hasAnyRole('DOCTOR')")
    @GetMapping
    public List<AvailabilityResponse> getAvailabilities(
            @PathVariable Long doctorId) {

        return availabilityService.getDoctorAvailabilities(doctorId);

    }

    // update availability
    @PreAuthorize("hasAnyRole('DOCTOR')")
    @PutMapping("/{id}")
    public AvailabilityResponse updateAvailability(
            @PathVariable Long doctorId,
            @PathVariable Long id,
            @Valid @RequestBody UpdateAvailabilityRequest request) {

        AvailabilityResponse availability = availabilityService.updateAvailability(id, request);
        activityLogService.log("AVAILABILITY: Doctor ID " + doctorId,
                "updated availability ID " + id + " by :" + SecurityContextHolder.getContext().getAuthentication().getName());
        return availability;
    }

    // delete availability
    @PreAuthorize("hasAnyRole('DOCTOR')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAvailability(
            @PathVariable Long doctorId,
            @PathVariable Long id) {

        availabilityService.deleteAvailabilityById(id);
        activityLogService.log("AVAILABILITY: Doctor ID " + doctorId,
                "deleted availability ID " + id + " by :" + SecurityContextHolder.getContext().getAuthentication().getName());
    }
}
