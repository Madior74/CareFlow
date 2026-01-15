package org.school.careflow.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "diagnostics", indexes = {
    @Index(name = "idx_diagnostic_consultation", columnList = "consultation_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Diagnostic extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "consultation_id", nullable = false)
    private Consultation consultation;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "diagnosis_code", length = 20)
    private String diagnosisCode; // Code CIM-10

    @Column(name = "diagnosis_text", columnDefinition = "TEXT", nullable = false)
    private String diagnosisText;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    // Relations
    @OneToMany(mappedBy = "diagnostic", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Prescription> prescriptions;
}