package com.example.marker.controller;

import com.example.marker.dto.MarkResponse;
import com.example.marker.dto.SubjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/student")
@RequiredArgsConstructor
public class StudentController {

    @GetMapping("/subjects")
    public List<SubjectResponse> getMyEnrolledSubjects() {
        return null;
    }

    @GetMapping("/marks")
    public List<MarkResponse> getMyMarks() {
        return null;
    }

    @GetMapping("/marks/{subjectId}")
    public MarkResponse getMyMarkForSubject(@PathVariable Long subjectId) {
        return null;
    }
}
