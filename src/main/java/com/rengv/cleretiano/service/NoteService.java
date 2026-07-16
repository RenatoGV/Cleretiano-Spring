package com.rengv.cleretiano.service;

import com.rengv.cleretiano.dto.NoteRequestDTO;
import com.rengv.cleretiano.dto.NoteResponseDTO;
import com.rengv.cleretiano.model.Course;
import com.rengv.cleretiano.model.Note;
import com.rengv.cleretiano.model.Student;
import com.rengv.cleretiano.model.Teacher;
import com.rengv.cleretiano.repository.CourseRepository;
import com.rengv.cleretiano.repository.NoteRepository;
import com.rengv.cleretiano.repository.StudentRepository;
import com.rengv.cleretiano.repository.TeacherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;

    public NoteService(NoteRepository noteRepository, StudentRepository studentRepository,
                        CourseRepository courseRepository, TeacherRepository teacherRepository) {
        this.noteRepository = noteRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.teacherRepository = teacherRepository;
    }

    public List<NoteResponseDTO> findAll() {
        return noteRepository.findAll().stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }

    public NoteResponseDTO findById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada"));
        return toResponseDTO(note);
    }

    public NoteResponseDTO create(NoteRequestDTO request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        validateScore(request.getScore());

        Note note = new Note();
        note.setStudent(student);
        note.setCourse(course);
        note.setTeacher(teacher);
        note.setScore(request.getScore());
        note.setObservation(request.getObservation());

        Note saved = noteRepository.save(note);
        return toResponseDTO(saved);
    }

    public NoteResponseDTO update(Long id, NoteRequestDTO request) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Nota no encontrada"));

        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        Course course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Docente no encontrado"));

        validateScore(request.getScore());

        note.setStudent(student);
        note.setCourse(course);
        note.setTeacher(teacher);
        note.setScore(request.getScore());
        note.setObservation(request.getObservation());

        Note saved = noteRepository.save(note);
        return toResponseDTO(saved);
    }

    public void delete(Long id) {
        noteRepository.deleteById(id);
    }

    private void validateScore(Double score) {
        if (score < 0 || score > 20) {
            throw new RuntimeException("La nota debe estar entre 0 y 20");
        }
    }

    private NoteResponseDTO toResponseDTO(Note note) {
        NoteResponseDTO dto = new NoteResponseDTO();
        dto.setId(note.getId());
        dto.setStudentId(note.getStudent().getId());
        dto.setStudentFullName(note.getStudent().getFirstName() + " " + note.getStudent().getLastName());
        dto.setCourseId(note.getCourse().getId());
        dto.setCourseName(note.getCourse().getName());
        dto.setTeacherId(note.getTeacher().getId());
        dto.setTeacherFullName(note.getTeacher().getFirstName() + " " + note.getTeacher().getLastName());
        dto.setScore(note.getScore());
        dto.setObservation(note.getObservation());
        return dto;
    }
}
