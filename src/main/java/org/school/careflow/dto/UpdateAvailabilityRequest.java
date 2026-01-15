package org.school.careflow.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Mise à jour d'un créneau de disponibilité")
public class UpdateAvailabilityRequest {

    @Schema(example = "2025-01-10T09:00")
    private LocalDateTime startTime;

    @Schema(example = "2025-01-10T12:00")
    private LocalDateTime endTime;
}