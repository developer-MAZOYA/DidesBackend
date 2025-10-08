package org.dides.studentmanagement.service;

import org.dides.studentmanagement.model.AdmissionCourses;
import java.util.List;
import java.util.Optional;

public interface AdmissionCoursesService {

    List<AdmissionCourses> getAllCourses();

    Optional<AdmissionCourses> getCourseById(Long courseId);

    AdmissionCourses createCourse(AdmissionCourses course);

    AdmissionCourses updateCourse(Long courseId, AdmissionCourses course);

    void deleteCourse(Long courseId);

    List<AdmissionCourses> getCoursesByType(String courseType);

    List<AdmissionCourses> getFreeCourses();

    List<AdmissionCourses> getActiveCourses();

    List<AdmissionCourses> getCoursesByTypeAndNature(String courseType, String natureOfCourse);

    AdmissionCourses toggleCourseStatus(Long courseId);

    AdmissionCourses toggleCourseFreeStatus(Long courseId);
}