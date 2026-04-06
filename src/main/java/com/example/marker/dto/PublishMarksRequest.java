package com.example.marker.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PublishMarksRequest {
    @NotNull
    private Long subjectId;
}
