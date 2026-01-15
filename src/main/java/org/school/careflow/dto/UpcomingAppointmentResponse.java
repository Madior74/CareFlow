package org.school.careflow.dto;

import java.time.LocalDateTime;

import org.school.careflow.model.AppointmentStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Rendez-vous à venir")
public class UpcomingAppointmentResponse {

    @Schema(description = "ID du rendez-vous")
    private Long id;

    @Schema(description = "Nom du patient")
    private String patientName;

    @Schema(description = "Nom du médecin")
    private String doctorName;

    @Schema(description = "Heure du rendez-vous")
    private LocalDateTime appointmentTime;

    @Schema(description = "Statut du rendez-vous")
    private AppointmentStatus status;
}

