package com.example.marker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EnrolmentRequest {
    @NotNull
    private Long studentId;

    @NotNull
    private Long subjectId;

    private BigDecimal maxMarks = BigDecimal.valueOf(100.0);
}
