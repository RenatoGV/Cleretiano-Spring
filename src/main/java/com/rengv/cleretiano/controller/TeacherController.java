package com.rengv.cleretiano.controller;

import com.rengv.cleretiano.dto.TeacherRequestDTO;
import com.rengv.cleretiano.dto.TeacherResponseDTO;
import com.rengv.cleretiano.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @GetMapping
    public List<TeacherResponseDTO> findAll() {
        return teacherService.findAll();
    }

    @GetMapping("/{id}")
    public TeacherResponseDTO findById(@PathVariable Long id) {
        return teacherService.findById(id);
    }

    @PostMapping
    public TeacherResponseDTO create(@Valid @RequestBody TeacherRequestDTO request) {
        return teacherService.create(request);
    }

    @PutMapping("/{id}")
    public TeacherResponseDTO update(@PathVariable Long id, @Valid @RequestBody TeacherRequestDTO request) {
        return teacherService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        teacherService.delete(id);
    }
}
