package com.example.marker.controller;

import com.example.marker.dto.CreateSubjectRequest;
import com.example.marker.dto.SubjectResponse;
import com.example.marker.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final AdminService adminService;

    @GetMapping
    public Page<SubjectResponse> listSubjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Long teacherId) {
        return adminService.listSubjects(department, teacherId, PageRequest.of(page, size));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectResponse createSubject(@RequestBody CreateSubjectRequest request) {
        return adminService.createSubject(request);
    }

    @GetMapping("/{subjectId}")
    public SubjectResponse getSubject(@PathVariable Long subjectId) {
        // Simple mock for now
        return null; 
    }
}
