package com.coursemanagement.controller;

import com.coursemanagement.model.Course;
import com.coursemanagement.model.Module;
import com.coursemanagement.service.CourseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

	@Autowired
	private CourseService courseService;

	// Create a course
	@PostMapping
	public ResponseEntity<Course> createCourse(@RequestBody Course course) {
		Course created = courseService.createCourse(course);
		return new ResponseEntity<>(created, HttpStatus.CREATED);
	}

	// Get all courses
	@GetMapping
	public ResponseEntity<List<Course>> getAllCourses() {
		return new ResponseEntity<>(courseService.getAllCourses(), HttpStatus.OK);
	}

	// Get course by ID
	@GetMapping("/{id}")
	public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
		Course course = courseService.getCourseById(id);
		return course != null ? new ResponseEntity<>(course, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Update a course
	@PutMapping("/{id}")
	public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
		Course course = courseService.updateCourse(id, updatedCourse);
		return course != null ? new ResponseEntity<>(course, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Delete a course
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
		Course course = courseService.getCourseById(id);
		if (course != null) {
			courseService.deleteCourse(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// Add module to course
	@PostMapping("/{courseId}/modules")
	public ResponseEntity<Course> addModuleToCourse(@PathVariable Long courseId, @RequestBody Module module) {
		Course updatedCourse = courseService.addModuleToCourse(courseId, module);
		return updatedCourse != null ? new ResponseEntity<>(updatedCourse, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	// Delete module from course
	@DeleteMapping("/{courseId}/modules/{moduleId}")
	public ResponseEntity<Course> removeModuleFromCourse(@PathVariable Long courseId, @PathVariable Long moduleId) {
		Course updatedCourse = courseService.removeModuleFromCourse(courseId, moduleId);
		return updatedCourse != null ? new ResponseEntity<>(updatedCourse, HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
