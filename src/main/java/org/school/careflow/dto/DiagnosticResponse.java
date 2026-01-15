package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Réponse avec les informations d'un diagnostic")
public class DiagnosticResponse {

    @Schema(description = "ID du diagnostic")
    private Long id;

    @Schema(description = "ID de la consultation associée")
    private Long consultationId;

    @Schema(description = "ID du médecin")
    private Long doctorId;

    @Schema(description = "Code CIM-10")
    private String diagnosisCode;

    @Schema(description = "Texte du diagnostic")
    private String diagnosisText;

    @Schema(description = "Notes")
    private String notes;

    @Schema(description = "Date de création")
    private LocalDateTime createdAt;

    @Schema(description = "Date de mise à jour")
    private LocalDateTime updatedAt;
}