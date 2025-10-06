package org.dides.studentmanagement.repo;

import org.dides.studentmanagement.model.AdmissionPersonInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalInfoRepo extends JpaRepository<AdmissionPersonInfo, Long>  {

}
