package org.school.careflow.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

@Data
@Schema(description = "Réponse avec les informations du médecin")
public class DoctorResponse {
    
    @Schema(description = "ID du médecin")
    private Long id;
    
    @Schema(description = "Prénom du médecin")
    private String firstname;
    
    @Schema(description = "Nom du médecin")
    private String lastname;
    
    @Schema(description = "Email")
    private String email;
    
    @Schema(description = "Numéro de téléphone")
    private String phone;
    
    @Schema(description = "Adresse")
    private String address;
    
    @Schema(description = "Spécialité")
    private String specialty;
    
    @Schema(description = "Numéro de licence médicale")
    private String licenseNumber;
    
    @Schema(description = "Date de naissance")
    private LocalDate dateOfBirth;
    
    @Schema(description = "Date d'embauche")
    private LocalDate hireDate;
    
    @Schema(description = "Sexe")
    private String gender;
    
    @Schema(description = "Compte actif")
    private boolean active;
}