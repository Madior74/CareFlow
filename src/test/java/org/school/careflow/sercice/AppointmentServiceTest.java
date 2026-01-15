package org.school.careflow.sercice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.school.careflow.dto.AppointmentResponse;
import org.school.careflow.dto.CreateAppointmentRequest;
import org.school.careflow.exception.ResourceNotFoundException;
import org.school.careflow.model.Appointment;
import org.school.careflow.model.AppointmentStatus;
import org.school.careflow.model.Availability;
import org.school.careflow.model.Doctor;
import org.school.careflow.model.Patient;
import org.school.careflow.repository.AppointmentRepository;
import org.school.careflow.repository.AvailabilityRepository;
import org.school.careflow.repository.DoctorRepository;
import org.school.careflow.repository.PatientRepository;
import org.school.careflow.service.AppointmentService;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @InjectMocks
    private AppointmentService appointmentService;

    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private AvailabilityRepository availabilityRepository;

    @Test
    void should_create_appointment_successfully() {
        // GIVEN
        Long doctorId = 1L;
        Long patientId = 2L;
        LocalDateTime appointmentTime = LocalDateTime.of(2025, 1, 10, 10, 0);

        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        Patient patient = new Patient();
        patient.setId(patientId);

        Availability availability = new Availability();
        availability.setStartTime(appointmentTime.minusMinutes(30));
        availability.setEndTime(appointmentTime.plusMinutes(30));

        Appointment savedAppointment = new Appointment();
        savedAppointment.setId(100L);
        savedAppointment.setDoctor(doctor);
        savedAppointment.setPatient(patient);
        savedAppointment.setAppointmentTime(appointmentTime);
        savedAppointment.setStatus(AppointmentStatus.SCHEDULED);

        when(doctorRepository.findById(doctorId))
                .thenReturn(Optional.of(doctor));

        when(patientRepository.findById(patientId))
                .thenReturn(Optional.of(patient));

        when(availabilityRepository
                .existsByDoctorIdAndStartTimeLessThanEqualAndEndTimeGreaterThan(doctorId, appointmentTime,
                        appointmentTime))
                .thenReturn(true);

        when(appointmentRepository
                .existsByDoctorIdAndAppointmentTime(doctorId, appointmentTime))
                .thenReturn(false);

        when(appointmentRepository.save(any(Appointment.class)))
                .thenReturn(savedAppointment);

        // WHEN
        CreateAppointmentRequest request = new CreateAppointmentRequest();
        request.setDoctorId(doctorId);
        request.setPatientId(patientId);
        request.setAppointmentTime(appointmentTime);

        AppointmentResponse result = appointmentService
                .createAppointment(request);

        // THEN
        assertNotNull(result);
        assertEquals(doctorId, result.getDoctorId());
        assertEquals(patientId, result.getPatientId());
        assertEquals(AppointmentStatus.SCHEDULED, result.getStatus());

        verify(appointmentRepository).save(any(Appointment.class));
    }

    @Test
    void should_throw_exception_when_appointment_is_outside_availability() {
        // GIVEN
        Long doctorId = 1L;
        Long patientId = 2L;
        LocalDateTime appointmentTime = LocalDateTime.of(2025, 1, 10, 18, 0);

        Doctor doctor = new Doctor();
        doctor.setId(doctorId);

        Patient patient = new Patient();
        patient.setId(patientId);

        when(doctorRepository.findById(doctorId))
                .thenReturn(Optional.of(doctor));

        when(patientRepository.findById(patientId))
                .thenReturn(Optional.of(patient));

        // ❌ aucune disponibilité pour cette heure
        when(availabilityRepository
                .existsByDoctorIdAndStartTimeLessThanEqualAndEndTimeGreaterThan(doctorId, appointmentTime, appointmentTime))
                .thenReturn(false);

        // WHEN + THEN
        CreateAppointmentRequest request = new CreateAppointmentRequest();
        request.setDoctorId(doctorId);
        request.setPatientId(patientId);
        request.setAppointmentTime(appointmentTime);

        assertThrows(
                ResourceNotFoundException.class,
                () -> appointmentService.createAppointment(request)
        );

        verify(appointmentRepository, never()).save(any());
    }


    @Test
void should_throw_exception_when_doctor_not_found() {
    // GIVEN
    Long doctorId = 99L;
    Long patientId = 1L;
    LocalDateTime appointmentTime = LocalDateTime.now();

    when(doctorRepository.findById(doctorId))
            .thenReturn(Optional.empty());

    // WHEN + THEN
    CreateAppointmentRequest request = new CreateAppointmentRequest();
    request.setDoctorId(doctorId);
    request.setPatientId(patientId);
    request.setAppointmentTime(appointmentTime);

    assertThrows(ResourceNotFoundException.class, () ->
            appointmentService.createAppointment(request)
    );
    

    verify(appointmentRepository, never()).save(any());
}






}
