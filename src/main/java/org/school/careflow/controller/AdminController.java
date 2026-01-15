package org.school.careflow.controller;

import org.school.careflow.dto.DashboardStatsResponse;
import org.school.careflow.service.AdminService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@Tag(name = "Admin", description = "Gestion administrative")
public class AdminController {

    private final AdminService adminService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
        summary = "Récupérer les statistiques du tableau de bord",
        description = "Retourne les statistiques pour le tableau de bord administrateur"
    )
    @ApiResponse(responseCode = "200", description = "Statistiques récupérées avec succès")
    @ApiResponse(responseCode = "403", description = "Accès refusé")
    public DashboardStatsResponse getDashboardStats() {
        return adminService.getDashboardStats();
    }
}
