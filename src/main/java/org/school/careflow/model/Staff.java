package org.school.careflow.model;

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

@Entity
@Table(name = "staff", indexes = {
    @Index(name = "idx_staff_user_id", columnList = "user_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Staff extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @NotNull(message = "First name cannot be null")
    @Size(min = 2, max = 100, message = "First name should be between 2 and 100 characters")
    private String firstname;

    @NotNull(message = "Last name cannot be null")
    @Size(min = 2, max = 100, message = "Last name should be between 2 and 100 characters")
    private String lastname;

    @NotNull(message = "Phone cannot be null")
    @Column(name = "phone", length = 20)
    private String phone;

    @NotNull(message = "Address cannot be null")
    @Size(max = 255, message = "Address should not exceed 255 characters")
    private String address;

    @Column(name = "position", length = 100)
    private String position;

    @Column(name = "department", length = 100)
    private String department;

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate;

    // Méthodes utilitaires
    public String getFullName() {
        return firstname + " " + lastname;
    }
}