package org.school.careflow.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Requête de création d'un créneau de disponibilité")
public class CreateAvailabilityRequest {

    @NotNull(message = "L'heure de début est obligatoire")
    @FutureOrPresent(message = "L'heure de début doit être dans le futur ou le présent")
    @Schema(description = "Heure de début du créneau de disponibilité", example = "2025-01-10T09:00")
    private LocalDateTime startTime;

    @NotNull(message = "L'heure de fin est obligatoire")
    @FutureOrPresent(message = "L'heure de fin doit être dans le futur ou le présent")
    @Schema(description = "Heure de fin du créneau de disponibilité", example = "2025-01-10T12:00")
    private LocalDateTime endTime;

    @AssertTrue(message = "L'heure de début doit être antérieure à l'heure de fin")
    private boolean isValidTimeRange() {
        if (startTime == null || endTime == null) {
            return true; // Les validations @NotNull géreront les nulls
        }
        return startTime.isBefore(endTime);
    }
}

