package org.school.careflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "prescriptions", indexes = {
    @Index(name = "idx_prescription_diagnostic", columnList = "diagnostic_id"),
    @Index(name = "idx_prescription_status", columnList = "status")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prescription extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "diagnostic_id", nullable = false)
    private Diagnostic diagnostic;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    @Column(name = "dosage", columnDefinition = "TEXT", nullable = false)
    private String dosage; // Ex: "1 comprimé matin et soir"

    @Column(name = "duration_days", nullable = false)
    private Integer durationDays;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "instructions", columnDefinition = "TEXT")
    private String instructions;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PrescriptionStatus status = PrescriptionStatus.ACTIVE;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    // Validation
    @PrePersist
    @PreUpdate
    private void calculateDates() {
        if (startDate == null) {
            startDate = LocalDateTime.now();
        }
        if (durationDays != null && startDate != null) {
            endDate = startDate.plusDays(durationDays);
        }
    }

}