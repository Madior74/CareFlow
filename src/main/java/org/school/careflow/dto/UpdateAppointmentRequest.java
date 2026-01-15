package org.school.careflow.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Mise à jour d'un rendez-vous")
public class UpdateAppointmentRequest {

    @Schema(description = "ID du médecin")
    private Long doctorId;

    @Schema(description = "ID du patient")
    private Long patientId;

    @Schema(example = "2025-01-10T10:00")
    private LocalDateTime appointmentTime;
}