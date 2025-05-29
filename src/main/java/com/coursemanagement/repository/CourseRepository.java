package com.coursemanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.coursemanagement.model.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {
}
