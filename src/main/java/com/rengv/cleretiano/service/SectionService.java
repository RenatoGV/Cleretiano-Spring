package com.rengv.cleretiano.service;

import com.rengv.cleretiano.dto.SectionRequestDTO;
import com.rengv.cleretiano.dto.SectionResponseDTO;
import com.rengv.cleretiano.model.Section;
import com.rengv.cleretiano.repository.SectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public List<SectionResponseDTO> findAll() {
        return sectionRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public SectionResponseDTO findById(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sección no encontrada"));
        return toResponseDTO(section);
    }

    public SectionResponseDTO create(SectionRequestDTO request) {
        Section section = new Section();
        section.setName(request.getName());

        Section saved = sectionRepository.save(section);
        return toResponseDTO(saved);
    }

    public SectionResponseDTO update(Long id, SectionRequestDTO request) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Sección no encontrada"));

        section.setName(request.getName());

        Section saved = sectionRepository.save(section);
        return toResponseDTO(saved);
    }

    public void delete(Long id) {
        sectionRepository.deleteById(id);
    }

    private SectionResponseDTO toResponseDTO(Section section) {
        return new SectionResponseDTO(
                section.getId(),
                section.getName()
        );
    }
}
