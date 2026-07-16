package com.rengv.cleretiano.controller;

import com.rengv.cleretiano.dto.StudentRequestDTO;
import com.rengv.cleretiano.dto.StudentResponseDTO;
import com.rengv.cleretiano.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<StudentResponseDTO> findAll() {
        return studentService.findAll();
    }

    @GetMapping("/{id}")
    public StudentResponseDTO findById(@PathVariable Long id) {
        return studentService.findById(id);
    }

    @PostMapping
    public StudentResponseDTO create(@Valid @RequestBody StudentRequestDTO request) {
        return studentService.create(request);
    }

    @PutMapping("/{id}")
    public StudentResponseDTO update(@PathVariable Long id, @Valid @RequestBody StudentRequestDTO request) {
        return studentService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        studentService.delete(id);
    }
}
