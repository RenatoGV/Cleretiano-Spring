package com.rengv.cleretiano.repository;

import com.rengv.cleretiano.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
