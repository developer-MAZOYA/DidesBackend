package org.dides.studentmanagement.repo;

import org.dides.studentmanagement.model.StudentCourseSelection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentCourseSelectionRepository extends JpaRepository<StudentCourseSelection, Long> {

    Optional<StudentCourseSelection> findByPersonalInfoId(Long personalInfoId);

    Optional<StudentCourseSelection> findByStudentEmail(String studentEmail);

    List<StudentCourseSelection> findByApplicantNameContainingIgnoreCase(String applicantName);

    List<StudentCourseSelection> findBySubmitted(boolean submitted);

    @Query("SELECT s FROM StudentCourseSelection s JOIN s.selectedCourses c WHERE c.courseId = :courseId")
    List<StudentCourseSelection> findByCourseId(@Param("courseId") Long courseId);

    boolean existsByPersonalInfoId(Long personalInfoId);

    @Query("SELECT COUNT(s) FROM StudentCourseSelection s WHERE s.personalInfoId = :personalInfoId AND s.submitted = true")
    long countSubmittedSelectionsByPersonalInfoId(@Param("personalInfoId") Long personalInfoId);
}