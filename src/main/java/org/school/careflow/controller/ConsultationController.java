package org.school.careflow.controller;

import java.util.List;

import org.school.careflow.dto.CreateConsultationRequest;
import org.school.careflow.dto.ConsultationResponse;
import org.school.careflow.service.ActivityLogService;
import org.school.careflow.service.ConsultationService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
@RequestMapping("/api/consultation")
@RequiredArgsConstructor
@Tag(name = "Consultations", description = "Gestion des consultations médicales")
public class ConsultationController {

    private final ConsultationService consultationService;
    private final ActivityLogService activityLogService;

    @Operation(summary = "Créer une consultation", description = "Accessible uniquement aux administrateurs, médecins et personnel.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Consultation créée"),
            @ApiResponse(responseCode = "400", description = "Données invalides"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'STAFF')")
    @ResponseStatus(HttpStatus.CREATED)
    public ConsultationResponse createConsultation(@Valid @RequestBody CreateConsultationRequest request) {
        ConsultationResponse response = consultationService.createConsultation(request);
        activityLogService.log("CONSULTATION CREATED for appointment: " + request.getAppointmentId(),
                "by: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return response;
    }

    @Operation(summary = "Récupérer toutes les consultations", description = "Accessible uniquement aux administrateurs, médecins et personnel.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Liste des consultations récupérée"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @GetMapping("/all")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'STAFF')")
    public List<ConsultationResponse> getAllConsultations() {
        return consultationService.getAllConsultations();
    }

    @Operation(summary = "Récupérer une consultation par ID", description = "Accessible uniquement aux administrateurs, médecins et personnel.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consultation récupérée"),
            @ApiResponse(responseCode = "404", description = "Consultation non trouvée"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'STAFF')")
    public ConsultationResponse getConsultationById(@PathVariable Long id) {
        return consultationService.getConsultationById(id);
    }

    @Operation(summary = "Mettre à jour une consultation", description = "Accessible uniquement aux administrateurs, médecins et personnel.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Consultation mise à jour"),
            @ApiResponse(responseCode = "404", description = "Consultation non trouvée"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DOCTOR', 'STAFF')")
    public ConsultationResponse updateConsultation(@PathVariable Long id, @Valid @RequestBody CreateConsultationRequest request) {
        ConsultationResponse response = consultationService.updateConsultation(id, request);
        activityLogService.log("CONSULTATION UPDATED ID: " + id,
                "by: " + SecurityContextHolder.getContext().getAuthentication().getName());
        return response;
    }

    @Operation(summary = "Supprimer une consultation", description = "Accessible uniquement aux administrateurs.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Consultation supprimée"),
            @ApiResponse(responseCode = "404", description = "Consultation non trouvée"),
            @ApiResponse(responseCode = "403", description = "Accès refusé")
    })
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteConsultation(@PathVariable Long id) {
        consultationService.deleteConsultationById(id);
        activityLogService.log("CONSULTATION DELETED ID: " + id,
                "by: " + SecurityContextHolder.getContext().getAuthentication().getName());
    }
}