package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Requête de mise à jour d'un médecin")
public class UpdateDoctorRequest {

    @Email(message = "L'email doit être valide")
    @Schema(description = "Adresse email")
    private String email;

    @Size(min = 6, max = 100, message = "Le mot de passe doit contenir entre 6 et 100 caractères")
    @Schema(description = "Mot de passe", example = "MyPassword123!")
    private String password;

    @Size(min = 3, max = 100, message = "Le prénom doit contenir entre 3 et 100 caractères")
    @Schema(description = "Prénom du médecin")
    private String firstname;

    @Size(min = 3, max = 100, message = "Le nom doit contenir entre 3 et 100 caractères")
    @Schema(description = "Nom du médecin")
    private String lastname;

    @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{1,9}$",
             message = "Format de numéro de téléphone invalide")
    @Schema(description = "Numéro de téléphone")
    private String phone;

    @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
    @Schema(description = "Adresse")
    private String address;

    @Schema(description = "Spécialité du médecin", example = "Cardiologie")
    private String specialty;

    @Size(max = 50, message = "Le numéro de licence ne doit pas dépasser 50 caractères")
    @Schema(description = "Numéro de licence médicale", example = "LIC123456")
    private String licenseNumber;

    @Schema(description = "Date de naissance", example = "1980-05-15")
    private LocalDate dateOfBirth;

    @Schema(description = "Date d'embauche", example = "2020-01-15")
    private LocalDate hireDate;

    @Size(max = 10, message = "Le sexe ne doit pas dépasser 10 caractères")
    @Schema(description = "Sexe (M/F/Autre)", example = "M")
    private String gender;

    // Méthodes utilitaires
    public String getFullName() {
        if (firstname != null && lastname != null) {
            return firstname + " " + lastname;
        }
        return null;
    }
}