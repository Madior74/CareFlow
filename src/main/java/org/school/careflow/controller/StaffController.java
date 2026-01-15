package org.school.careflow.controller;

import java.util.List;

import org.school.careflow.dto.CreateStaffRequest;
import org.school.careflow.dto.StaffResponse;
import org.school.careflow.dto.UpdateStaffRequest;
import org.school.careflow.service.StaffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@Tag(name = "Staff", description = "Gestion du personnel")
public class StaffController {

    private final StaffService staffService;

    /**
     * Créer un nouveau membre du personnel
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Créer un membre du personnel", description = "Crée un nouveau membre du personnel avec un compte utilisateur")
    public ResponseEntity<StaffResponse> createStaff(@Valid @RequestBody CreateStaffRequest request) {
        StaffResponse response = staffService.createStaff(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * Récupérer tous les membres du personnel
     */
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Operation(summary = "Lister tous les membres du personnel", description = "Récupère la liste complète de tous les membres du personnel")
    public ResponseEntity<List<StaffResponse>> getAllStaff() {
        List<StaffResponse> staffList = staffService.getAllStaff();
        return ResponseEntity.ok(staffList);
    }

    /**
     * Récupérer un membre du personnel par son ID
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Operation(summary = "Récupérer un membre du personnel par ID", description = "Récupère les détails d'un membre du personnel spécifique")
    public ResponseEntity<StaffResponse> getStaffById(@PathVariable Long id) {
        StaffResponse response = staffService.getStaffById(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Récupérer un membre du personnel par l'ID de l'utilisateur
     */
    @GetMapping("/user/{userId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'STAFF')")
    @Operation(summary = "Récupérer un membre du personnel par ID utilisateur", description = "Récupère les détails d'un membre du personnel via son ID utilisateur")
    public ResponseEntity<StaffResponse> getStaffByUserId(@PathVariable Long userId) {
        StaffResponse response = staffService.getStaffByUserId(userId);
        return ResponseEntity.ok(response);
    }

    /**
     * Mettre à jour un membre du personnel
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Mettre à jour un membre du personnel", description = "Met à jour les informations d'un membre du personnel")
    public ResponseEntity<StaffResponse> updateStaff(
            @PathVariable Long id,
            @Valid @RequestBody UpdateStaffRequest request) {
        StaffResponse response = staffService.updateStaff(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Supprimer un membre du personnel
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Supprimer un membre du personnel", description = "Supprime un membre du personnel et désactive son compte utilisateur")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }
}
