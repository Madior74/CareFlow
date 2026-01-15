package org.school.careflow.controller;

import java.util.List;

import org.school.careflow.dto.CreatePatientRequest;
import org.school.careflow.dto.PatientResponse;
import org.school.careflow.dto.UpdatePatientRequest;
import org.school.careflow.service.ActivityLogService;
import org.school.careflow.service.PatientService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
@Tag(name = "Patients", description = "Gestion des patients")

public class PatientController {

    private final PatientService patientService;
    private final ActivityLogService activityLogService;

    // New doctor
    @Operation(summary = "Créer un patient", description = "Accessible uniquement aux administrateurs ,aux medecins et au personnel.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Patient créé"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR','STAFF')")
    @ResponseStatus(HttpStatus.CREATED)
    public PatientResponse createPatient(@Valid @RequestBody CreatePatientRequest createPatientRequest) {
        PatientResponse patient = patientService.createPatient(createPatientRequest);
        activityLogService.log("PATIENT:"+createPatientRequest.getFullName(), "created successfully by: "+SecurityContextHolder.getContext().getAuthentication().getName());
        return patient;
    }

    // get all patients
    @Operation(summary = "Récupérer tous les patients", description = "Accessible uniquement aux administrateurs ,aux medecins et au personnel.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des patients récupérée"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR','STAFF')")
    public List<PatientResponse> getAllPatients() {
        return patientService.getAllPatients();

    }

    // get patient by id
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR','STAFF')")
    @GetMapping("/{id}")
    public PatientResponse getPatientById(@PathVariable Long id) {
        return patientService.getPatientById(id);
    }

    // update patient
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR','STAFF')")
    @PutMapping("/update/{id}")
    public PatientResponse updatePatient(@PathVariable Long id, @Valid @RequestBody UpdatePatientRequest updatePatientRequest) {
        PatientResponse patient = patientService.updatePatient(id, updatePatientRequest);
        activityLogService.log("PATIENT:"+updatePatientRequest.getFullName(), "updated successfully by: "+SecurityContextHolder.getContext().getAuthentication().getName());
        return patient;
    }

    // delete patient by id
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public void deletePatientById(@PathVariable Long id) {
        patientService.deletePatientById(id);
        activityLogService.log("PATIENT ID:"+id, "deleted successfully by: "+SecurityContextHolder.getContext().getAuthentication().getName());
    }

}