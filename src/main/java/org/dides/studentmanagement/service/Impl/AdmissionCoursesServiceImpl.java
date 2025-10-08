package org.dides.studentmanagement.service.Impl;

import org.dides.studentmanagement.model.AdmissionCourses;
import org.dides.studentmanagement.repo.CoursesRepo;
import org.dides.studentmanagement.service.AdmissionCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdmissionCoursesServiceImpl implements AdmissionCoursesService {

    @Autowired
    private CoursesRepo coursesRepository;

    @Override
    public List<AdmissionCourses> getAllCourses() {
        return coursesRepository.findAll();
    }

    @Override
    public Optional<AdmissionCourses> getCourseById(Long courseId) {
        return coursesRepository.findById(courseId);
    }

    @Override
    public AdmissionCourses createCourse(AdmissionCourses course) {
        // Validate course name uniqueness
        if (course.getCourseName() != null) {
            Optional<AdmissionCourses> existing = coursesRepository.findByCourseName(course.getCourseName());
            if (existing.isPresent()) {
                throw new RuntimeException("Course with name '" + course.getCourseName() + "' already exists");
            }
        }

        // Set default values for checkboxes if not provided
        if (course.getIsFree() == null) {
            course.setIsFree(false);
        }
        if (course.getActive() == null) {
            course.setActive(true);
        }

        return coursesRepository.save(course);
    }

    @Override
    public AdmissionCourses updateCourse(Long courseId, AdmissionCourses course) {
        Optional<AdmissionCourses> existingCourse = coursesRepository.findById(courseId);

        if (existingCourse.isPresent()) {
            AdmissionCourses updatedCourse = existingCourse.get();

            // Update fields
            updatedCourse.setCourseName(course.getCourseName());
            updatedCourse.setCourseType(course.getCourseType());
            updatedCourse.setNatureOfCourse(course.getNatureOfCourse());
            updatedCourse.setIsFree(course.getIsFree());
            updatedCourse.setActive(course.getActive());

            return coursesRepository.save(updatedCourse);
        } else {
            throw new RuntimeException("Course not found with id: " + courseId);
        }
    }

    @Override
    public void deleteCourse(Long courseId) {
        if (coursesRepository.existsById(courseId)) {
            coursesRepository.deleteById(courseId);
        } else {
            throw new RuntimeException("Course not found with id: " + courseId);
        }
    }

    @Override
    public List<AdmissionCourses> getCoursesByType(String courseType) {
        return coursesRepository.findByCourseType(courseType);
    }

    @Override
    public List<AdmissionCourses> getFreeCourses() {
        return coursesRepository.findByIsFree(true);
    }

    @Override
    public List<AdmissionCourses> getActiveCourses() {
        return coursesRepository.findByActive(true);
    }

    @Override
    public List<AdmissionCourses> getCoursesByTypeAndNature(String courseType, String natureOfCourse) {
        return coursesRepository.findByCourseTypeAndNatureOfCourse(courseType, natureOfCourse);
    }

    @Override
    public AdmissionCourses toggleCourseStatus(Long courseId) {
        Optional<AdmissionCourses> course = coursesRepository.findById(courseId);
        if (course.isPresent()) {
            AdmissionCourses updatedCourse = course.get();
            updatedCourse.setActive(!updatedCourse.getActive());
            return coursesRepository.save(updatedCourse);
        } else {
            throw new RuntimeException("Course not found with id: " + courseId);
        }
    }

    @Override
    public AdmissionCourses toggleCourseFreeStatus(Long courseId) {
        Optional<AdmissionCourses> course = coursesRepository.findById(courseId);
        if (course.isPresent()) {
            AdmissionCourses updatedCourse = course.get();
            updatedCourse.setIsFree(!updatedCourse.getIsFree());
            return coursesRepository.save(updatedCourse);
        } else {
            throw new RuntimeException("Course not found with id: " + courseId);
        }
    }
}