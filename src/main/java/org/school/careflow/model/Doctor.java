package org.school.careflow.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "doctor", indexes = {
    @Index(name = "idx_doctor_user_id", columnList = "user_id"),
    @Index(name = "idx_doctor_name", columnList = "lastname, firstname")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "First name cannot be null")
    @Size(min = 2, max = 100, message = "First name should be between 2 and 100 characters")
    private String firstname;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, max = 100, message = "Last name should be between 2 and 100 characters")
    private String lastname;

    @NotNull(message = "Phone cannot be null")
    @Column(name = "phone", length = 50)
    private String phone;

    @NotNull(message = "Address cannot be null")
    @Size(max = 255, message = "Address should not exceed 255 characters")
    private String address;

    @OneToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @Column(name = "specialty", length = 100)
    private String specialty;


    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    @Column(name = "gender", length = 10)
    private String gender;

    // Relations
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Availability> availabilities;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Appointment> appointments;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consultation> consultations;

    // Méthodes utilitaires
    public String getFullName() {
        return firstname + " " + lastname;
    }
}
