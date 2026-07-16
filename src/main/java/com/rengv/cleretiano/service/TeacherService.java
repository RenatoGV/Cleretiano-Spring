package com.rengv.cleretiano.service;

import com.rengv.cleretiano.dto.TeacherRequestDTO;
import com.rengv.cleretiano.dto.TeacherResponseDTO;
import com.rengv.cleretiano.model.Course;
import com.rengv.cleretiano.model.Teacher;
import com.rengv.cleretiano.repository.CourseRepository;
import com.rengv.cleretiano.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final CourseService courseService;

    public TeacherService(TeacherRepository teacherRepository, CourseRepository courseRepository,
                           CourseService courseService) {
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.courseService = courseService;
    }

    public List<TeacherResponseDTO> findAll() {
        return teacherRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public TeacherResponseDTO findById(Long id) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        return toResponseDTO(teacher);
    }

    public TeacherResponseDTO create(TeacherRequestDTO request) {
        Teacher teacher = new Teacher();
        teacher.setCode(request.getCode());
        teacher.setDni(request.getDni());
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setCourses(resolveCourses(request.getCourseIds()));

        Teacher saved = teacherRepository.save(teacher);
        return toResponseDTO(saved);
    }

    public TeacherResponseDTO update(Long id, TeacherRequestDTO request) {
        Teacher teacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        teacher.setCode(request.getCode());
        teacher.setDni(request.getDni());
        teacher.setFirstName(request.getFirstName());
        teacher.setLastName(request.getLastName());
        teacher.setCourses(resolveCourses(request.getCourseIds()));

        Teacher saved = teacherRepository.save(teacher);
        return toResponseDTO(saved);
    }

    public void delete(Long id) {
        teacherRepository.deleteById(id);
    }

    private Set<Course> resolveCourses(List<Long> courseIds) {
        Set<Course> courses = new HashSet<>();
        if (courseIds != null) {
            for (Long courseId : courseIds) {
                Course course = courseRepository.findById(courseId)
                        .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
                courses.add(course);
            }
        }
        return courses;
    }

    private TeacherResponseDTO toResponseDTO(Teacher teacher) {
        TeacherResponseDTO dto = new TeacherResponseDTO();
        dto.setId(teacher.getId());
        dto.setCode(teacher.getCode());
        dto.setDni(teacher.getDni());
        dto.setFirstName(teacher.getFirstName());
        dto.setLastName(teacher.getLastName());
        dto.setCourses(teacher.getCourses().stream()
                .map(courseService::toResponseDTO)
                .collect(Collectors.toList()));
        return dto;
    }
}
