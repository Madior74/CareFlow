package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Requête de création d'un membre du personnel")
public class CreateStaffRequest {

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "L'email doit être valide")
    @Schema(description = "Adresse email", example = "staff@example.com")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, max = 100, message = "Le mot de passe doit contenir entre 6 et 100 caractères")
    @Schema(description = "Mot de passe", example = "MyPassword123!",accessMode = Schema.AccessMode.WRITE_ONLY)
    private String password;

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 2, max = 100, message = "Le prénom doit contenir entre 2 et 100 caractères")
    @Schema(description = "Prénom du membre du personnel")
    private String firstname;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Schema(description = "Nom du membre du personnel")
    private String lastname;

    @NotBlank(message = "Le numéro de téléphone est obligatoire")
    @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{1,9}$", message = "Format de numéro de téléphone invalide")
    @Schema(description = "Numéro de téléphone")
    private String phone;

    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
    @Schema(description = "Adresse")
    private String address;

    @NotBlank(message = "Le poste est obligatoire")
    @Size(max = 100, message = "Le poste ne doit pas dépasser 100 caractères")
    @Schema(description = "Poste occupé")
    private String position;

    @Schema(description = "Département")
    @Size(max = 100, message = "Le département ne doit pas dépasser 100 caractères")
    private String department;

    @Schema(description = "Date d'embauche")
    private LocalDate hireDate;
}
