package org.school.careflow.dto;

import java.time.LocalDateTime;

import org.school.careflow.model.AppointmentStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Rendez-vous")
public class AppointmentResponse {

    private Long id;
    private Long doctorId;
    private Long patientId;
    private LocalDateTime appointmentTime;
    private AppointmentStatus status;
}
