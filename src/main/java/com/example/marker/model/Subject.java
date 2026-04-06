package com.example.marker.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "subjects",
       uniqueConstraints = @UniqueConstraint(columnNames = {"code"}))
@Getter @Setter @NoArgsConstructor
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String code;                // e.g. "CS301"

    @Column(nullable = false, length = 150)
    private String name;

    @Column
    private Integer creditHours;

    @Column(length = 100)
    private String department;

    @Column(nullable = false)
    private boolean active = true;

    // Teacher who teaches this subject
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    // All mark records for this subject (one per enrolled student)
    @OneToMany(mappedBy = "subject",
               cascade = CascadeType.ALL,
               orphanRemoval = true,
               fetch = FetchType.LAZY)
    private Set<Mark> marks = new LinkedHashSet<>();
}
