package org.school.careflow.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Réponse avec les informations d'un créneau de disponibilité")
public class AvailabilityResponse {

    @Schema(description = "ID du créneau de disponibilité", example = "1")
    private Long id;

    @Schema(description = "ID du médecin", example = "1")
    private Long doctorId;

    @Schema(description = "Nom complet du médecin", example = "Dr. John Doe")
    private String doctorFullName;

    @Schema(description = "Heure de début du créneau", example = "2025-01-10T09:00")
    private LocalDateTime startTime;

    @Schema(description = "Heure de fin du créneau", example = "2025-01-10T12:00")
    private LocalDateTime endTime;

    @Schema(description = "Date de création", example = "2025-01-01T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Date de dernière modification", example = "2025-01-01T10:00:00")
    private LocalDateTime updatedAt;
}
