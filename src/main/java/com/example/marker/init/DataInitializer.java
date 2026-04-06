package com.example.marker.init;

import com.example.marker.model.*;
import com.example.marker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final MarkRepository markRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if (userRepository.count() > 0) {
            return;
        }

        // 1. Create Admin
        Admin admin = new Admin();
        admin.setUsername("admin");
        admin.setPasswordHash(passwordEncoder.encode("admin123"));
        admin.setFullName("System Administrator");
        admin.setEmail("admin@college.edu");
        admin.setDepartment("Administration");
        adminRepository.save(admin);

        // 2. Create Teacher
        Teacher teacher = new Teacher();
        teacher.setUsername("teacher1");
        teacher.setPasswordHash(passwordEncoder.encode("teacher123"));
        teacher.setFullName("Dr. John Doe");
        teacher.setEmail("john.doe@college.edu");
        teacher.setDepartment("Computer Science");
        teacher.setEmployeeCode("T001");
        teacherRepository.save(teacher);

        // 3. Create Student
        Student student = new Student();
        student.setUsername("student1");
        student.setPasswordHash(passwordEncoder.encode("student123"));
        student.setFullName("Jane Smith");
        student.setEmail("jane.smith@college.edu");
        student.setRollNumber("S101");
        student.setProgramme("B.Tech");
        student.setSemester("4th");
        student.setDepartment("Computer Science");
        studentRepository.save(student);

        // 4. Create Subject and assign to Teacher
        Subject subject = new Subject();
        subject.setCode("CS401");
        subject.setName("Database Management Systems");
        subject.setCreditHours(4);
        subject.setDepartment("Computer Science");
        subject.setTeacher(teacher);
        subjectRepository.save(subject);

        // 5. Enrol Student in Subject (create Mark record)
        Mark mark = new Mark();
        mark.setStudent(student);
        mark.setSubject(subject);
        mark.setMaxMarks(BigDecimal.valueOf(100.0));
        mark.setStatus(Mark.Status.ENROLLED);
        markRepository.save(mark);

        System.out.println("--- Database Seeded Successfully ---");
        System.out.println("Admin: admin / admin123");
        System.out.println("Teacher: teacher1 / teacher123");
        System.out.println("Student: student1 / student123");
    }
}
