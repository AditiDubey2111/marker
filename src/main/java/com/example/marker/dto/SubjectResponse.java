package com.example.marker.dto;

import lombok.Data;

@Data
public class SubjectResponse {
    private Long id;
    private String code;
    private String name;
    private Integer creditHours;
    private String department;
    private boolean active;
    private TeacherResponse teacher;
}
