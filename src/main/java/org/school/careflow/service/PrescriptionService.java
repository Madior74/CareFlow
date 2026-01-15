package org.school.careflow.service;

import java.util.List;

import org.school.careflow.dto.CreatePrescriptionRequest;
import org.school.careflow.dto.PrescriptionResponse;
import org.school.careflow.exception.ResourceNotFoundException;
import org.school.careflow.model.Diagnostic;
import org.school.careflow.model.Medication;
import org.school.careflow.model.Prescription;
import org.school.careflow.model.PrescriptionStatus;
import org.school.careflow.repository.DiagnosticRepository;
import org.school.careflow.repository.MedicationRepository;
import org.school.careflow.repository.PrescriptionRepository;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final DiagnosticRepository diagnosticRepository;
    private final MedicationRepository medicationRepository;

    @Transactional
    public PrescriptionResponse createPrescription(CreatePrescriptionRequest request) {
        Diagnostic diagnostic = diagnosticRepository.findById(request.getDiagnosticId())
                .orElseThrow(() -> new ResourceNotFoundException("Diagnostic", "id", request.getDiagnosticId().toString()));

        Medication medication = medicationRepository.findById(request.getMedicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Medication", "id", request.getMedicationId().toString()));

        Prescription prescription = new Prescription();
        prescription.setDiagnostic(diagnostic);
        prescription.setMedication(medication);
        prescription.setDosage(request.getDosage());
        prescription.setDurationDays(request.getDurationDays());
        prescription.setQuantity(request.getQuantity());
        prescription.setInstructions(request.getInstructions());
        prescription.setStatus(PrescriptionStatus.ACTIVE);

        Prescription saved = prescriptionRepository.save(prescription);
        return mapToResponse(saved);
    }

    public PrescriptionResponse getPrescriptionById(Long id) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription", "id", id.toString()));
        return mapToResponse(prescription);
    }

    public List<PrescriptionResponse> getAllPrescriptions() {
        return prescriptionRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    public PrescriptionResponse updatePrescription(Long id, CreatePrescriptionRequest request) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription", "id", id.toString()));

        Medication medication = medicationRepository.findById(request.getMedicationId())
                .orElseThrow(() -> new ResourceNotFoundException("Medication", "id", request.getMedicationId().toString()));

        prescription.setMedication(medication);
        prescription.setDosage(request.getDosage());
        prescription.setDurationDays(request.getDurationDays());
        prescription.setQuantity(request.getQuantity());
        prescription.setInstructions(request.getInstructions());

        Prescription updated = prescriptionRepository.save(prescription);
        return mapToResponse(updated);
    }

    @Transactional
    public void deletePrescriptionById(Long id) {
        if (!prescriptionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Prescription", "id", id.toString());
        }
        prescriptionRepository.deleteById(id);
    }

    // Status management
    @Transactional
    public PrescriptionResponse updateStatus(Long id, PrescriptionStatus status) {
        Prescription prescription = prescriptionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Prescription", "id", id.toString()));
        prescription.setStatus(status);
        Prescription updated = prescriptionRepository.save(prescription);
        return mapToResponse(updated);
    }

    private PrescriptionResponse mapToResponse(Prescription prescription) {
        PrescriptionResponse response = new PrescriptionResponse();
        response.setId(prescription.getId());
        response.setDiagnosticId(prescription.getDiagnostic().getId());
        response.setMedicationId(prescription.getMedication().getId());
        response.setDosage(prescription.getDosage());
        response.setDurationDays(prescription.getDurationDays());
        response.setQuantity(prescription.getQuantity());
        response.setInstructions(prescription.getInstructions());
        response.setStatus(prescription.getStatus());
        response.setStartDate(prescription.getStartDate());
        response.setEndDate(prescription.getEndDate());
        response.setCreatedAt(prescription.getCreatedAt());
        response.setUpdatedAt(prescription.getUpdatedAt());
        return response;
    }
}