package com.example.marker.controller;

import com.example.marker.dto.EnterMarksRequest;
import com.example.marker.dto.MarkResponse;
import com.example.marker.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/marks")
@RequiredArgsConstructor
public class MarkController {

    private final MarkService markService;

    @GetMapping("/{markId}")
    public MarkResponse getMark(@PathVariable Long markId) {
        return null;
    }

    @PutMapping("/{markId}")
    public MarkResponse enterMarks(@PathVariable Long markId, @RequestBody EnterMarksRequest request) {
        // In a real app, we'd get the teacher from the security context
        // return markService.enterMarks(markId, request, currentTeacher);
        return null;
    }
}
