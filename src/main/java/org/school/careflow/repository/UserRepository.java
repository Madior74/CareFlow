package org.school.careflow.repository;

import java.util.Optional;

import org.school.careflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Trouver un utilisateur par son email
    Optional<User> findByEmail(String email);

    // Vérifier si un email existe
    boolean existsByEmail(String email);
}
