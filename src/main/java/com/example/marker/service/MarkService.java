package com.example.marker.service;

import com.example.marker.dto.*;
import com.example.marker.model.*;
import com.example.marker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarkService {

    private final MarkRepository markRepository;
    private final SubjectRepository subjectRepository;
    private final StudentRepository studentRepository;

    @Transactional
    public MarkResponse enterMarks(Long markId, EnterMarksRequest request, Teacher teacher) {
        Mark mark = markRepository.findById(markId)
                .orElseThrow(() -> new RuntimeException("Mark record not found"));

        // 1. A teacher may only enter marks for subjects assigned to them
        if (!mark.getSubject().getTeacher().getId().equals(teacher.getId())) {
            throw new RuntimeException("Unauthorized: This subject is not assigned to you.");
        }

        // 2. Marks with status PUBLISHED cannot be edited
        if (mark.getStatus() == Mark.Status.PUBLISHED) {
            throw new RuntimeException("Conflict: Published marks cannot be edited.");
        }

        // Update marks
        if (request.getInternalMarks() != null) mark.setInternalMarks(request.getInternalMarks());
        if (request.getExternalMarks() != null) mark.setExternalMarks(request.getExternalMarks());
        if (request.getGrade() != null) mark.setGrade(request.getGrade());
        if (request.getRemarks() != null) mark.setRemarks(request.getRemarks());
        
        mark.setEnteredBy(teacher);
        mark.setMarksUpdatedAt(LocalDateTime.now());
        
        // Mark.recalculate() is called via @PreUpdate/@PrePersist
        
        return mapToMarkResponse(markRepository.save(mark));
    }

    @Transactional
    public int publishMarks(Long subjectId) {
        // 4. Only Admin can publish marks for a subject (enforced at controller/security layer)
        List<Mark> marks = markRepository.findBySubjectIdAndStatus(subjectId, Mark.Status.MARKS_ENTERED);
        for (Mark mark : marks) {
            mark.setStatus(Mark.Status.PUBLISHED);
        }
        markRepository.saveAll(marks);
        return marks.size();
    }

    private MarkResponse mapToMarkResponse(Mark mark) {
        // Mocking mapping logic
        MarkResponse response = new MarkResponse();
        response.setId(mark.getId());
        response.setInternalMarks(mark.getInternalMarks());
        response.setExternalMarks(mark.getExternalMarks());
        response.setTotalMarks(mark.getTotalMarks());
        response.setMaxMarks(mark.getMaxMarks());
        response.setGrade(mark.getGrade());
        response.setStatus(mark.getStatus());
        response.setRemarks(mark.getRemarks());
        response.setMarksUpdatedAt(mark.getMarksUpdatedAt());
        // ... set student, subject, teacher responses ...
        return response;
    }
}
