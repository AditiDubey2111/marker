package com.example.marker.controller;

import com.example.marker.dto.EnrolmentRequest;
import com.example.marker.dto.MarkResponse;
import com.example.marker.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/enrolments")
@RequiredArgsConstructor
public class AdminEnrolmentController {

    private final AdminService adminService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MarkResponse enrolStudent(@RequestBody EnrolmentRequest request) {
        return null; // adminService.enrolStudent(request);
    }

    @DeleteMapping("/{markId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void unenrolStudent(@PathVariable Long markId) {
        // adminService.unenrolStudent(markId);
    }
}
