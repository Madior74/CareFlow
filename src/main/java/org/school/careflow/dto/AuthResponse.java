package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Réponse d'authentification contenant le token JWT")
public class AuthResponse {

    @Schema(description = "Token JWT", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;

    @Schema(description = "Type de token", example = "Bearer")
    private String type = "Bearer";

    @Schema(description = "ID de l'utilisateur")
    private Long userId;

    @Schema(description = "Email de l'utilisateur")
    private String email;

    @Schema(description = "Message de confirmation")
    private String message;

    public AuthResponse(String token, Long userId, String email, String message) {
        this.token = token;
        this.type = "Bearer";
        this.userId = userId;
        this.email = email;
        this.message = message;
    }
}
