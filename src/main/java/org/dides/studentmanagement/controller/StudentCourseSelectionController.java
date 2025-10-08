package org.dides.studentmanagement.controller;

import org.dides.studentmanagement.dto.CourseSelectionRequest;
import org.dides.studentmanagement.dto.CourseSelectionResponse;
import org.dides.studentmanagement.service.StudentCourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/course-selections")
public class StudentCourseSelectionController {

    @Autowired
    private StudentCourseSelectionService courseSelectionService;

    @PostMapping
    public ResponseEntity<?> createOrUpdateCourseSelection(@RequestBody CourseSelectionRequest request) {
        try {
            CourseSelectionResponse response = courseSelectionService.createOrUpdateCourseSelection(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/personal-info/{personalInfoId}")
    public ResponseEntity<?> getCourseSelectionByPersonalInfoId(@PathVariable Long personalInfoId) {
        try {
            CourseSelectionResponse response = courseSelectionService.getCourseSelectionByPersonalInfoId(personalInfoId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/student/{studentEmail}")
    public ResponseEntity<?> getCourseSelectionByStudentEmail(@PathVariable String studentEmail) {
        try {
            CourseSelectionResponse response = courseSelectionService.getCourseSelectionByStudentEmail(studentEmail);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<CourseSelectionResponse>> getAllCourseSelections() {
        List<CourseSelectionResponse> selections = courseSelectionService.getAllCourseSelections();
        return ResponseEntity.ok(selections);
    }

    @GetMapping("/{selectionId}")
    public ResponseEntity<?> getCourseSelectionById(@PathVariable Long selectionId) {
        try {
            CourseSelectionResponse response = courseSelectionService.getCourseSelectionById(selectionId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{selectionId}")
    public ResponseEntity<Void> deleteCourseSelection(@PathVariable Long selectionId) {
        try {
            courseSelectionService.deleteCourseSelection(selectionId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{personalInfoId}/submit")
    public ResponseEntity<?> submitCourseSelection(@PathVariable Long personalInfoId) {
        try {
            CourseSelectionResponse response = courseSelectionService.submitCourseSelection(personalInfoId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/submitted")
    public ResponseEntity<List<CourseSelectionResponse>> getSubmittedSelections() {
        List<CourseSelectionResponse> submissions = courseSelectionService.getSubmittedSelections();
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/search/applicant")
    public ResponseEntity<List<CourseSelectionResponse>> getSelectionsByApplicantName(@RequestParam String applicantName) {
        List<CourseSelectionResponse> selections = courseSelectionService.getSelectionsByApplicantName(applicantName);
        return ResponseEntity.ok(selections);
    }

    @GetMapping("/{personalInfoId}/has-submitted")
    public ResponseEntity<Boolean> hasStudentSubmittedSelection(@PathVariable Long personalInfoId) {
        boolean hasSubmitted = courseSelectionService.hasSubmittedSelection(personalInfoId);
        return ResponseEntity.ok(hasSubmitted);
    }
}