package com.rengv.cleretiano.config;

import com.rengv.cleretiano.model.*;
import com.rengv.cleretiano.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final GradeRepository gradeRepository;
    private final SectionRepository sectionRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final NoteRepository noteRepository;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public DataInitializer(UserRepository userRepository, GradeRepository gradeRepository,
                            SectionRepository sectionRepository, CourseRepository courseRepository,
                            TeacherRepository teacherRepository, StudentRepository studentRepository,
                            NoteRepository noteRepository) {
        this.userRepository = userRepository;
        this.gradeRepository = gradeRepository;
        this.sectionRepository = sectionRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
        this.studentRepository = studentRepository;
        this.noteRepository = noteRepository;
    }

    @Override
    public void run(String... args) {
        createAdminUser();

        if (gradeRepository.count() == 0) {
            seedAcademicData();
        }
    }

    private void createAdminUser() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setFirstName("Administrador");
            admin.setLastName("Administrador");
            admin.setRole(Role.ADMIN);
            userRepository.save(admin);
        }
    }

    private void seedAcademicData() {
        // Grades
        Grade first = new Grade();
        first.setName("1ro de Secundaria");
        first.setLevel(Level.SECONDARY);
        first = gradeRepository.save(first);

        Grade second = new Grade();
        second.setName("2do de Secundaria");
        second.setLevel(Level.SECONDARY);
        second = gradeRepository.save(second);

        // Sections
        Section sectionA = new Section();
        sectionA.setName("A");
        sectionRepository.save(sectionA);

        Section sectionB = new Section();
        sectionB.setName("B");
        sectionRepository.save(sectionB);

        Section sectionC = new Section();
        sectionC.setName("C");
        sectionRepository.save(sectionC);

        // Courses
        Course math = new Course();
        math.setName("Matemática");
        math = courseRepository.save(math);

        Course communication = new Course();
        communication.setName("Comunicación");
        communication = courseRepository.save(communication);

        Course science = new Course();
        science.setName("Ciencias");
        science = courseRepository.save(science);

        // Teachers
        Teacher teacher1 = new Teacher();
        teacher1.setCode("D001");
        teacher1.setDni("40111222");
        teacher1.setFirstName("María");
        teacher1.setLastName("Gonzales");
        teacher1.setCourses(Set.of(math, science));
        teacher1 = teacherRepository.save(teacher1);

        Teacher teacher2 = new Teacher();
        teacher2.setCode("D002");
        teacher2.setDni("40222333");
        teacher2.setFirstName("Carlos");
        teacher2.setLastName("Ramirez");
        teacher2.setCourses(Set.of(communication));
        teacher2 = teacherRepository.save(teacher2);

        // Students
        Student student1 = new Student();
        student1.setCode("E001");
        student1.setDni("70111222");
        student1.setFirstName("Ana");
        student1.setLastName("Torres");
        student1.setBirthDate(LocalDate.of(2011, 3, 15));
        student1.setGender("F");
        student1.setAddress("Jr. Los Pinos 123, Huancayo");
        student1.setGrade(first);
        student1.setSection(sectionA);
        student1 = studentRepository.save(student1);

        Student student2 = new Student();
        student2.setCode("E002");
        student2.setDni("70222333");
        student2.setFirstName("Luis");
        student2.setLastName("Quispe");
        student2.setBirthDate(LocalDate.of(2011, 7, 22));
        student2.setGender("M");
        student2.setAddress("Av. Huancavelica 456, Huancayo");
        student2.setGrade(first);
        student2.setSection(sectionB);
        student2 = studentRepository.save(student2);

        // Notes
        Note note1 = new Note();
        note1.setStudent(student1);
        note1.setCourse(math);
        note1.setTeacher(teacher1);
        note1.setScore(17.0);
        note1.setObservation("Buen desempeño");
        noteRepository.save(note1);

        Note note2 = new Note();
        note2.setStudent(student2);
        note2.setCourse(communication);
        note2.setTeacher(teacher2);
        note2.setScore(15.0);
        note2.setObservation("Debe mejorar la ortografía");
        noteRepository.save(note2);
    }
}
