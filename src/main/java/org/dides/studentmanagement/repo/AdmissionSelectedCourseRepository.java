package org.dides.studentmanagement.repo;

import org.dides.studentmanagement.model.Admission;
import org.dides.studentmanagement.model.AdmissionSelectedCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdmissionSelectedCourseRepository extends JpaRepository<AdmissionSelectedCourse, Long> {

    List<AdmissionSelectedCourse> findByAdmission(Admission admission);

    void deleteByAdmission(Admission admission);

    List<AdmissionSelectedCourse> findByCourseCourseId(Long courseId);
}