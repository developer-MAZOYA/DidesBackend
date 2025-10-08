package org.dides.studentmanagement.controller;

import org.dides.studentmanagement.model.AdmissionCourses;
import org.dides.studentmanagement.service.AdmissionCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/courses")
public class AdmissionCoursesController {

    @Autowired
    private AdmissionCoursesService coursesService;

    @GetMapping("/CoursesAll")
    public ResponseEntity<List<AdmissionCourses>> getAllCourses() {
        List<AdmissionCourses> courses = coursesService.getAllCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("Course/{courseId}")
    public ResponseEntity<AdmissionCourses> getCourseById(@PathVariable Long courseId) {
        Optional<AdmissionCourses> course = coursesService.getCourseById(courseId);
        return course.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("AddCourse")
    public ResponseEntity<AdmissionCourses> createCourse(@RequestBody AdmissionCourses course) {
        try {
            AdmissionCourses createdCourse = coursesService.createCourse(course);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("Course-Update/{courseId}")
    public ResponseEntity<AdmissionCourses> updateCourse(
            @PathVariable Long courseId,
            @RequestBody AdmissionCourses course) {
        try {
            AdmissionCourses updatedCourse = coursesService.updateCourse(courseId, course);
            return ResponseEntity.ok(updatedCourse);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("Course-Delete/{courseId}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long courseId) {
        try {
            coursesService.deleteCourse(courseId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Filter endpoints
    @GetMapping("Course/type/{courseType}")
    public ResponseEntity<List<AdmissionCourses>> getCoursesByType(@PathVariable String courseType) {
        List<AdmissionCourses> courses = coursesService.getCoursesByType(courseType);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/free")
    public ResponseEntity<List<AdmissionCourses>> getFreeCourses() {
        List<AdmissionCourses> courses = coursesService.getFreeCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/active")
    public ResponseEntity<List<AdmissionCourses>> getActiveCourses() {
        List<AdmissionCourses> courses = coursesService.getActiveCourses();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("filter-by-type-nature/filter")
    public ResponseEntity<List<AdmissionCourses>> getCoursesByTypeAndNature(
            @RequestParam String courseType,
            @RequestParam(required = false) String natureOfCourse) {
        List<AdmissionCourses> courses;
        if (natureOfCourse != null) {
            courses = coursesService.getCoursesByTypeAndNature(courseType, natureOfCourse);
        } else {
            courses = coursesService.getCoursesByType(courseType);
        }
        return ResponseEntity.ok(courses);
    }

    // Toggle endpoints for checkboxes
    @PatchMapping("course/{courseId}/toggle-active")
    public ResponseEntity<AdmissionCourses> toggleCourseStatus(@PathVariable Long courseId) {
        try {
            AdmissionCourses updatedCourse = coursesService.toggleCourseStatus(courseId);
            return ResponseEntity.ok(updatedCourse);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("course/{courseId}/toggle-free")
    public ResponseEntity<AdmissionCourses> toggleCourseFreeStatus(@PathVariable Long courseId) {
        try {
            AdmissionCourses updatedCourse = coursesService.toggleCourseFreeStatus(courseId);
            return ResponseEntity.ok(updatedCourse);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}