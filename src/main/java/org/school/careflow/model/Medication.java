package org.school.careflow.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "medications", indexes = {
    @Index(name = "idx_medication_name", columnList = "name")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Medication extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 200)
    private String name;

    @Column(name = "dosage_form", length = 50)
    private String dosageForm; // Comprimé, sirop, injection, etc.

    @Column(name = "active_ingredient", columnDefinition = "TEXT")
    private String activeIngredient;

    @Column(name = "strength", length = 50)
    private String strength; // 500mg, 1000mg, etc.

    @Column(name = "manufacturer", length = 100)
    private String manufacturer;
}
