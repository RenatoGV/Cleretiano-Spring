package com.rengv.cleretiano.service;

import com.rengv.cleretiano.dto.DashboardSummaryDTO;
import com.rengv.cleretiano.repository.CourseRepository;
import com.rengv.cleretiano.repository.GradeRepository;
import com.rengv.cleretiano.repository.NoteRepository;
import com.rengv.cleretiano.repository.SectionRepository;
import com.rengv.cleretiano.repository.StudentRepository;
import com.rengv.cleretiano.repository.TeacherRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final GradeRepository gradeRepository;
    private final SectionRepository sectionRepository;
    private final NoteRepository noteRepository;

    public DashboardService(StudentRepository studentRepository, TeacherRepository teacherRepository,
                             CourseRepository courseRepository, GradeRepository gradeRepository,
                             SectionRepository sectionRepository, NoteRepository noteRepository) {
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.courseRepository = courseRepository;
        this.gradeRepository = gradeRepository;
        this.sectionRepository = sectionRepository;
        this.noteRepository = noteRepository;
    }

    public DashboardSummaryDTO getSummary() {
        return new DashboardSummaryDTO(
                studentRepository.count(),
                teacherRepository.count(),
                courseRepository.count(),
                gradeRepository.count(),
                sectionRepository.count(),
                noteRepository.count()
        );
    }
}
