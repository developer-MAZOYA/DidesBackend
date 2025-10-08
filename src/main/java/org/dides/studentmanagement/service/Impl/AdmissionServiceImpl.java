package org.dides.studentmanagement.service.Impl;

import org.dides.studentmanagement.dto.*;
import org.dides.studentmanagement.enums.AdmissionStatus;
import org.dides.studentmanagement.model.*;
import org.dides.studentmanagement.repo.CoursesRepo;
import org.dides.studentmanagement.repo.AdmissionRepository;
import org.dides.studentmanagement.repo.AdmissionSelectedCourseRepository;
import org.dides.studentmanagement.service.AdmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdmissionServiceImpl implements AdmissionService {

    @Autowired
    private AdmissionRepository admissionRepository;

    @Autowired
    private CoursesRepo coursesRepository;

    @Autowired
    private AdmissionSelectedCourseRepository selectedCourseRepository;

    private AdmissionResponse convertToResponse(Admission admission) {
        return new AdmissionResponse(
                admission.getAdmissionId(),
                admission.getApplicationNumber(),
                admission.getPersonalInfo(),
                admission.getNextOfKin(),
                admission.getEducationBackgrounds(),
                admission.getSponsor(),
                admission.getSelectedCourses(),
                admission.getStatus().name(),
                admission.getCurrentStep(),
                admission.getCreatedAt(),
                admission.getSubmittedAt(),
                "Admission processed successfully"
        );
    }

    @Override
    public AdmissionResponse createAdmission(CreateAdmissionRequest request) {
        // Check if admission already exists for this email
        if (request.getEmail() != null) {
            admissionRepository.findByPersonalInfoEmail(request.getEmail())
                    .ifPresent(admission -> {
                        throw new RuntimeException("Admission already exists for email: " + request.getEmail());
                    });
        }

        Admission admission = new Admission();

        // Create basic personal info with minimal data
        AdmissionPersonInfo personalInfo = new AdmissionPersonInfo();
        personalInfo.setEmail(request.getEmail());
        personalInfo.setFirstname(request.getFirstname());
        personalInfo.setLastname(request.getLastname());
        admission.setPersonalInfo(personalInfo);

        admission.setCurrentStep("personal_info");

        Admission savedAdmission = admissionRepository.save(admission);
        return convertToResponse(savedAdmission);
    }

    @Override
    public AdmissionResponse updatePersonalInfo(Long admissionId, AdmissionPersonInfo personalInfo) {
        Admission admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission not found with id: " + admissionId));

        admission.setPersonalInfo(personalInfo);
        admission.setCurrentStep("next_of_kin");

        Admission updatedAdmission = admissionRepository.save(admission);
        AdmissionResponse response = convertToResponse(updatedAdmission);
        response.setMessage("Personal information updated successfully");
        return response;
    }

    @Override
    public AdmissionResponse updateNextOfKin(Long admissionId, AdmissionNextOfKin nextOfKin) {
        Admission admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission not found with id: " + admissionId));

        admission.setNextOfKin(nextOfKin);
        admission.setCurrentStep("education");

        Admission updatedAdmission = admissionRepository.save(admission);
        AdmissionResponse response = convertToResponse(updatedAdmission);
        response.setMessage("Next of kin information updated successfully");
        return response;
    }

    @Override
    public AdmissionResponse updateEducationBackgrounds(Long admissionId, List<AdmissionEdu> educationBackgrounds) {
        Admission admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission not found with id: " + admissionId));

        // Clear existing and add new education backgrounds
        admission.getEducationBackgrounds().clear();
        educationBackgrounds.forEach(edu -> edu.setAdmission(admission));
        admission.getEducationBackgrounds().addAll(educationBackgrounds);
        admission.setCurrentStep("sponsor");

        Admission updatedAdmission = admissionRepository.save(admission);
        AdmissionResponse response = convertToResponse(updatedAdmission);
        response.setMessage("Education background updated successfully");
        return response;
    }

    @Override
    public AdmissionResponse updateSponsor(Long admissionId, AdmissionSponsor sponsor) {
        Admission admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission not found with id: " + admissionId));

        admission.setSponsor(sponsor);
        admission.setCurrentStep("course_selection");

        Admission updatedAdmission = admissionRepository.save(admission);
        AdmissionResponse response = convertToResponse(updatedAdmission);
        response.setMessage("Sponsor information updated successfully");
        return response;
    }

    @Override
    public AdmissionResponse updateCourseSelection(Long admissionId, CourseSelectionRequest request) {
        Admission admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission not found with id: " + admissionId));

        updateCourseSelection(admission, request.getSelectedCourseIds());
        admission.setCurrentStep("review");

        Admission updatedAdmission = admissionRepository.save(admission);
        AdmissionResponse response = convertToResponse(updatedAdmission);
        response.setMessage("Course selection updated successfully");
        return response;
    }

    @Override
    public AdmissionResponse submitAdmission(Long admissionId) {
        Admission admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission not found with id: " + admissionId));

        // Validate all steps are completed
        if (!validateAdmissionCompletion(admissionId)) {
            throw new RuntimeException("Cannot submit incomplete admission. Please complete all steps.");
        }

        admission.setStatus(AdmissionStatus.SUBMITTED);
        admission.setSubmittedAt(LocalDateTime.now());

        Admission submittedAdmission = admissionRepository.save(admission);
        AdmissionResponse response = convertToResponse(submittedAdmission);
        response.setMessage("Admission submitted successfully! Your application number is: " + submittedAdmission.getApplicationNumber());

        return response;
    }

    @Override
    public AdmissionResponse getAdmissionById(Long admissionId) {
        Admission admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission not found with id: " + admissionId));
        return convertToResponse(admission);
    }

    @Override
    public AdmissionResponse getAdmissionByEmail(String email) {
        Admission admission = admissionRepository.findByPersonalInfoEmail(email)
                .orElseThrow(() -> new RuntimeException("Admission not found with email: " + email));
        return convertToResponse(admission);
    }

    @Override
    public List<AdmissionResponse> getAllAdmissions() {
        return admissionRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean validateAdmissionCompletion(Long admissionId) {
        Admission admission = admissionRepository.findById(admissionId)
                .orElseThrow(() -> new RuntimeException("Admission not found with id: " + admissionId));

        return admission.getPersonalInfo() != null &&
                admission.getNextOfKin() != null &&
                !admission.getEducationBackgrounds().isEmpty() &&
                admission.getSponsor() != null &&
                !admission.getSelectedCourses().isEmpty();
    }

    private void updateCourseSelection(Admission admission, List<Long> selectedCourseIds) {
        // Clear existing selections
        selectedCourseRepository.deleteByAdmission(admission);
        admission.getSelectedCourses().clear();

        // Get the selected courses
        List<AdmissionCourses> courses = coursesRepository.findAllById(selectedCourseIds);

        // Validate courses exist and are active
        if (courses.size() != selectedCourseIds.size()) {
            throw new RuntimeException("One or more selected courses were not found");
        }

        List<AdmissionCourses> inactiveCourses = courses.stream()
                .filter(course -> !course.getActive())
                .collect(Collectors.toList());

        if (!inactiveCourses.isEmpty()) {
            throw new RuntimeException("Cannot select inactive courses");
        }

        // Create new course selections
        List<AdmissionSelectedCourse> selectedCourses = new ArrayList<>();
        for (int i = 0; i < courses.size(); i++) {
            AdmissionSelectedCourse selectedCourse = new AdmissionSelectedCourse();
            selectedCourse.setAdmission(admission);
            selectedCourse.setCourse(courses.get(i));
            selectedCourse.setPriorityOrder(i + 1);
            selectedCourses.add(selectedCourse);
        }

        admission.getSelectedCourses().addAll(selectedCourses);
    }
}