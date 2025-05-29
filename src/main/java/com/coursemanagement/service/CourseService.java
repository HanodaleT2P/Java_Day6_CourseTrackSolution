package com.coursemanagement.service;

import com.coursemanagement.model.Course;
import com.coursemanagement.model.Module;
import com.coursemanagement.repository.CourseRepository;
import com.coursemanagement.repository.ModuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ModuleRepository moduleRepository;

    // Create a course
    public Course createCourse(Course course) {
        // Set course reference for each module (bidirectional link)
        for (Module module : course.getModules()) {
            module.setCourse(course);
        }
        return courseRepository.save(course);
    }

    // Get all courses
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    // Get course by ID
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElse(null);
    }

    // Update course
    public Course updateCourse(Long id, Course updatedCourse) {
        Course existingCourse = getCourseById(id);
        if (existingCourse != null) {
            existingCourse.setTitle(updatedCourse.getTitle());
            existingCourse.setInstructor(updatedCourse.getInstructor());
            existingCourse.setCategory(updatedCourse.getCategory());
            existingCourse.setStartDate(updatedCourse.getStartDate());
            existingCourse.setEndDate(updatedCourse.getEndDate());
            return courseRepository.save(existingCourse);
        }
        return null;
    }

    // Delete course
    public void deleteCourse(Long id) {
        Course course = getCourseById(id);
        if (course != null) {
            courseRepository.delete(course);
        }
    }

    // Add module to course
    public Course addModuleToCourse(Long courseId, Module module) {
        Course course = getCourseById(courseId);
        if (course != null) {
            module.setCourse(course);
            course.getModules().add(module); // Cascade will save module
            return courseRepository.save(course);
        }
        return null;
    }

    // Remove module from course
    public Course removeModuleFromCourse(Long courseId, Long moduleId) {
        Course course = getCourseById(courseId);
        if (course != null) {
            Optional<Module> moduleOpt = moduleRepository.findById(moduleId);
            if (moduleOpt.isPresent()) {
                Module module = moduleOpt.get();
                if (course.getModules().removeIf(m -> m.getId().equals(moduleId))) {
                    // Orphan removal will handle deletion
                    return courseRepository.save(course);
                }
            }
        }
        return null;
    }
}
