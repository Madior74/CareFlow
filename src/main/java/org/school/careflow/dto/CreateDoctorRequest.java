package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Requête d'inscription d'un médecin")
public class CreateDoctorRequest {

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Schema(description = "Adresse email", example = "john.doe@example.com")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, max = 100, message = "Le mot de passe doit contenir entre 6 et 100 caractères")
    @Schema(description = "Mot de passe", example = "MyPassword123!",accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;
 
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 100, message = "Le prénom doit contenir entre 2 et 100 caractères")
    @Schema(description = "Prénom du médecin", example = "John")
    private String firstname;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Schema(description = "Nom du médecin", example = "Doe")
    private String lastname;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{1,9}$", 
             message = "Format de numéro de téléphone invalide")
    @Schema(description = "Numéro de téléphone Senegal(771234567)", example = "771234567")
    private String phone;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
    @Schema(description = "Adresse", example = "Dakar, Senegal")
    private String address;

    @NotBlank(message = "La spécialité est obligatoire")
    @Schema(description = "Spécialité du médecin", example = "Cardiologie")
    private String specialty;


    @Past(message = "La date de naissance doit être dans le passé")
    @NotNull(message = "La date de naissance est obligatoire")
    @Schema(description = "Date de naissance", example = "1980-05-15")
    private LocalDate dateOfBirth;
    @PastOrPresent(message = "La date d'embauche doit être dans le passé ou le présent")
    @NotNull(message = "La date d'embauche est obligatoire")
    @Schema(description = "Date d'embauche", example = "2020-01-15")
    private LocalDate hireDate;

    @NotBlank(message = "Le sexe est obligatoire")
    @Size(max = 10, message = "Le sexe ne doit pas dépasser 10 caractères")
    @Schema(description = "Sexe (M/F/Autre)", example = "M")
    private String gender;

     // Méthodes utilitaires
    public String getFullName() {
        return firstname + " " + lastname;
    }
}