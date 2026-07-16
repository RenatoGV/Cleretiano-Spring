package com.rengv.cleretiano.service;

import com.rengv.cleretiano.dto.StudentRequestDTO;
import com.rengv.cleretiano.dto.StudentResponseDTO;
import com.rengv.cleretiano.model.Grade;
import com.rengv.cleretiano.model.Section;
import com.rengv.cleretiano.model.Student;
import com.rengv.cleretiano.repository.GradeRepository;
import com.rengv.cleretiano.repository.SectionRepository;
import com.rengv.cleretiano.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;
    private final SectionRepository sectionRepository;

    public StudentService(StudentRepository studentRepository, GradeRepository gradeRepository,
                           SectionRepository sectionRepository) {
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
        this.sectionRepository = sectionRepository;
    }

    public List<StudentResponseDTO> findAll() {
        return studentRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public StudentResponseDTO findById(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return toResponseDTO(student);
    }

    public StudentResponseDTO create(StudentRequestDTO request) {
        Grade grade = gradeRepository.findById(request.getGradeId())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));

        Section section = sectionRepository.findById(request.getSectionId())
                .orElseThrow(() -> new RuntimeException("Sección no encontrada"));

        Student student = new Student();
        student.setCode(request.getCode());
        student.setDni(request.getDni());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setBirthDate(request.getBirthDate());
        student.setGender(request.getGender());
        student.setAddress(request.getAddress());
        student.setGrade(grade);
        student.setSection(section);

        Student saved = studentRepository.save(student);
        return toResponseDTO(saved);
    }

    public StudentResponseDTO update(Long id, StudentRequestDTO request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        Grade grade = gradeRepository.findById(request.getGradeId())
                .orElseThrow(() -> new RuntimeException("Grado no encontrado"));

        Section section = sectionRepository.findById(request.getSectionId())
                .orElseThrow(() -> new RuntimeException("Sección no encontrada"));

        student.setCode(request.getCode());
        student.setDni(request.getDni());
        student.setFirstName(request.getFirstName());
        student.setLastName(request.getLastName());
        student.setBirthDate(request.getBirthDate());
        student.setGender(request.getGender());
        student.setAddress(request.getAddress());
        student.setGrade(grade);
        student.setSection(section);

        Student saved = studentRepository.save(student);
        return toResponseDTO(saved);
    }

    public void delete(Long id) {
        studentRepository.deleteById(id);
    }

    private StudentResponseDTO toResponseDTO(Student student) {
        StudentResponseDTO dto = new StudentResponseDTO();
        dto.setId(student.getId());
        dto.setCode(student.getCode());
        dto.setDni(student.getDni());
        dto.setFirstName(student.getFirstName());
        dto.setLastName(student.getLastName());
        dto.setBirthDate(student.getBirthDate());
        dto.setGender(student.getGender());
        dto.setAddress(student.getAddress());
        dto.setGradeId(student.getGrade().getId());
        dto.setGradeName(student.getGrade().getName());
        dto.setSectionId(student.getSection().getId());
        dto.setSectionName(student.getSection().getName());
        return dto;
    }
}
