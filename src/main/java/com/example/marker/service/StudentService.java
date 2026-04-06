package com.example.marker.service;

import com.example.marker.dto.MarkResponse;
import com.example.marker.model.Mark;
import com.example.marker.model.Student;
import com.example.marker.repository.MarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {

    private final MarkRepository markRepository;

    public List<MarkResponse> getMyPublishedMarks(Student student) {
        // 5. Students can only view their own marks, and only when status is PUBLISHED
        return markRepository.findByStudentIdAndStatus(student.getId(), Mark.Status.PUBLISHED, null)
                .getContent()
                .stream()
                .map(this::mapToMarkResponse)
                .collect(Collectors.toList());
    }

    private MarkResponse mapToMarkResponse(Mark mark) {
        // Mocking mapping logic
        MarkResponse response = new MarkResponse();
        response.setId(mark.getId());
        response.setInternalMarks(mark.getInternalMarks());
        response.setExternalMarks(mark.getExternalMarks());
        response.setTotalMarks(mark.getTotalMarks());
        response.setMaxMarks(mark.getMaxMarks());
        response.setGrade(mark.getGrade());
        response.setStatus(mark.getStatus());
        return response;
    }
}
