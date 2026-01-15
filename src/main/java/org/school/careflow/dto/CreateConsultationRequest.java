package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Requête de création d'une consultation")
public class CreateConsultationRequest {

    @NotNull(message = "L'ID du rendez-vous est obligatoire")
    @Schema(description = "ID du rendez-vous associé")
    private Long appointmentId;

    @NotNull(message = "L'ID du médecin est obligatoire")
    @Schema(description = "ID du médecin", example = "1")
    private Long doctorId;

    @NotNull(message = "L'ID du patient est obligatoire")
    @Schema(description = "ID du patient", example = "1")
    private Long patientId;

    @NotBlank(message = "Les symptômes sont obligatoires")
    @Size(max = 1000, message = "Les symptômes ne doivent pas dépasser 1000 caractères")
    @Schema(description = "Symptômes décrits par le patient", example = "Mal de tête, fièvre, nausées")
    private String symptoms;

    @Size(max = 1000, message = "Les observations ne doivent pas dépasser 1000 caractères")
    @Schema(description = "Observations du médecin", example = "Le patient a une fièvre de 38°C")
    private String observations;

    @DecimalMin(value = "0.0", inclusive = false, message = "La taille doit être positive")
    @DecimalMax(value = "300.0", message = "La taille doit être réaliste (cm)")
    @Schema(description = "Taille en cm", example = "180.0")
    private BigDecimal height;

    @DecimalMin(value = "0.0", inclusive = false, message = "Le poids doit être positif")
    @DecimalMax(value = "500.0", message = "Le poids doit être réaliste (kg)")
    @Schema(description = "Poids en kg", example = "80.0")
    private BigDecimal weight;

    @DecimalMin(value = "30.0", message = "La température doit être réaliste")
    @DecimalMax(value = "45.0", message = "La température doit être réaliste")
    @Schema(description = "Température corporelle", example = "38.0")
    private BigDecimal temperature;

    @Size(max = 20, message = "La pression artérielle ne doit pas dépasser 20 caractères")
    @Schema(description = "Pression artérielle (ex: 120/80)", example = "120/80")
    private String bloodPressure;
}