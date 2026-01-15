package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "Requête de création d'une prescription")
public class CreatePrescriptionRequest {

    @NotNull(message = "L'ID du diagnostic est obligatoire")
    @Schema(description = "ID du diagnostic associé")
    private Long diagnosticId;

    @NotNull(message = "L'ID du médicament est obligatoire")
    @Schema(description = "ID du médicament")
    private Long medicationId;

    @NotBlank(message = "Le dosage est obligatoire")
    @Size(max = 500, message = "Le dosage ne doit pas dépasser 500 caractères")
    @Schema(description = "Dosage (ex: 1 comprimé matin et soir)")
    private String dosage;

    @NotNull(message = "La durée en jours est obligatoire")
    @Min(value = 1, message = "La durée doit être d'au moins 1 jour")
    @Max(value = 365, message = "La durée ne peut pas dépasser 365 jours")
    @Schema(description = "Durée du traitement en jours")
    private Integer durationDays;

    @Min(value = 1, message = "La quantité doit être positive")
    @Schema(description = "Quantité prescrite")
    private Integer quantity;

    @Size(max = 1000, message = "Les instructions ne doivent pas dépasser 1000 caractères")
    @Schema(description = "Instructions supplémentaires")
    private String instructions;
}