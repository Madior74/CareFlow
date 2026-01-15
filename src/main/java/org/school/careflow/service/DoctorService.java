package org.school.careflow.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.school.careflow.dto.CreateDoctorRequest;
import org.school.careflow.dto.DoctorResponse;
import org.school.careflow.dto.UpdateDoctorRequest;
import org.school.careflow.exception.DuplicateResourceException;
import org.school.careflow.exception.ResourceNotFoundException;
import org.school.careflow.model.Doctor;
import org.school.careflow.model.Role;
import org.school.careflow.model.RoleName;
import org.school.careflow.model.User;
import org.school.careflow.repository.DoctorRepository;
import org.school.careflow.repository.RoleRepository;
import org.school.careflow.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    // create new doctor
    @Transactional
    public DoctorResponse createDoctor(CreateDoctorRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Utilisateur", "email", request.getEmail());
        }

        Role doctorRole = roleRepository.findByName(RoleName.ROLE_DOCTOR)
                .orElseThrow(() -> new IllegalStateException("ROLE_DOCTOR missing"));

        // Création de l'utilisateur
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(doctorRole));
        user.setActive(true); // Activer le compte par défaut

        // Création du médecin
        Doctor doctor = new Doctor();
        doctor.setFirstname(request.getFirstname());
        doctor.setLastname(request.getLastname());
        doctor.setPhone(request.getPhone());
        doctor.setAddress(request.getAddress());
        doctor.setSpecialty(request.getSpecialty());
        doctor.setDateOfBirth(request.getDateOfBirth());
        doctor.setHireDate(request.getHireDate());
        doctor.setGender(request.getGender());
        doctor.setUser(user); // Lien avec l'utilisateur

        // Sauvegarde (cascade sauvera aussi l'User)
        Doctor savedDoctor = doctorRepository.save(doctor);

        // Retourner une réponse DTO
        return mapToDoctorResponse(savedDoctor);
    }

    // get doctor by id
    public DoctorResponse getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", id));
        return mapToDoctorResponse(doctor);
    }

    // get all doctors
    public List<DoctorResponse> getAllDoctors() {
        return doctorRepository.findAll()
                .stream()
                .map(this::mapToDoctorResponse)
                .collect(Collectors.toList());
    }

    // update doctor
    public DoctorResponse updateDoctor(Long id, UpdateDoctorRequest updateDoctorRequest) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", id.toString()));

        if (updateDoctorRequest.getFirstname() != null) {
            doctor.setFirstname(updateDoctorRequest.getFirstname());
        }
        if (updateDoctorRequest.getLastname() != null) {
            doctor.setLastname(updateDoctorRequest.getLastname());
        }
        if (updateDoctorRequest.getPhone() != null) {
            doctor.setPhone(updateDoctorRequest.getPhone());
        }
        if (updateDoctorRequest.getAddress() != null) {
            doctor.setAddress(updateDoctorRequest.getAddress());
        }
        if (updateDoctorRequest.getSpecialty() != null) {
            doctor.setSpecialty(updateDoctorRequest.getSpecialty());
        }
     
        if (updateDoctorRequest.getDateOfBirth() != null) {
            doctor.setDateOfBirth(updateDoctorRequest.getDateOfBirth());
        }
        if (updateDoctorRequest.getHireDate() != null) {
            doctor.setHireDate(updateDoctorRequest.getHireDate());
        }
        if (updateDoctorRequest.getGender() != null) {
            doctor.setGender(updateDoctorRequest.getGender());
        }

        Doctor updatedDoctor = doctorRepository.save(doctor);
        return mapToDoctorResponse(updatedDoctor);
    }

    // delete doctor by id
    public void deleteDoctorById(Long id) {
        doctorRepository.deleteById(id);
    }

    // activate/deactivate doctor
    public DoctorResponse toggleDoctorStatus(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", id.toString()));

        User user = doctor.getUser();
        user.setActive(!user.isActive()); // Toggle the active status

        Doctor updatedDoctor = doctorRepository.save(doctor);
        return mapToDoctorResponse(updatedDoctor);
    }

    private DoctorResponse mapToDoctorResponse(Doctor doctor) {
        DoctorResponse response = new DoctorResponse();
        response.setId(doctor.getId());
        response.setFirstname(doctor.getFirstname());
        response.setLastname(doctor.getLastname());
        response.setEmail(doctor.getUser().getEmail());
        response.setPhone(doctor.getPhone());
        response.setAddress(doctor.getAddress());
        response.setSpecialty(doctor.getSpecialty());
        response.setDateOfBirth(doctor.getDateOfBirth());
        response.setHireDate(doctor.getHireDate());
        response.setGender(doctor.getGender());
        response.setActive(doctor.getUser().isActive());
        return response;
    }
}