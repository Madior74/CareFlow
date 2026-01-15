package org.school.careflow.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Schema(description = "Requête de connexion")
public class LoginRequest {

    @NotBlank(message = "Le username ou l'email est obligatoire")
    @Schema(description = "Adresse email", example = "admin@clinic.local", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Schema(description = "Mot de passe", example = "admin123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}