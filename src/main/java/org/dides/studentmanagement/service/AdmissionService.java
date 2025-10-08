package org.dides.studentmanagement.service;

import org.dides.studentmanagement.dto.*;
import org.dides.studentmanagement.model.AdmissionPersonInfo;
import org.dides.studentmanagement.model.AdmissionNextOfKin;
import org.dides.studentmanagement.model.AdmissionEdu;
import org.dides.studentmanagement.model.AdmissionSponsor;

import java.util.List;

public interface AdmissionService {

    // Start admission process
    AdmissionResponse createAdmission(CreateAdmissionRequest request);

    // Step 1: Personal Information
    AdmissionResponse updatePersonalInfo(Long admissionId, AdmissionPersonInfo personalInfo);

    // Step 2: Next of Kin
    AdmissionResponse updateNextOfKin(Long admissionId, AdmissionNextOfKin nextOfKin);

    // Step 3: Education Background
    AdmissionResponse updateEducationBackgrounds(Long admissionId, List<AdmissionEdu> educationBackgrounds);

    // Step 4: Sponsor Information
    AdmissionResponse updateSponsor(Long admissionId, AdmissionSponsor sponsor);

    // Step 5: Course Selection
    AdmissionResponse updateCourseSelection(Long admissionId, CourseSelectionRequest request);

    // Final submission
    AdmissionResponse submitAdmission(Long admissionId);

    // Get admission details
    AdmissionResponse getAdmissionById(Long admissionId);
    AdmissionResponse getAdmissionByEmail(String email);
    List<AdmissionResponse> getAllAdmissions();

    // Validation
    boolean validateAdmissionCompletion(Long admissionId);
}