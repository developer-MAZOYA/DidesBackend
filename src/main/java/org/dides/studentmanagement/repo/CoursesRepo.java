package org.dides.studentmanagement.repo;

import org.dides.studentmanagement.model.AdmissionCourses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoursesRepo extends JpaRepository<AdmissionCourses,Long> {

}
