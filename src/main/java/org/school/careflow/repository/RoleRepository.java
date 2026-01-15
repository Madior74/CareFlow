package org.school.careflow.repository;

import java.util.Optional;

import org.school.careflow.model.Role;
import org.school.careflow.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    
    // Trouver un rôle par son nom
    Optional<Role> findByName(RoleName name);
}