package org.school.careflow.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.school.careflow.dto.AppointmentResponse;
import org.school.careflow.dto.CreateAppointmentRequest;
import org.school.careflow.exception.ResourceNotFoundException;
import org.school.careflow.model.Appointment;
import org.school.careflow.model.AppointmentStatus;
import org.school.careflow.model.Doctor;
import org.school.careflow.model.Patient;
import org.school.careflow.repository.AppointmentRepository;
import org.school.careflow.repository.AvailabilityRepository;
import org.school.careflow.repository.DoctorRepository;
import org.school.careflow.repository.PatientRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final AvailabilityRepository availabilityRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Transactional
    public AppointmentResponse createAppointment(CreateAppointmentRequest request) {

        Doctor doctor = doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Doctor", "id", request.getDoctorId()));

        Patient patient = patientRepository.findById(request.getPatientId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Patient", "id", request.getPatientId()));

        LocalDateTime appointmentTime = request.getAppointmentTime();

        boolean isWithinAvailability =
                availabilityRepository.existsByDoctorIdAndStartTimeLessThanEqualAndEndTimeGreaterThan(
                        doctor.getId(),
                        appointmentTime,
                        appointmentTime
                );

        if (!isWithinAvailability) {
            throw new ResourceNotFoundException( "Le médecin n'est pas disponible à cette heure");
        }

        boolean alreadyBooked =
                appointmentRepository.existsByDoctorIdAndAppointmentTime(
                        doctor.getId(),
                        appointmentTime
                );

        if (alreadyBooked) {
            throw new ResourceNotFoundException("This time slot is already booked");
        }

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        Appointment saved = appointmentRepository.save(appointment);

        return mapToResponse(saved);
    }

    @Transactional
    public AppointmentResponse cancelAppointment(Long appointmentId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Appointment", "id", appointmentId));

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED) {
            throw new ResourceNotFoundException("Only scheduled appointments can be canceled");
        }

        if (appointment.getAppointmentTime().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new ResourceNotFoundException("Appointment cannot be canceled less than 1 hour before");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);

        return mapToResponse(appointment);
    }


    // get appointment by doctor id
    public List<AppointmentResponse> getAppointmentsByDoctorId(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", doctorId))
                .getAppointments()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private AppointmentResponse mapToResponse(Appointment appointment) {
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setDoctorId(appointment.getDoctor().getId());
        response.setPatientId(appointment.getPatient().getId());
        response.setAppointmentTime(appointment.getAppointmentTime());
        response.setStatus(appointment.getStatus());
        return response;
    }
}

