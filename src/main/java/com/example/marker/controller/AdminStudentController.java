package com.example.marker.controller;

import com.example.marker.dto.CreateStudentRequest;
import com.example.marker.dto.StudentResponse;
import com.example.marker.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/students")
@RequiredArgsConstructor
public class AdminStudentController {

    private final AdminService adminService;

    @GetMapping
    public Page<StudentResponse> listStudents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) String programme,
            @RequestParam(required = false) String semester,
            @RequestParam(required = false) Boolean active) {
        return adminService.listStudents(department, programme, semester, active, PageRequest.of(page, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse createStudent(@RequestBody CreateStudentRequest request) {
        return adminService.createStudent(request);
    }
}
