// package org.school.careflow.dto;

// import io.swagger.v3.oas.annotations.media.Schema;
// import jakarta.validation.constraints.Email;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Size;
// import lombok.Data;

// @Data
// @Schema(description = "Requête d'inscription d'un nouvel utilisateur")
// public class RegisterRequest {

//     @NotBlank(message = "L'email est obligatoire")
//     @Email(message = "L'email doit être valide")
//     @Schema(description = "Adresse email", example = "user@example.com")
//     private String email;

//     @NotBlank(message = "Le mot de passe est obligatoire")
//     @Size(min = 6, max = 100, message = "Le mot de passe doit contenir entre 6 et 100 caractères")
//     @Schema(description = "Mot de passe", example = "password123")
//     private String password;

//     @NotBlank(message = "Le prénom est obligatoire")
//     @Size(min = 2, max = 100, message = "Le prénom doit contenir entre 2 et 100 caractères")
//     @Schema(description = "Prénom", example = "John")
//     private String firstname;

//     @NotBlank(message = "Le nom est obligatoire")
//     @Size(min = 2, max = 100, message = "Le nom doit contenir entre 2 et 100 caractères")
//     @Schema(description = "Nom", example = "Doe")
//     private String lastname;

//     @NotBlank(message = "Le rôle est obligatoire")
//     @Schema(description = "Type de rôle (ADMIN, DOCTOR, STAFF, SECRETARY)", example = "STAFF")
//     private String role;
// }
