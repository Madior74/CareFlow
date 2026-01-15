package org.school.careflow.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(description = "Création d'un rendez-vous")
public class CreateAppointmentRequest {

    @NotNull
    @Schema(description = "ID du médecin")
    private Long doctorId;

    @NotNull
    @Schema(description = "ID du patient")
    private Long patientId;

    @NotNull
    @Schema(example = "2025-01-10T10:00")
    private LocalDateTime appointmentTime;
}

