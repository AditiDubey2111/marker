package com.example.marker.repository;

import com.example.marker.model.Mark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarkRepository extends JpaRepository<Mark, Long> {
    Optional<Mark> findByStudentIdAndSubjectId(Long studentId, Long subjectId);
    boolean existsByStudentIdAndSubjectId(Long studentId, Long subjectId);
    
    Page<Mark> findBySubjectId(Long subjectId, Pageable pageable);
    Page<Mark> findByStudentId(Long studentId, Pageable pageable);
    Page<Mark> findByStatus(Mark.Status status, Pageable pageable);
    
    Page<Mark> findBySubjectIdAndStatus(Long subjectId, Mark.Status status, Pageable pageable);
    Page<Mark> findByStudentIdAndStatus(Long studentId, Mark.Status status, Pageable pageable);
    
    List<Mark> findBySubjectIdAndStatus(Long subjectId, Mark.Status status);
}
