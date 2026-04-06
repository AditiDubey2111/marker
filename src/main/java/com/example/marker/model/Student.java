package com.example.marker.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "students")
@DiscriminatorValue("STUDENT")
@Getter @Setter @NoArgsConstructor
public class Student extends User {

    @Column(nullable = false, unique = true, length = 20)
    private String rollNumber;

    @Column(length = 50)
    private String programme;       // e.g. B.Tech, M.Sc

    @Column(length = 10)
    private String semester;        // e.g. "3rd"

    @Column(length = 100)
    private String department;

    // All mark records for this student (one per enrolled subject)
    @OneToMany(mappedBy = "student",
               cascade = CascadeType.ALL,
               orphanRemoval = true,
               fetch = FetchType.LAZY)
    private Set<Mark> marks = new LinkedHashSet<>();
}
