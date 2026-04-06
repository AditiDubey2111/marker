package com.example.marker.service;

import com.example.marker.dto.*;
import com.example.marker.model.*;
import com.example.marker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final MarkRepository markRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public TeacherResponse createTeacher(CreateTeacherRequest request) {
        Teacher teacher = new Teacher();
        teacher.setUsername(request.getUsername());
        teacher.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        teacher.setFullName(request.getFullName());
        teacher.setEmail(request.getEmail());
        teacher.setDepartment(request.getDepartment());
        teacher.setEmployeeCode(request.getEmployeeCode());
        
        return mapToTeacherResponse(teacherRepository.save(teacher));
    }

    public Page<TeacherResponse> listTeachers(String department, Boolean active, Pageable pageable) {
        if (department != null && active != null) {
            return teacherRepository.findByDepartmentAndActive(department, active, pageable).map(this::mapToTeacherResponse);
        } else if (department != null) {
            return teacherRepository.findByDepartment(department, pageable).map(this::mapToTeacherResponse);
        } else if (active != null) {
            return teacherRepository.findByActive(active, pageable).map(this::mapToTeacherResponse);
        }
        return teacherRepository.findAll(pageable).map(this::mapToTeacherResponse);
    }

    @Transactional
    public StudentResponse createStudent(CreateStudentRequest request) {
        Student student = new Student();
        student.setUsername(request.getUsername());
        student.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        student.setFullName(request.getFullName());
        student.setEmail(request.getEmail());
        student.setRollNumber(request.getRollNumber());
        student.setProgramme(request.getProgramme());
        student.setSemester(request.getSemester());
        student.setDepartment(request.getDepartment());
        
        return mapToStudentResponse(studentRepository.save(student));
    }

    public Page<StudentResponse> listStudents(String department, String programme, String semester, Boolean active, Pageable pageable) {
        // Simple implementation for now
        return studentRepository.findAll(pageable).map(this::mapToStudentResponse);
    }

    @Transactional
    public SubjectResponse createSubject(CreateSubjectRequest request) {
        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        
        Subject subject = new Subject();
        subject.setCode(request.getCode());
        subject.setName(request.getName());
        subject.setCreditHours(request.getCreditHours());
        subject.setDepartment(request.getDepartment());
        subject.setTeacher(teacher);
        
        return mapToSubjectResponse(subjectRepository.save(subject));
    }

    public Page<SubjectResponse> listSubjects(String department, Long teacherId, Pageable pageable) {
        return subjectRepository.findAll(pageable).map(this::mapToSubjectResponse);
    }

    @Transactional
    public MarkResponse enrolStudent(EnrolmentRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        if (markRepository.existsByStudentIdAndSubjectId(request.getStudentId(), request.getSubjectId())) {
            throw new RuntimeException("Student already enrolled in this subject");
        }

        Mark mark = new Mark();
        mark.setStudent(student);
        mark.setSubject(subject);
        mark.setMaxMarks(request.getMaxMarks());
        mark.setStatus(Mark.Status.ENROLLED);
        
        return mapToMarkResponse(markRepository.save(mark));
    }

    // Mapping Helpers
    private TeacherResponse mapToTeacherResponse(Teacher t) {
        TeacherResponse r = new TeacherResponse();
        r.setId(t.getId());
        r.setUsername(t.getUsername());
        r.setFullName(t.getFullName());
        r.setEmail(t.getEmail());
        r.setRole(User.Role.TEACHER);
        r.setActive(t.isActive());
        r.setCreatedAt(t.getCreatedAt());
        r.setDepartment(t.getDepartment());
        r.setEmployeeCode(t.getEmployeeCode());
        return r;
    }

    private StudentResponse mapToStudentResponse(Student s) {
        StudentResponse r = new StudentResponse();
        r.setId(s.getId());
        r.setUsername(s.getUsername());
        r.setFullName(s.getFullName());
        r.setEmail(s.getEmail());
        r.setRole(User.Role.STUDENT);
        r.setActive(s.isActive());
        r.setCreatedAt(s.getCreatedAt());
        r.setRollNumber(s.getRollNumber());
        r.setProgramme(s.getProgramme());
        r.setSemester(s.getSemester());
        r.setDepartment(s.getDepartment());
        return r;
    }

    private SubjectResponse mapToSubjectResponse(Subject s) {
        SubjectResponse r = new SubjectResponse();
        r.setId(s.getId());
        r.setCode(s.getCode());
        r.setName(s.getName());
        r.setCreditHours(s.getCreditHours());
        r.setDepartment(s.getDepartment());
        r.setActive(s.isActive());
        r.setTeacher(mapToTeacherResponse(s.getTeacher()));
        return r;
    }

    private MarkResponse mapToMarkResponse(Mark m) {
        MarkResponse r = new MarkResponse();
        r.setId(m.getId());
        r.setStudent(mapToStudentResponse(m.getStudent()));
        r.setSubject(mapToSubjectResponse(m.getSubject()));
        r.setStatus(m.getStatus());
        r.setMaxMarks(m.getMaxMarks());
        return r;
    }
}
