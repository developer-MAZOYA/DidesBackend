package org.dides.studentmanagement.service;

import org.dides.studentmanagement.dto.CourseSelectionRequest;
import org.dides.studentmanagement.dto.CourseSelectionResponse;

import java.util.List;

public interface StudentCourseSelectionService {

    CourseSelectionResponse createOrUpdateCourseSelection(CourseSelectionRequest request);

    CourseSelectionResponse getCourseSelectionByPersonalInfoId(Long personalInfoId);

    CourseSelectionResponse getCourseSelectionByStudentEmail(String studentEmail);

    List<CourseSelectionResponse> getAllCourseSelections();

    CourseSelectionResponse getCourseSelectionById(Long selectionId);

    void deleteCourseSelection(Long selectionId);

    CourseSelectionResponse submitCourseSelection(Long personalInfoId);

    List<CourseSelectionResponse> getSubmittedSelections();

    List<CourseSelectionResponse> getSelectionsByApplicantName(String applicantName);

    boolean hasSubmittedSelection(Long personalInfoId);
}