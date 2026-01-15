package org.school.careflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "consultations", indexes = {
    @Index(name = "idx_consultation_appointment", columnList = "appointment_id"),
    @Index(name = "idx_consultation_patient", columnList = "patient_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Consultation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "appointment_id", unique = true, nullable = false)
    private Appointment appointment;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @Column(name = "symptoms", columnDefinition = "TEXT")
    private String symptoms;

    @Column(name = "observations", columnDefinition = "TEXT")
    private String observations;

    @Column(name = "height", precision = 5, scale = 2)
    private BigDecimal height; // en cm

    @Column(name = "weight", precision = 5, scale = 2)
    private BigDecimal weight; // en kg

    @Column(name = "temperature", precision = 4, scale = 1)
    private BigDecimal temperature;

    @Column(name = "blood_pressure", length = 20)
    private String bloodPressure;

    // Relations
    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Diagnostic> diagnostics;

    // Méthodes utilitaires
    public BigDecimal calculateBMI() {
        if (height == null || weight == null || height.compareTo(BigDecimal.ZERO) == 0) {
            return null;
        }
        // Convert cm to meters
        BigDecimal heightInMeters = height.divide(BigDecimal.valueOf(100), 2, BigDecimal.ROUND_HALF_UP);
        return weight.divide(heightInMeters.multiply(heightInMeters), 2, BigDecimal.ROUND_HALF_UP);
    }
}