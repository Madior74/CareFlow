package org.school.careflow.controller;

import java.util.Map;

import org.school.careflow.dto.AuthResponse;
import org.school.careflow.dto.LoginRequest;
import org.school.careflow.service.ActivityLogService;
import org.school.careflow.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Gestion de l'authentification des utilisateurs")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final ActivityLogService activityLogService;

    // /**
    //  * Inscription d'un nouvel utilisateur
    //  */
    // @PostMapping("/register")
    // @Operation(summary = "Inscription", description = "Créer un nouveau compte utilisateur")
    // public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
    //     try {
    //         AuthResponse response = userService.registerUser(request);
    //         activityLogService.log("REGISTER", "User: " + request.getEmail() + " registered successfully");
            
    //         return ResponseEntity.status(HttpStatus.CREATED)
    //                 .header("Authorization", "Bearer " + response.getToken())
    //                 .body(response);
    //     } catch (RuntimeException e) {
    //         return ResponseEntity.status(HttpStatus.BAD_REQUEST)
    //                 .body(Map.of("error", e.getMessage()));
    //     }
    // }

    /**
     * Connexion d'un utilisateur
     */
    @PostMapping("/login")
    @Operation(summary = "Connexion", description = "Se connecter avec email et mot de passe")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            AuthResponse response = userService.authenticateUser(request.getEmail(), request.getPassword());

            activityLogService.log("LOGIN", "User: " + request.getEmail() + " logged in successfully");

            // Retourner le token dans le header et le body
            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + response.getToken())
                    .body(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Rafraîchir le token JWT
     */
    @PostMapping("/refresh")
    @Operation(summary = "Rafraîchir le token", description = "Obtenir un nouveau token JWT à partir d'un token existant")
    public ResponseEntity<?> refreshToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Token manquant ou invalide"));
            }

            String token = authHeader.substring(7);
            AuthResponse response = userService.refreshToken(token);

            activityLogService.log("REFRESH_TOKEN", "Token refreshed for user: " + response.getEmail());

            return ResponseEntity.ok()
                    .header("Authorization", "Bearer " + response.getToken())
                    .body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    /**
     * Déconnexion d'un utilisateur
     */
    @PostMapping("/logout")
    @Operation(summary = "Déconnexion", description = "Déconnecter l'utilisateur actuel")
    public ResponseEntity<?> logout(@RequestHeader(value = "Authorization", required = false) String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                String email = userService.getUserEmailFromToken(token);
                activityLogService.log("LOGOUT", "User: " + email + " logged out successfully");
            }

            return ResponseEntity.ok()
                    .body(Map.of("message", "Déconnexion réussie"));
        } catch (Exception e) {
            // Même en cas d'erreur, on considère la déconnexion comme réussie côté client
            return ResponseEntity.ok()
                    .body(Map.of("message", "Déconnexion réussie"));
        }
    }

}
