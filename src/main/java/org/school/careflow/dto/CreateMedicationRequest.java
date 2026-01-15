package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "Requête de création d'un médicament")
public class CreateMedicationRequest {

    @NotBlank(message = "Le nom du médicament est obligatoire")
    @Size(max = 200, message = "Le nom ne doit pas dépasser 200 caractères")
    @Schema(description = "Nom du médicament")
    private String name;

    @Size(max = 50, message = "La forme galénique ne doit pas dépasser 50 caractères")
    @Schema(description = "Forme galénique (ex: comprimé, sirop)")
    private String dosageForm;

    @Size(max = 1000, message = "L'ingrédient actif ne doit pas dépasser 1000 caractères")
    @Schema(description = "Ingrédient actif")
    private String activeIngredient;

    @Size(max = 50, message = "La force ne doit pas dépasser 50 caractères")
    @Schema(description = "Force (ex: 500mg)")
    private String strength;

    @Size(max = 100, message = "Le fabricant ne doit pas dépasser 100 caractères")
    @Schema(description = "Fabricant")
    private String manufacturer;
}