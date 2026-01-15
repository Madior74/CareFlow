package org.school.careflow.service;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.school.careflow.dto.CreateStaffRequest;
import org.school.careflow.dto.StaffResponse;
import org.school.careflow.dto.UpdateStaffRequest;
import org.school.careflow.exception.DuplicateResourceException;
import org.school.careflow.exception.ResourceNotFoundException;
import org.school.careflow.model.Role;
import org.school.careflow.model.RoleName;
import org.school.careflow.model.Staff;
import org.school.careflow.model.User;
import org.school.careflow.repository.RoleRepository;
import org.school.careflow.repository.StaffRepository;
import org.school.careflow.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Créer un nouveau membre du personnel
     */
    @Transactional
    public StaffResponse createStaff(CreateStaffRequest request) {
        // Vérifier si l'email existe déjà
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Utilisateur", "email", request.getEmail());
        }

        // Créer l'utilisateur
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setActive(true);

        // Assigner le rôle STAFF
        Role staffRole = roleRepository.findByName(RoleName.ROLE_STAFF)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "name", "ROLE_STAFF"));
        Set<Role> roles = new HashSet<>();
        roles.add(staffRole);
        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        // Créer le staff
        Staff staff = new Staff();
        staff.setUser(savedUser);
        staff.setFirstname(request.getFirstname());
        staff.setLastname(request.getLastname());
        staff.setPhone(request.getPhone());
        staff.setAddress(request.getAddress());
        staff.setPosition(request.getPosition());
        staff.setDepartment(request.getDepartment());
        staff.setHireDate(request.getHireDate() != null ? request.getHireDate() : LocalDate.now());

        Staff savedStaff = staffRepository.save(staff);

        return mapToStaffResponse(savedStaff);
    }

    /**
     * Récupérer un membre du personnel par son ID
     */
    public StaffResponse getStaffById(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "id", id.toString()));
        return mapToStaffResponse(staff);
    }

    /**
     * Récupérer tous les membres du personnel
     */
    public List<StaffResponse> getAllStaff() {
        return staffRepository.findAll().stream()
                .map(this::mapToStaffResponse)
                .collect(Collectors.toList());
    }

    /**
     * Mettre à jour un membre du personnel
     */
    @Transactional
    public StaffResponse updateStaff(Long id, UpdateStaffRequest request) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "id", id.toString()));

        // Mettre à jour les champs uniquement s'ils sont fournis
        if (request.getFirstname() != null && !request.getFirstname().isBlank()) {
            staff.setFirstname(request.getFirstname());
        }
        if (request.getLastname() != null && !request.getLastname().isBlank()) {
            staff.setLastname(request.getLastname());
        }
        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            staff.setPhone(request.getPhone());
        }
        if (request.getAddress() != null && !request.getAddress().isBlank()) {
            staff.setAddress(request.getAddress());
        }
        if (request.getPosition() != null && !request.getPosition().isBlank()) {
            staff.setPosition(request.getPosition());
        }
        if (request.getDepartment() != null && !request.getDepartment().isBlank()) {
            staff.setDepartment(request.getDepartment());
        }
        if (request.getHireDate() != null) {
            staff.setHireDate(request.getHireDate());
        }

        Staff updatedStaff = staffRepository.save(staff);
        return mapToStaffResponse(updatedStaff);
    }

    /**
     * Supprimer un membre du personnel
     */
    @Transactional
    public void deleteStaff(Long id) {
        Staff staff = staffRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "id", id.toString()));
        
        // Désactiver l'utilisateur au lieu de le supprimer complètement
        User user = staff.getUser();
        user.setActive(false);
        userRepository.save(user);
        
        // Supprimer le staff
        staffRepository.delete(staff);
    }

    /**
     * Récupérer un membre du personnel par l'ID de l'utilisateur
     */
    public StaffResponse getStaffByUserId(Long userId) {
        Staff staff = staffRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Staff", "userId", userId.toString()));
        return mapToStaffResponse(staff);
    }

    /**
     * Mapper Staff vers StaffResponse
     */
    private StaffResponse mapToStaffResponse(Staff staff) {
        StaffResponse response = new StaffResponse();
        response.setId(staff.getId());
        response.setUserId(staff.getUser().getId());
        response.setEmail(staff.getUser().getEmail());
        response.setFirstname(staff.getFirstname());
        response.setLastname(staff.getLastname());
        response.setPhone(staff.getPhone());
        response.setAddress(staff.getAddress());
        response.setPosition(staff.getPosition());
        response.setDepartment(staff.getDepartment());
        response.setHireDate(staff.getHireDate());
        response.setCreatedAt(staff.getCreatedAt());
        response.setUpdatedAt(staff.getUpdatedAt());
        response.setActive(staff.getUser().isActive());
        return response;
    }
}
