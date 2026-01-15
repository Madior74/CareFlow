package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.school.careflow.model.ConditionType;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Schema(description = "Réponse avec les informations d'un historique médical")
public class MedicalHistoryResponse {

    @Schema(description = "ID de l'historique médical")
    private Long id;

    @Schema(description = "ID du patient")
    private Long patientId;

    @Schema(description = "Nom de la condition")
    private String conditionName;

    @Schema(description = "Type de condition")
    private ConditionType conditionType;

    @Schema(description = "Date du diagnostic")
    private LocalDate diagnosisDate;

    @Schema(description = "Notes")
    private String notes;

    @Schema(description = "Date de création")
    private LocalDateTime createdAt;

    @Schema(description = "Date de mise à jour")
    private LocalDateTime updatedAt;
}