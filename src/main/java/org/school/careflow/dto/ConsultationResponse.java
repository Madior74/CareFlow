package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Schema(description = "Réponse avec les informations d'une consultation")
public class ConsultationResponse {

    @Schema(description = "ID de la consultation")
    private Long id;

    @Schema(description = "ID du rendez-vous associé")
    private Long appointmentId;

    @Schema(description = "ID du médecin")
    private Long doctorId;

    @Schema(description = "ID du patient")
    private Long patientId;

    @Schema(description = "Symptômes")
    private String symptoms;

    @Schema(description = "Observations")
    private String observations;

    @Schema(description = "Taille en cm")
    private BigDecimal height;

    @Schema(description = "Poids en kg")
    private BigDecimal weight;

    @Schema(description = "Température")
    private BigDecimal temperature;

    @Schema(description = "Pression artérielle")
    private String bloodPressure;

    @Schema(description = "IMC calculé")
    private BigDecimal bmi;

    @Schema(description = "Date de création")
    private LocalDateTime createdAt;

    @Schema(description = "Date de mise à jour")
    private LocalDateTime updatedAt;
}