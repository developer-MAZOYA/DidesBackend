package org.dides.studentmanagement.controller;

import org.dides.studentmanagement.dto.*;
import org.dides.studentmanagement.model.*;
import org.dides.studentmanagement.service.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admissions")
public class AdmissionController {

    @Autowired
    private AdmissionService admissionService;

    // 1. Start admission process
    @PostMapping("/Start")
    public ResponseEntity<?> createAdmission(@RequestBody CreateAdmissionRequest request) {
        try {
            AdmissionResponse response = admissionService.createAdmission(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 2. Step 1: Personal Information
    @PutMapping("/{admissionId}/personal-info")
    public ResponseEntity<?> updatePersonalInfo(@PathVariable Long admissionId, @RequestBody AdmissionPersonInfo personalInfo) {
        try {
            AdmissionResponse response = admissionService.updatePersonalInfo(admissionId, personalInfo);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 3. Step 2: Next of Kin
    @PutMapping("/{admissionId}/next-of-kin")
    public ResponseEntity<?> updateNextOfKin(@PathVariable Long admissionId, @RequestBody AdmissionNextOfKin nextOfKin) {
        try {
            AdmissionResponse response = admissionService.updateNextOfKin(admissionId, nextOfKin);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 4. Step 3: Education Background
    @PutMapping("/{admissionId}/education")
    public ResponseEntity<?> updateEducationBackgrounds(@PathVariable Long admissionId, @RequestBody List<AdmissionEdu> educationBackgrounds) {
        try {
            AdmissionResponse response = admissionService.updateEducationBackgrounds(admissionId, educationBackgrounds);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 5. Step 4: Sponsor Information
    @PutMapping("/{admissionId}/sponsor")
    public ResponseEntity<?> updateSponsor(@PathVariable Long admissionId, @RequestBody AdmissionSponsor sponsor) {
        try {
            AdmissionResponse response = admissionService.updateSponsor(admissionId, sponsor);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 6. Step 5: Course Selection
    @PutMapping("/{admissionId}/courses")
    public ResponseEntity<?> updateCourseSelection(@PathVariable Long admissionId, @RequestBody CourseSelectionRequest request) {
        try {
            AdmissionResponse response = admissionService.updateCourseSelection(admissionId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 7. Final submission
    @PostMapping("/{admissionId}/submit")
    public ResponseEntity<?> submitAdmission(@PathVariable Long admissionId) {
        try {
            AdmissionResponse response = admissionService.submitAdmission(admissionId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Get endpoints remain the same...
    @GetMapping("/{admissionId}")
    public ResponseEntity<?> getAdmissionById(@PathVariable Long admissionId) {
        try {
            AdmissionResponse response = admissionService.getAdmissionById(admissionId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getAdmissionByEmail(@PathVariable String email) {
        try {
            AdmissionResponse response = admissionService.getAdmissionByEmail(email);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<AdmissionResponse>> getAllAdmissions() {
        List<AdmissionResponse> admissions = admissionService.getAllAdmissions();
        return ResponseEntity.ok(admissions);
    }

    @GetMapping("/{admissionId}/validate")
    public ResponseEntity<Boolean> validateAdmissionCompletion(@PathVariable Long admissionId) {
        try {
            boolean isValid = admissionService.validateAdmissionCompletion(admissionId);
            return ResponseEntity.ok(isValid);
        } catch (RuntimeException e) {
            return ResponseEntity.ok(false);
        }
    }
}