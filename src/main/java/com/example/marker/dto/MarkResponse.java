package com.example.marker.dto;

import com.example.marker.model.Mark;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MarkResponse {
    private Long id;
    private StudentResponse student;
    private SubjectResponse subject;
    private TeacherResponse enteredBy;
    private BigDecimal internalMarks;
    private BigDecimal externalMarks;
    private BigDecimal totalMarks;
    private BigDecimal maxMarks;
    private String grade;
    private Mark.Status status;
    private String remarks;
    private LocalDateTime marksUpdatedAt;
}
