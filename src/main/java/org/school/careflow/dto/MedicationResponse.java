package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(description = "Réponse avec les informations d'un médicament")
public class MedicationResponse {

    @Schema(description = "ID du médicament")
    private Long id;

    @Schema(description = "Nom du médicament")
    private String name;

    @Schema(description = "Forme galénique")
    private String dosageForm;

    @Schema(description = "Ingrédient actif")
    private String activeIngredient;

    @Schema(description = "Force")
    private String strength;

    @Schema(description = "Fabricant")
    private String manufacturer;

    @Schema(description = "Date de création")
    private LocalDateTime createdAt;

    @Schema(description = "Date de mise à jour")
    private LocalDateTime updatedAt;
}