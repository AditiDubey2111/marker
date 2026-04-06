package com.example.marker.repository;

import com.example.marker.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findByEmployeeCode(String employeeCode);
    Page<Teacher> findByDepartmentAndActive(String department, boolean active, Pageable pageable);
    Page<Teacher> findByDepartment(String department, Pageable pageable);
    Page<Teacher> findByActive(boolean active, Pageable pageable);
}
