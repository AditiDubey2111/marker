package com.example.marker.repository;

import com.example.marker.model.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRollNumber(String rollNumber);
    Page<Student> findByDepartmentAndProgrammeAndSemesterAndActive(
            String department, String programme, String semester, boolean active, Pageable pageable);
    // Can add more flexible query methods or use Specification if needed,
    // but for now, simple ones will do or just find all and filter in service.
    // I'll add a few common ones.
}
