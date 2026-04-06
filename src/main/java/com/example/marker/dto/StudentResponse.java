package com.example.marker.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class StudentResponse extends UserBaseDto {
    private String rollNumber;
    private String programme;
    private String semester;
    private String department;
}
