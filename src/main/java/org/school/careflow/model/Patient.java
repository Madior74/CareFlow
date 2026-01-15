package org.school.careflow.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "patient", indexes = {
    @Index(name = "idx_patient_name", columnList = "lastname, firstname")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Patient first name cannot be null")
    @Size(min = 2, max = 100, message = "Patient first name should be between 2 and 100 characters")
    private String firstname;

    @NotNull(message = "Patient last name cannot be null")
    @Size(min = 2, max = 100, message = "Patient last name should be between 2 and 100 characters")
    private String lastname;

    @NotNull(message = "Phone number cannot be null")
    @Pattern(regexp = "^[0-9]{9}$", message = "Phone number must be 9 digits long")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "gender", length = 10)
    private String gender;

    @NotNull(message = "Address cannot be null")
    @Size(max = 255, message = "Address should not exceed 255 characters")
    private String address;

    @Column(name = "blood_type", length = 5)
    private String bloodType;

    @Column(name = "allergies", columnDefinition = "TEXT")
    private String allergies;

    @Column(name = "emergency_contact_name", length = 100)
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone", length = 20)
    private String emergencyContactPhone;

    // Relations
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MedicalHistory> medicalHistories;

    // Méthodes utilitaires
    public String getFullName() {
        return firstname + " " + lastname;
    }

    public Integer getAge() {
        if (dateOfBirth == null) return null;
        return LocalDate.now().getYear() - dateOfBirth.getYear();
    }
}