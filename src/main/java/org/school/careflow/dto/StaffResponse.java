package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Schema(description = "Réponse contenant les informations d'un membre du personnel")
public class StaffResponse {

    @Schema(description = "ID du membre du personnel")
    private Long id;

    @Schema(description = "ID de l'utilisateur associé")
    private Long userId;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "Prénom")
    private String firstname;

    @Schema(description = "Nom")
    private String lastname;

    @Schema(description = "Numéro de téléphone")
    private String phone;

    @Schema(description = "Adresse")
    private String address;

    @Schema(description = "Poste occupé")
    private String position;

    @Schema(description = "Département")
    private String department;

    @Schema(description = "Date d'embauche")
    private LocalDate hireDate;

    @Schema(description = "Date de création")
    private LocalDateTime createdAt;

    @Schema(description = "Date de dernière modification")
    private LocalDateTime updatedAt;

    @Schema(description = "Statut actif")
    private boolean isActive;
}
