package com.rengv.cleretiano.controller;

import com.rengv.cleretiano.dto.CourseRequestDTO;
import com.rengv.cleretiano.dto.CourseResponseDTO;
import com.rengv.cleretiano.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<CourseResponseDTO> findAll() {
        return courseService.findAll();
    }

    @GetMapping("/{id}")
    public CourseResponseDTO findById(@PathVariable Long id) {
        return courseService.findById(id);
    }

    @PostMapping
    public CourseResponseDTO create(@Valid @RequestBody CourseRequestDTO request) {
        return courseService.create(request);
    }

    @PutMapping("/{id}")
    public CourseResponseDTO update(@PathVariable Long id, @Valid @RequestBody CourseRequestDTO request) {
        return courseService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        courseService.delete(id);
    }
}
