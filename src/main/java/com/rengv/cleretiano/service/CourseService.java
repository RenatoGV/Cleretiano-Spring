package com.rengv.cleretiano.service;

import com.rengv.cleretiano.dto.CourseRequestDTO;
import com.rengv.cleretiano.dto.CourseResponseDTO;
import com.rengv.cleretiano.model.Course;
import com.rengv.cleretiano.repository.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseResponseDTO> findAll() {
        return courseRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public CourseResponseDTO findById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        return toResponseDTO(course);
    }

    public CourseResponseDTO create(CourseRequestDTO request) {
        Course course = new Course();
        course.setName(request.getName());

        Course saved = courseRepository.save(course);
        return toResponseDTO(saved);
    }

    public CourseResponseDTO update(Long id, CourseRequestDTO request) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        course.setName(request.getName());

        Course saved = courseRepository.save(course);
        return toResponseDTO(saved);
    }

    public void delete(Long id) {
        courseRepository.deleteById(id);
    }

    public CourseResponseDTO toResponseDTO(Course course) {
        return new CourseResponseDTO(course.getId(), course.getName());
    }
}
