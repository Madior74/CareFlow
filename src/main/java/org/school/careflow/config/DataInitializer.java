package org.school.careflow.config;
import java.util.Set;

import org.school.careflow.model.Role;
import org.school.careflow.model.RoleName;
import org.school.careflow.model.User;
import org.school.careflow.repository.RoleRepository;
import org.school.careflow.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        initRoles();
        initAdmin();
    }

    private void initRoles() {
        for (RoleName roleName : RoleName.values()) {
            roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(
                    new Role(null, roleName)
                ));
        }
    }

    private void initAdmin() {
        // Créer l'administrateur
        String adminEmail = "admin@clinic.local";
        if (!userRepository.existsByEmail(adminEmail)) {
            Role adminRole = roleRepository.findByName(RoleName.ROLE_ADMIN)
                .orElseThrow(() -> new IllegalStateException("ROLE_ADMIN missing"));

            User admin = new User();
            admin.setEmail(adminEmail);
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setActive(true);
            admin.setRoles(Set.of(adminRole));
            userRepository.save(admin);
        }

        // Créer un compte médecin de test
        String doctorEmail = "doctor@clinic.local";
        if (!userRepository.existsByEmail(doctorEmail)) {
            Role doctorRole = roleRepository.findByName(RoleName.ROLE_DOCTOR)
                .orElseThrow(() -> new IllegalStateException("ROLE_DOCTOR missing"));

            User doctor = new User();
            doctor.setEmail(doctorEmail);
            doctor.setPassword(passwordEncoder.encode("doctor123"));
            doctor.setActive(true);
            doctor.setRoles(Set.of(doctorRole));
            userRepository.save(doctor);
        }

        // Créer un compte personnel de test
        String staffEmail = "staff@clinic.local";
        if (!userRepository.existsByEmail(staffEmail)) {
            Role staffRole = roleRepository.findByName(RoleName.ROLE_STAFF)
                .orElseThrow(() -> new IllegalStateException("ROLE_STAFF missing"));

            User staff = new User();
            staff.setEmail(staffEmail);
            staff.setPassword(passwordEncoder.encode("staff123"));
            staff.setActive(true);
            staff.setRoles(Set.of(staffRole));
            userRepository.save(staff);
        }
    }



    //L’initialisation est idempotente, centralisée et garantit l’existence des rôles et d’un administrateur système.
}
