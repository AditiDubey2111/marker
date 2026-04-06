package com.example.marker.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
@DiscriminatorValue("TEACHER")
@Getter @Setter @NoArgsConstructor
public class Teacher extends User {

    @Column(length = 100)
    private String department;

    @Column(length = 20)
    private String employeeCode;

    // Subjects this teacher is responsible for
    @OneToMany(mappedBy = "teacher", fetch = FetchType.LAZY)
    private Set<Subject> subjects = new LinkedHashSet<>();
}
