package com.example.marker.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TeacherResponse extends UserBaseDto {
    private String department;
    private String employeeCode;
}
