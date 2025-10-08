package org.dides.studentmanagement.controller;

import org.dides.studentmanagement.model.AdmissionEdu;
import org.dides.studentmanagement.service.AdmissionEduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/education-background")
public class AdmissionEduController {

    @Autowired
    private AdmissionEduService educationService;

    @GetMapping
    public ResponseEntity<List<AdmissionEdu>> getAllEducationBackgrounds() {
        List<AdmissionEdu> educationBackgrounds = educationService.getAllEducationBackgrounds();
        return ResponseEntity.ok(educationBackgrounds);
    }

    @GetMapping("/{eduId}")
    public ResponseEntity<AdmissionEdu> getEducationBackgroundById(@PathVariable Long eduId) {
        Optional<AdmissionEdu> educationBackground = educationService.getEducationBackgroundById(eduId);
        return educationBackground.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AdmissionEdu> createEducationBackground(@RequestBody AdmissionEdu educationBackground) {
        try {
            AdmissionEdu createdEducation = educationService.createEducationBackground(educationBackground);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEducation);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{eduId}")
    public ResponseEntity<AdmissionEdu> updateEducationBackground(
            @PathVariable Long eduId,
            @RequestBody AdmissionEdu educationBackground) {
        try {
            AdmissionEdu updatedEducation = educationService.updateEducationBackground(eduId, educationBackground);
            return ResponseEntity.ok(updatedEducation);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{eduId}")
    public ResponseEntity<Void> deleteEducationBackground(@PathVariable Long eduId) {
        try {
            educationService.deleteEducationBackground(eduId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Filter endpoints
    @GetMapping("/qualification/{qualificationType}")
    public ResponseEntity<List<AdmissionEdu>> getEducationByQualificationType(@PathVariable String qualificationType) {
        List<AdmissionEdu> educationBackgrounds = educationService.getEducationByQualificationType(qualificationType);
        return ResponseEntity.ok(educationBackgrounds);
    }

    @GetMapping("/search/school")
    public ResponseEntity<List<AdmissionEdu>> getEducationBySchoolName(@RequestParam String schoolName) {
        List<AdmissionEdu> educationBackgrounds = educationService.getEducationBySchoolName(schoolName);
        return ResponseEntity.ok(educationBackgrounds);
    }

    @GetMapping("/search/index")
    public ResponseEntity<List<AdmissionEdu>> getEducationByIndexNumber(@RequestParam String indexNumber) {
        List<AdmissionEdu> educationBackgrounds = educationService.getEducationByIndexNumber(indexNumber);
        return ResponseEntity.ok(educationBackgrounds);
    }

    @GetMapping("/search/country")
    public ResponseEntity<List<AdmissionEdu>> getEducationByCountry(@RequestParam String country) {
        List<AdmissionEdu> educationBackgrounds = educationService.getEducationByCountry(country);
        return ResponseEntity.ok(educationBackgrounds);
    }

    @GetMapping("/search/unique")
    public ResponseEntity<AdmissionEdu> getEducationByIndexAndQualification(
            @RequestParam String indexNumber,
            @RequestParam String qualificationType) {
        Optional<AdmissionEdu> educationBackground = educationService.getEducationByIndexAndQualification(indexNumber, qualificationType);
        return educationBackground.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}