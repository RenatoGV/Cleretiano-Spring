package com.rengv.cleretiano.service;

import com.rengv.cleretiano.dto.GradeRequestDTO;
import com.rengv.cleretiano.dto.GradeResponseDTO;
import com.rengv.cleretiano.model.Grade;
import com.rengv.cleretiano.model.Level;
import com.rengv.cleretiano.repository.GradeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GradeService {

    private final GradeRepository gradeRepository;

    public GradeService(GradeRepository gradeRepository) {
        this.gradeRepository = gradeRepository;
    }

    public List<GradeResponseDTO> findAll() {
        return gradeRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public GradeResponseDTO findById(Long id) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));
        return toResponseDTO(grade);
    }

    public GradeResponseDTO create(GradeRequestDTO request) {
        Grade grade = new Grade();
        grade.setName(request.getName());
        grade.setLevel(Level.valueOf(request.getLevel()));

        Grade saved = gradeRepository.save(grade);
        return toResponseDTO(saved);
    }

    public GradeResponseDTO update(Long id, GradeRequestDTO request) {
        Grade grade = gradeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));

        grade.setName(request.getName());
        grade.setLevel(Level.valueOf(request.getLevel()));

        Grade saved = gradeRepository.save(grade);
        return toResponseDTO(saved);
    }

    public void delete(Long id) {
        gradeRepository.deleteById(id);
    }

    private GradeResponseDTO toResponseDTO(Grade grade) {
        return new GradeResponseDTO(grade.getId(), grade.getName(), grade.getLevel().name());
    }
}
