package org.school.careflow.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.school.careflow.dto.AuthResponse;
import org.school.careflow.exception.DuplicateResourceException;
import org.school.careflow.model.Role;
import org.school.careflow.model.RoleName;
import org.school.careflow.model.User;
import org.school.careflow.repository.RoleRepository;
import org.school.careflow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    // /**
    //  * Enregistrer un nouvel utilisateur
    //  */
    // @Transactional
    // public AuthResponse registerUser(RegisterRequest request) {
    //     // Vérifier si l'email existe déjà
    //     if (userRepository.findByEmail(request.getEmail()).isPresent()) {
    //         throw new DuplicateResourceException("Utilisateur", "email", request.getEmail());
    //     }

    //     // Créer l'utilisateur
    //     User user = new User();
    //     user.setEmail(request.getEmail());
    //     user.setPassword(passwordEncoder.encode(request.getPassword()));
    //     user.setActive(true);

    //     // Assigner le rôle
    //     RoleName roleName;
    //     try {
    //         roleName = RoleName.valueOf("ROLE_" + request.getRole().toUpperCase());
    //     } catch (IllegalArgumentException e) {
    //         throw new RuntimeException("Rôle invalide: " + request.getRole());
    //     }

    //     Role role = roleRepository.findByName(roleName)
    //             .orElseThrow(() -> new RuntimeException("Rôle non trouvé: " + roleName));
        
    //     Set<Role> roles = new HashSet<>();
    //     roles.add(role);
    //     user.setRoles(roles);

    //     User savedUser = userRepository.save(user);

    //     // Générer le token
    //     UserDetails userDetails = createUserDetails(savedUser);
    //     String token = jwtService.generateToken(userDetails);

    //     return new AuthResponse(token, savedUser.getId(), savedUser.getEmail(), "Inscription réussie");
    // }

    /**
     * Authentifier un utilisateur et générer un token JWT
     */
    public AuthResponse authenticateUser(String email, String password) {
        // Trouver l'utilisateur par email
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Username/Email ou mot de passe incorrect"));

        // Vérifier que le compte est actif
        if (!user.isActive()) {
            throw new RuntimeException("Votre compte est désactivé");
        }

        // Vérifier le mot de passe
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Username/Email ou mot de passe incorrect");
        }

        // Créer UserDetails pour générer le token
        UserDetails userDetails = createUserDetails(user);

        // Générer le token JWT
        String token = jwtService.generateToken(userDetails);

        return new AuthResponse(token, user.getId(), user.getEmail(), "Connexion réussie");
    }

    /**
     * Rafraîchir le token JWT
     */
    public AuthResponse refreshToken(String token) {
        try {
            // Extraire l'email du token
            String email = jwtService.extractUsername(token);

            // Trouver l'utilisateur
            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // Vérifier que le compte est actif
            if (!user.isActive()) {
                throw new RuntimeException("Votre compte est désactivé");
            }

            // Créer UserDetails et générer un nouveau token
            UserDetails userDetails = createUserDetails(user);
            String newToken = jwtService.generateToken(userDetails);

            return new AuthResponse(newToken, user.getId(), user.getEmail(), "Token rafraîchi avec succès");
        } catch (Exception e) {
            throw new RuntimeException("Token invalide ou expiré");
        }
    }

    /**
     * Obtenir l'email de l'utilisateur à partir du token
     */
    public String getUserEmailFromToken(String token) {
        try {
            return jwtService.extractUsername(token);
        } catch (Exception e) {
            throw new RuntimeException("Token invalide");
        }
    }

    /**
     * Trouver un utilisateur par son email
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Créer UserDetails à partir d'un utilisateur
     */
    private UserDetails createUserDetails(User user) {
        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream()
                        .map(role -> role.getName().name())
                        .toArray(String[]::new))
                .build();
    }
}
