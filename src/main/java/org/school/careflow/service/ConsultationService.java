package org.school.careflow.service;

import java.util.List;

import org.school.careflow.dto.CreateConsultationRequest;
import org.school.careflow.dto.ConsultationResponse;
import org.school.careflow.exception.ResourceNotFoundException;
import org.school.careflow.model.Appointment;
import org.school.careflow.model.Consultation;
import org.school.careflow.repository.AppointmentRepository;
import org.school.careflow.repository.ConsultationRepository;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final AppointmentRepository appointmentRepository;

    // create new consultation
    @Transactional
    public ConsultationResponse createConsultation(CreateConsultationRequest request) {
        Appointment appointment = appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", request.getAppointmentId().toString()));

        if (consultationRepository.existsByAppointmentId(request.getAppointmentId())) {
            throw new IllegalArgumentException("Une consultation existe déjà pour ce rendez-vous");
        }

        Consultation consultation = new Consultation();
        consultation.setAppointment(appointment);
        consultation.setDoctor(appointment.getDoctor());
        consultation.setPatient(appointment.getPatient());
        consultation.setSymptoms(request.getSymptoms());
        consultation.setObservations(request.getObservations());
        consultation.setHeight(request.getHeight());
        consultation.setWeight(request.getWeight());
        consultation.setTemperature(request.getTemperature());
        consultation.setBloodPressure(request.getBloodPressure());

        Consultation saved = consultationRepository.save(consultation);
        return mapToResponse(saved);
    }

    // get consultation by id
    public ConsultationResponse getConsultationById(Long id) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "id", id.toString()));
        return mapToResponse(consultation);
    }

    // get all consultations
    public List<ConsultationResponse> getAllConsultations() {
        return consultationRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    // update consultation
    @Transactional
    public ConsultationResponse updateConsultation(Long id, CreateConsultationRequest request) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "id", id.toString()));

        consultation.setSymptoms(request.getSymptoms());
        consultation.setObservations(request.getObservations());
        consultation.setHeight(request.getHeight());
        consultation.setWeight(request.getWeight());
        consultation.setTemperature(request.getTemperature());
        consultation.setBloodPressure(request.getBloodPressure());

        Consultation updated = consultationRepository.save(consultation);
        return mapToResponse(updated);
    }

    // delete consultation by id
    @Transactional
    public void deleteConsultationById(Long id) {
        if (!consultationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Consultation", "id", id.toString());
        }
        consultationRepository.deleteById(id);
    }

    // mapper
    private ConsultationResponse mapToResponse(Consultation consultation) {
        ConsultationResponse response = new ConsultationResponse();
        response.setId(consultation.getId());
        response.setAppointmentId(consultation.getAppointment().getId());
        response.setDoctorId(consultation.getDoctor().getId());
        response.setPatientId(consultation.getPatient().getId());
        response.setSymptoms(consultation.getSymptoms());
        response.setObservations(consultation.getObservations());
        response.setHeight(consultation.getHeight());
        response.setWeight(consultation.getWeight());
        response.setTemperature(consultation.getTemperature());
        response.setBloodPressure(consultation.getBloodPressure());
        response.setBmi(consultation.calculateBMI());
        response.setCreatedAt(consultation.getCreatedAt());
        response.setUpdatedAt(consultation.getUpdatedAt());
        return response;
    }
}