package org.school.careflow.model;

public enum RoleName {
    ROLE_ADMIN,
    ROLE_DOCTOR,
    ROLE_SECRETARY,  // Ajouté pour la secrétaire
    ROLE_STAFF;
    
    // Optionnel: pour avoir un nom affichable
    public String getDisplayName() {
        return this.name().substring(5).toLowerCase();
    }
}