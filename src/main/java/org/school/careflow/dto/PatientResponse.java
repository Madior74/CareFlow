package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Réponse avec les informations du patient")
public class PatientResponse {

    @Schema(description = "ID du patient")
    private Long id;

    @Schema(description = "Prénom du patient")
    private String firstname;

    @Schema(description = "Nom du patient")
    private String lastname;

    @Schema(description = "Email")
    private String email;

    @Schema(description = "Numéro de téléphone")
    private String phone;

    @Schema(description = "Date de naissance")
    private LocalDate dateOfBirth;

    @Schema(description = "Genre")
    private String gender;

    @Schema(description = "Adresse")
    private String address;

    @Schema(description = "Groupe sanguin")
    private String bloodType;

    @Schema(description = "Allergies")
    private String allergies;

    @Schema(description = "Nom du contact d'urgence")
    private String emergencyContactName;

    @Schema(description = "Téléphone du contact d'urgence")
    private String emergencyContactPhone;

}
