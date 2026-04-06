package com.example.marker.repository;

import com.example.marker.model.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
    Optional<Subject> findByCode(String code);
    Page<Subject> findByActive(boolean active, Pageable pageable);
    Page<Subject> findByDepartmentAndActive(String department, boolean active, Pageable pageable);
    Page<Subject> findByTeacherIdAndActive(Long teacherId, boolean active, Pageable pageable);
}
