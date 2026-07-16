package com.rengv.cleretiano.controller;

import com.rengv.cleretiano.dto.GradeRequestDTO;
import com.rengv.cleretiano.dto.GradeResponseDTO;
import com.rengv.cleretiano.service.GradeService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/grades")
public class GradeController {

    private final GradeService gradeService;

    public GradeController(GradeService gradeService) {
        this.gradeService = gradeService;
    }

    @GetMapping
    public List<GradeResponseDTO> findAll() {
        return gradeService.findAll();
    }

    @GetMapping("/{id}")
    public GradeResponseDTO findById(@PathVariable Long id) {
        return gradeService.findById(id);
    }

    @PostMapping
    public GradeResponseDTO create(@Valid @RequestBody GradeRequestDTO request) {
        return gradeService.create(request);
    }

    @PutMapping("/{id}")
    public GradeResponseDTO update(@PathVariable Long id, @Valid @RequestBody GradeRequestDTO request) {
        return gradeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        gradeService.delete(id);
    }
}
