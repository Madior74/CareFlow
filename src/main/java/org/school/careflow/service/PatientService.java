package org.school.careflow.service;

import java.util.List;

import org.school.careflow.dto.CreatePatientRequest;
import org.school.careflow.dto.PatientResponse;
import org.school.careflow.dto.UpdatePatientRequest;
import org.school.careflow.exception.DuplicateResourceException;
import org.school.careflow.exception.ResourceNotFoundException;
import org.school.careflow.model.Patient;
import org.school.careflow.repository.PatientRepository;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;


    // create new patient
    @Transactional
    public PatientResponse createPatient(CreatePatientRequest createPatientRequest) {

        if (createPatientRequest.getEmail() != null && !createPatientRequest.getEmail().isEmpty() 
                && patientRepository.existsByEmail(createPatientRequest.getEmail())) {
            throw new DuplicateResourceException("Patient", "email", createPatientRequest.getEmail());
        }

        // Creation du patient
        Patient patient = new Patient();
        patient.setFirstname(createPatientRequest.getFirstname());
        patient.setLastname(createPatientRequest.getLastname());
        patient.setPhone(createPatientRequest.getPhone());
        patient.setEmail(createPatientRequest.getEmail());
        patient.setDateOfBirth(createPatientRequest.getDateOfBirth());
        patient.setGender(createPatientRequest.getGender());
        patient.setAddress(createPatientRequest.getAddress());
        patient.setBloodType(createPatientRequest.getBloodType());
        patient.setAllergies(createPatientRequest.getAllergies());
        patient.setEmergencyContactName(createPatientRequest.getEmergencyContactName());
        patient.setEmergencyContactPhone(createPatientRequest.getEmergencyContactPhone());

        Patient savedPatient = patientRepository.save(patient);
        return mapToPatientResponse(savedPatient);
    }

    // get Patient by id
    public PatientResponse getPatientById(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id.toString()));
        return mapToPatientResponse(patient);
    }

    // get all patients
    public List<PatientResponse> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(this::mapToPatientResponse)
                .toList();
    }

    // update patient
    @Transactional
    public PatientResponse updatePatient(Long id, UpdatePatientRequest updatePatientRequest) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Patient", "id", id.toString()));

        if (updatePatientRequest.getFirstname() != null) {
            patient.setFirstname(updatePatientRequest.getFirstname());
        }
        if (updatePatientRequest.getLastname() != null) {
            patient.setLastname(updatePatientRequest.getLastname());
        }
        if (updatePatientRequest.getPhone() != null) {
            patient.setPhone(updatePatientRequest.getPhone());
        }
        if (updatePatientRequest.getEmail() != null) {
            // Vérifier que l'email n'existe pas déjà pour un autre patient
            if (patientRepository.existsByEmail(updatePatientRequest.getEmail()) 
                    && !updatePatientRequest.getEmail().equals(patient.getEmail())) {
                throw new DuplicateResourceException("Patient", "email", updatePatientRequest.getEmail());
            }
            patient.setEmail(updatePatientRequest.getEmail());
        }
        if (updatePatientRequest.getDateOfBirth() != null) {
            patient.setDateOfBirth(updatePatientRequest.getDateOfBirth());
        }
        if (updatePatientRequest.getGender() != null) {
            patient.setGender(updatePatientRequest.getGender());
        }
        if (updatePatientRequest.getAddress() != null) {
            patient.setAddress(updatePatientRequest.getAddress());
        }
        if (updatePatientRequest.getBloodType() != null) {
            patient.setBloodType(updatePatientRequest.getBloodType());
        }
        if (updatePatientRequest.getAllergies() != null) {
            patient.setAllergies(updatePatientRequest.getAllergies());
        }
        if (updatePatientRequest.getEmergencyContactName() != null) {
            patient.setEmergencyContactName(updatePatientRequest.getEmergencyContactName());
        }
        if (updatePatientRequest.getEmergencyContactPhone() != null) {
            patient.setEmergencyContactPhone(updatePatientRequest.getEmergencyContactPhone());
        }

        Patient updatedPatient = patientRepository.save(patient);
        return mapToPatientResponse(updatedPatient);
    }

    //delete patient by id
    @Transactional
    public void deletePatientById(Long id){
        if (!patientRepository.existsById(id)) {
            throw new ResourceNotFoundException("Patient", "id", id.toString());
        }
        patientRepository.deleteById(id);
    }

    // mapper
    private PatientResponse mapToPatientResponse(Patient patient) {
        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setId(patient.getId());
        patientResponse.setFirstname(patient.getFirstname());
        patientResponse.setLastname(patient.getLastname());
        patientResponse.setEmail(patient.getEmail());
        patientResponse.setPhone(patient.getPhone());
        patientResponse.setDateOfBirth(patient.getDateOfBirth());
        patientResponse.setGender(patient.getGender());
        patientResponse.setAddress(patient.getAddress());
        patientResponse.setBloodType(patient.getBloodType());
        patientResponse.setAllergies(patient.getAllergies());
        patientResponse.setEmergencyContactName(patient.getEmergencyContactName());
        patientResponse.setEmergencyContactPhone(patient.getEmergencyContactPhone());
        return patientResponse;
    }
}
