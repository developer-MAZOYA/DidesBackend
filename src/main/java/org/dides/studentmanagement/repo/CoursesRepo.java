package org.dides.studentmanagement.repo;

import org.dides.studentmanagement.model.AdmissionCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CoursesRepo extends JpaRepository<AdmissionCourses,Long> {
    Optional<AdmissionCourses> findByCourseName(String courseName);

    List<AdmissionCourses> findByCourseType(String courseType);

    List<AdmissionCourses> findByIsFree(Boolean isFree);

    List<AdmissionCourses> findByActive(Boolean active);

    List<AdmissionCourses> findByCourseTypeAndNatureOfCourse(String courseType, String natureOfCourse);
}
