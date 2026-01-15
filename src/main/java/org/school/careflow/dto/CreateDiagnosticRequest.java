package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "Requête de création d'un diagnostic")
public class CreateDiagnosticRequest {

    @NotNull(message = "L'ID de la consultation est obligatoire")
    @Schema(description = "ID de la consultation associée")
    private Long consultationId;

    @NotNull(message = "L'ID du médecin est obligatoire")
    @Schema(description = "ID du médecin")
    private Long doctorId;

    @Size(max = 20, message = "Le code de diagnostic ne doit pas dépasser 20 caractères")
    @Schema(description = "Code CIM-10")
    private String diagnosisCode;

    @NotBlank(message = "Le texte du diagnostic est obligatoire")
    @Size(max = 1000, message = "Le texte du diagnostic ne doit pas dépasser 1000 caractères")
    @Schema(description = "Description du diagnostic")
    private String diagnosisText;

    @Size(max = 1000, message = "Les notes ne doivent pas dépasser 1000 caractères")
    @Schema(description = "Notes supplémentaires")
    private String notes;
}