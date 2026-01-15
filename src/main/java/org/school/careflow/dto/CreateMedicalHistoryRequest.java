package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.school.careflow.model.ConditionType;

import java.time.LocalDate;

@Data
@Schema(description = "Requête de création d'un historique médical")
public class CreateMedicalHistoryRequest {

    @NotNull(message = "L'ID du patient est obligatoire")
    @Schema(description = "ID du patient")
    private Long patientId;

    @NotBlank(message = "Le nom de la condition est obligatoire")
    @Size(max = 200, message = "Le nom de la condition ne doit pas dépasser 200 caractères")
    @Schema(description = "Nom de la condition médicale")
    private String conditionName;

    @NotNull(message = "Le type de condition est obligatoire")
    @Schema(description = "Type de condition")
    private ConditionType conditionType;

    @PastOrPresent(message = "La date de diagnostic ne peut pas être dans le futur")
    @Schema(description = "Date du diagnostic")
    private LocalDate diagnosisDate;

    @Size(max = 1000, message = "Les notes ne doivent pas dépasser 1000 caractères")
    @Schema(description = "Notes supplémentaires")
    private String notes;
}