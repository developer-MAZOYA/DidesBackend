package org.dides.studentmanagement.service.Impl;

import org.dides.studentmanagement.dto.CourseSelectionRequest;
import org.dides.studentmanagement.dto.CourseSelectionResponse;
import org.dides.studentmanagement.model.AdmissionCourses;
import org.dides.studentmanagement.model.AdmissionPersonInfo;
import org.dides.studentmanagement.model.StudentCourseSelection;
import org.dides.studentmanagement.repo.CoursesRepo;
import org.dides.studentmanagement.repo.PersonalInfoRepo;
import org.dides.studentmanagement.repo.StudentCourseSelectionRepository;
import org.dides.studentmanagement.service.StudentCourseSelectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentCourseSelectionServiceImpl implements StudentCourseSelectionService {

    @Autowired
    private StudentCourseSelectionRepository selectionRepository;

    @Autowired
    private CoursesRepo coursesRepository;

    @Autowired
    private PersonalInfoRepo personInfoRepository;

    private CourseSelectionResponse convertToResponse(StudentCourseSelection selection) {
        String message = selection.isSubmitted() ?
                "Course selection submitted successfully!" :
                "Course selection saved as draft successfully!";

        return new CourseSelectionResponse(
                selection.getSelectionId(),
                selection.getPersonalInfoId(),
                selection.getStudentEmail(),
                selection.getApplicantName(),
                selection.getSelectedCourses(),
                selection.getSelectionDate(),
                selection.isSubmitted(),
                selection.getSelectedCourses().size(),
                message
        );
    }

    @Override
    public CourseSelectionResponse createOrUpdateCourseSelection(CourseSelectionRequest request) {
        // Validate personal info exists
        AdmissionPersonInfo personalInfo = personInfoRepository.findById(request.getPersonalInfoId())
                .orElseThrow(() -> new RuntimeException("Personal information not found with id: " + request.getPersonalInfoId()));

        // Validate required fields
        if (request.getSelectedCourseIds() == null || request.getSelectedCourseIds().isEmpty()) {
            throw new RuntimeException("At least one course must be selected");
        }

        // Check if student already has a selection
        Optional<StudentCourseSelection> existingSelection =
                selectionRepository.findByPersonalInfoId(request.getPersonalInfoId());

        StudentCourseSelection selection;

        if (existingSelection.isPresent()) {
            // Update existing selection
            selection = existingSelection.get();
            selection.setSubmitted(request.isSubmitted());
        } else {
            // Create new selection with personal info
            selection = new StudentCourseSelection();
            selection.setPersonalInfoId(request.getPersonalInfoId());
            selection.setStudentEmail(personalInfo.getEmail());
            selection.setApplicantName(personalInfo.getFirstname() + " " + personalInfo.getLastname());
            selection.setSubmitted(request.isSubmitted());
        }

        // Get the selected courses from the database
        List<AdmissionCourses> selectedCourses = coursesRepository.findAllById(request.getSelectedCourseIds());

        // Validate that all course IDs exist
        if (selectedCourses.size() != request.getSelectedCourseIds().size()) {
            throw new RuntimeException("One or more selected courses were not found");
        }

        // Validate that selected courses are active
        List<AdmissionCourses> inactiveCourses = selectedCourses.stream()
                .filter(course -> !course.getActive())
                .collect(Collectors.toList());

        if (!inactiveCourses.isEmpty()) {
            throw new RuntimeException("Cannot select inactive courses: " +
                    inactiveCourses.stream()
                            .map(AdmissionCourses::getCourseName)
                            .collect(Collectors.joining(", ")));
        }

        selection.setSelectedCourses(selectedCourses);

        StudentCourseSelection savedSelection = selectionRepository.save(selection);
        return convertToResponse(savedSelection);
    }

    @Override
    public CourseSelectionResponse getCourseSelectionByPersonalInfoId(Long personalInfoId) {
        StudentCourseSelection selection = selectionRepository.findByPersonalInfoId(personalInfoId)
                .orElseThrow(() -> new RuntimeException("No course selection found for personal info id: " + personalInfoId));
        return convertToResponse(selection);
    }

    @Override
    public CourseSelectionResponse getCourseSelectionByStudentEmail(String studentEmail) {
        StudentCourseSelection selection = selectionRepository.findByStudentEmail(studentEmail)
                .orElseThrow(() -> new RuntimeException("No course selection found for student: " + studentEmail));
        return convertToResponse(selection);
    }

    @Override
    public List<CourseSelectionResponse> getAllCourseSelections() {
        return selectionRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CourseSelectionResponse getCourseSelectionById(Long selectionId) {
        StudentCourseSelection selection = selectionRepository.findById(selectionId)
                .orElseThrow(() -> new RuntimeException("Course selection not found with id: " + selectionId));
        return convertToResponse(selection);
    }

    @Override
    public void deleteCourseSelection(Long selectionId) {
        if (!selectionRepository.existsById(selectionId)) {
            throw new RuntimeException("Course selection not found with id: " + selectionId);
        }
        selectionRepository.deleteById(selectionId);
    }

    @Override
    public CourseSelectionResponse submitCourseSelection(Long personalInfoId) {
        StudentCourseSelection selection = selectionRepository.findByPersonalInfoId(personalInfoId)
                .orElseThrow(() -> new RuntimeException("No course selection found for personal info id: " + personalInfoId));

        if (selection.getSelectedCourses().isEmpty()) {
            throw new RuntimeException("Cannot submit empty course selection");
        }

        selection.setSubmitted(true);
        StudentCourseSelection submittedSelection = selectionRepository.save(selection);
        return convertToResponse(submittedSelection);
    }

    @Override
    public List<CourseSelectionResponse> getSubmittedSelections() {
        return selectionRepository.findBySubmitted(true)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseSelectionResponse> getSelectionsByApplicantName(String applicantName) {
        return selectionRepository.findByApplicantNameContainingIgnoreCase(applicantName)
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasSubmittedSelection(Long personalInfoId) {
        return selectionRepository.countSubmittedSelectionsByPersonalInfoId(personalInfoId) > 0;
    }
}