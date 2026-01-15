package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.school.careflow.model.PrescriptionStatus;

import java.time.LocalDateTime;

@Data
@Schema(description = "Réponse avec les informations d'une prescription")
public class PrescriptionResponse {

    @Schema(description = "ID de la prescription")
    private Long id;

    @Schema(description = "ID du diagnostic associé")
    private Long diagnosticId;

    @Schema(description = "ID du médicament")
    private Long medicationId;

    @Schema(description = "Dosage")
    private String dosage;

    @Schema(description = "Durée en jours")
    private Integer durationDays;

    @Schema(description = "Quantité")
    private Integer quantity;

    @Schema(description = "Instructions")
    private String instructions;

    @Schema(description = "Statut")
    private PrescriptionStatus status;

    @Schema(description = "Date de début")
    private LocalDateTime startDate;

    @Schema(description = "Date de fin")
    private LocalDateTime endDate;

    @Schema(description = "Date de création")
    private LocalDateTime createdAt;

    @Schema(description = "Date de mise à jour")
    private LocalDateTime updatedAt;
}