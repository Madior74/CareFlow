package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Requête de mise à jour d'un membre du personnel")
public class UpdateStaffRequest {

    @Size(min = 2, max = 100, message = "Le prénom doit contenir entre 2 et 100 caractères")
    @Schema(description = "Prénom du membre du personnel")
    private String firstname;

    @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
    @Schema(description = "Nom du membre du personnel")
    private String lastname;

    @Pattern(regexp = "^[+]?[(]?[0-9]{1,4}[)]?[-\\s.]?[(]?[0-9]{1,4}[)]?[-\\s.]?[0-9]{1,9}$", message = "Format de numéro de téléphone invalide")
    @Schema(description = "Numéro de téléphone")
    private String phone;

    @Size(max = 255, message = "L'adresse ne doit pas dépasser 255 caractères")
    @Schema(description = "Adresse")
    private String address;

    @Size(max = 100, message = "Le poste ne doit pas dépasser 100 caractères")
    @Schema(description = "Poste occupé")
    private String position;

    @Size(max = 100, message = "Le département ne doit pas dépasser 100 caractères")
    @Schema(description = "Département")
    private String department;

    @Schema(description = "Date d'embauche")
    private LocalDate hireDate;
}
