package org.school.careflow.dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Statistiques du tableau de bord administrateur")
public class DashboardStatsResponse {

    @Schema(description = "Nombre de rendez-vous aujourd'hui")
    private Long todaysAppointments;

    @Schema(description = "Nombre de nouveaux patients ce mois")
    private Long newPatientsThisMonth;

    @Schema(description = "Nombre de médecins en service")
    private Long doctorsOnDuty;

    @Schema(description = "Nombre total de patients")
    private Long totalPatients;

    @Schema(description = "Liste des prochains rendez-vous")
    private List<UpcomingAppointmentResponse> upcomingAppointments;
}

