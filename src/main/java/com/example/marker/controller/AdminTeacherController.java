package com.example.marker.controller;

import com.example.marker.dto.CreateTeacherRequest;
import com.example.marker.dto.TeacherResponse;
import com.example.marker.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/teachers")
@RequiredArgsConstructor
public class AdminTeacherController {

    private final AdminService adminService;

    @GetMapping
    public Page<TeacherResponse> listTeachers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Boolean active) {
        return adminService.listTeachers(department, active, PageRequest.of(page, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TeacherResponse createTeacher(@RequestBody CreateTeacherRequest request) {
        return adminService.createTeacher(request);
    }
}
