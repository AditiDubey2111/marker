package com.example.marker.controller;

import com.example.marker.dto.MarkResponse;
import com.example.marker.dto.PublishMarksRequest;
import com.example.marker.model.Mark;
import com.example.marker.service.MarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin/marks")
@RequiredArgsConstructor
public class AdminMarkController {

    private final MarkService markService;

    @GetMapping
    public Page<MarkResponse> listMarks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) Long subjectId,
            @RequestParam(required = false) Long studentId,
            @RequestParam(required = false) Mark.Status status) {
        return null;
    }

    @PostMapping("/publish")
    public Map<String, Integer> publishMarks(@RequestBody PublishMarksRequest request) {
        int publishedCount = markService.publishMarks(request.getSubjectId());
        return Map.of("publishedCount", publishedCount);
    }
}
