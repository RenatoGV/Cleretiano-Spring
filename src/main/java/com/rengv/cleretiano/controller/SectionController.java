package com.rengv.cleretiano.controller;

import com.rengv.cleretiano.dto.SectionRequestDTO;
import com.rengv.cleretiano.dto.SectionResponseDTO;
import com.rengv.cleretiano.service.SectionService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class SectionController {

    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    public List<SectionResponseDTO> findAll() {
        return sectionService.findAll();
    }

    @GetMapping("/{id}")
    public SectionResponseDTO findById(@PathVariable Long id) {
        return sectionService.findById(id);
    }

    @PostMapping
    public SectionResponseDTO create(@Valid @RequestBody SectionRequestDTO request) {
        return sectionService.create(request);
    }

    @PutMapping("/{id}")
    public SectionResponseDTO update(@PathVariable Long id, @Valid @RequestBody SectionRequestDTO request) {
        return sectionService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        sectionService.delete(id);
    }
}
