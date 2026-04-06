package com.example.marker.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EnterMarksRequest {
    @DecimalMin("0.0")
    private BigDecimal internalMarks;

    @DecimalMin("0.0")
    private BigDecimal externalMarks;

    @Size(max = 5)
    private String grade;

    @Size(max = 500)
    private String remarks;
}
