package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Requête de mise à jour d'un patient")
public class UpdatePatientRequest {

    @Size(min = 2, max = 100, message = "Le prénom doit contenir entre 2 et 100 caractères")
    @Schema(description = "Prénom du patient")
    private String firstname;

    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Schema(description = "Nom du patient")
    private String lastname;

    @Pattern(regexp = "^[0-9]{9}$", message = "Le numéro de téléphone doit contenir exactement 9 chiffres")
    @Schema(description = "Numéro de téléphone")
    private String phone;

    @Email(message = "L'email doit être valide")
    @Schema(description = "Adresse email")
    private String email;

    @Schema(description = "Date de naissance")
    private LocalDate dateOfBirth;

    @Size(max = 10, message = "Le genre ne doit pas dépasser 10 caractères")
    @Schema(description = "Genre")
    private String gender;

    @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
    @Schema(description = "Adresse")
    private String address;

    @Size(max = 5, message = "Le groupe sanguin ne doit pas dépasser 5 caractères")
    @Schema(description = "Groupe sanguin")
    private String bloodType;

    @Schema(description = "Allergies")
    private String allergies;

    @Size(max = 100, message = "Le nom du contact d'urgence ne doit pas dépasser 100 caractères")
    @Schema(description = "Nom du contact d'urgence")
    private String emergencyContactName;

    @Size(max = 20, message = "Le téléphone du contact d'urgence ne doit pas dépasser 20 caractères")
    @Schema(description = "Téléphone du contact d'urgence")
    private String emergencyContactPhone;

    // Méthodes utilitaires
    public String getFullName() {
        if (firstname != null && lastname != null) {
            return firstname + " " + lastname;
        }
        return null;
    }
}