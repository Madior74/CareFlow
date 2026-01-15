package org.school.careflow.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

import org.school.careflow.dto.DashboardStatsResponse;
import org.school.careflow.dto.UpcomingAppointmentResponse;
import org.school.careflow.model.Appointment;
import org.school.careflow.model.AppointmentStatus;
import org.school.careflow.model.Doctor;
import org.school.careflow.model.Patient;
import org.school.careflow.repository.AppointmentRepository;
import org.school.careflow.repository.DoctorRepository;
import org.school.careflow.repository.PatientRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    public DashboardStatsResponse getDashboardStats() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.atTime(23, 59, 59);

        // Rendez-vous d'aujourd'hui
        Long todaysAppointments = appointmentRepository.findAll().stream()
                .filter(appointment -> {
                    LocalDateTime appointmentTime = appointment.getAppointmentTime();
                    return appointmentTime.isAfter(startOfDay.minusSeconds(1)) 
                        && appointmentTime.isBefore(endOfDay.plusSeconds(1))
                        && appointment.getStatus() != AppointmentStatus.CANCELLED;
                })
                .count();

        // Nouveaux patients ce mois
        YearMonth currentMonth = YearMonth.now();
        LocalDateTime startOfMonth = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfMonth = currentMonth.atEndOfMonth().atTime(23, 59, 59);

        Long newPatientsThisMonth = patientRepository.findAll().stream()
                .filter(patient -> {
                    LocalDateTime createdAt = patient.getCreatedAt();
                    return createdAt != null 
                        && createdAt.isAfter(startOfMonth.minusSeconds(1))
                        && createdAt.isBefore(endOfMonth.plusSeconds(1));
                })
                .count();

        // Médecins en service (actifs)
        Long doctorsOnDuty = doctorRepository.findAll().stream()
                .filter(doctor -> doctor.getUser() != null && doctor.getUser().isActive())
                .count();

        // Total patients
        Long totalPatients = patientRepository.count();

        // Prochains rendez-vous (les 10 prochains)
        List<UpcomingAppointmentResponse> upcomingAppointments = appointmentRepository.findAll().stream()
                .filter(appointment -> 
                    appointment.getAppointmentTime().isAfter(LocalDateTime.now())
                    && appointment.getStatus() == AppointmentStatus.SCHEDULED
                )
                .sorted((a1, a2) -> a1.getAppointmentTime().compareTo(a2.getAppointmentTime()))
                .limit(10)
                .map(this::mapToUpcomingAppointmentResponse)
                .collect(Collectors.toList());

        return new DashboardStatsResponse(
                todaysAppointments,
                newPatientsThisMonth,
                doctorsOnDuty,
                totalPatients,
                upcomingAppointments
        );
    }

    private UpcomingAppointmentResponse mapToUpcomingAppointmentResponse(Appointment appointment) {
        Patient patient = appointment.getPatient();
        Doctor doctor = appointment.getDoctor();
        
        String patientName = patient.getFirstname() + " " + patient.getLastname();
        String doctorName = doctor.getFirstname() + " " + doctor.getLastname();

        return new UpcomingAppointmentResponse(
                appointment.getId(),
                patientName,
                doctorName,
                appointment.getAppointmentTime(),
                appointment.getStatus()
        );
    }
}

