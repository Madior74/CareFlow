package org.school.careflow.controller;

import java.util.List;

import org.school.careflow.dto.CreateDoctorRequest;
import org.school.careflow.dto.DoctorResponse;
import org.school.careflow.dto.UpdateDoctorRequest;
import org.school.careflow.service.ActivityLogService;
import org.school.careflow.service.DoctorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/admin/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctors", description = "Gestion des médecins (ADMIN)")
public class DoctorController {

    private final DoctorService doctorService;
    private final ActivityLogService activityLogService;


    //New doctor
    @Operation(
        summary = "Créer un médecin",
        description = "Accessible uniquement aux administrateurs"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Médecin créé"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/new-doctor")
    @ResponseStatus(HttpStatus.CREATED)
    public DoctorResponse createDoctor(
            @Valid @RequestBody CreateDoctorRequest request) {
        DoctorResponse doctor = doctorService.createDoctor(request);
        activityLogService.log("DOCTOR:"+request.getFullName(), "created successfully by: "+SecurityContextHolder.getContext().getAuthentication().getName());
        return doctor;
    }
    // get doctor by id
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping("/{id}")
    public DoctorResponse getDoctorById(@PathVariable Long id) {
        return doctorService.getDoctorById(id);
    }


    // get all doctors
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @GetMapping
    public List<DoctorResponse> getAllDoctors() {
        return doctorService.getAllDoctors();
    }

    // update doctor
    @Operation(
        summary = "Mettre à jour un médecin",
        description = "Accessible uniquement aux administrateurs"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Médecin mis à jour"),
        @ApiResponse(responseCode = "400", description = "Données invalides"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Médecin non trouvé")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public DoctorResponse updateDoctor(@PathVariable Long id, @Valid @RequestBody UpdateDoctorRequest request) {
        DoctorResponse doctor = doctorService.updateDoctor(id, request);
        activityLogService.log("DOCTOR:"+request.getFullName(), "updated successfully by: "+SecurityContextHolder.getContext().getAuthentication().getName());
        return doctor;
    }

    // delete doctor
    @Operation(
        summary = "Supprimer un médecin",
        description = "Accessible uniquement aux administrateurs"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Médecin supprimé"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Médecin non trouvé")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteDoctor(@PathVariable Long id) {
        DoctorResponse doctor = doctorService.getDoctorById(id); // Get info before deletion for logging
        doctorService.deleteDoctorById(id);
        activityLogService.log("DOCTOR:"+doctor.getFirstname() + " " + doctor.getLastname(), "deleted successfully by: "+SecurityContextHolder.getContext().getAuthentication().getName());
    }

    // activate/deactivate doctor
    @Operation(
        summary = "Activer/Désactiver un médecin",
        description = "Accessible uniquement aux administrateurs"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Statut du médecin modifié"),
        @ApiResponse(responseCode = "403", description = "Accès refusé"),
        @ApiResponse(responseCode = "404", description = "Médecin non trouvé")
    })
    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/activate")
    public DoctorResponse toggleDoctorStatus(@PathVariable Long id) {
        DoctorResponse doctor = doctorService.toggleDoctorStatus(id);
        String action = doctor.isActive() ? "activated" : "deactivated";
        activityLogService.log("DOCTOR:"+doctor.getFirstname() + " " + doctor.getLastname(), action + " successfully by: "+SecurityContextHolder.getContext().getAuthentication().getName());
        return doctor;
    }
}

