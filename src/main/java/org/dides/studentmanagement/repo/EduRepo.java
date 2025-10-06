package org.dides.studentmanagement.repo;

import org.dides.studentmanagement.model.AdmissionEdu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EduRepo extends JpaRepository<AdmissionEdu,Long> {

}
