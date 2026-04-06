package com.example.marker.controller;

import com.example.marker.dto.MarkResponse;
import com.example.marker.dto.SubjectResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/teacher")
@RequiredArgsConstructor
public class TeacherController {

    @GetMapping("/subjects")
    public List<SubjectResponse> getMySubjects() {
        return null;
    }

    @GetMapping("/subjects/{subjectId}/marks")
    public Page<MarkResponse> getSubjectMarksForTeacher(
            @PathVariable Long subjectId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return null;
    }
}
