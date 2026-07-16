package com.rengv.cleretiano.repository;

import com.rengv.cleretiano.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
