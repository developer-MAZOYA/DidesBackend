package org.dides.studentmanagement.repo;

import org.dides.studentmanagement.model.AdmissionPersonInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonalInfoRepo extends JpaRepository<AdmissionPersonInfo, Long>  {

    Optional<AdmissionPersonInfo> findByEmail(String email);

    List<AdmissionPersonInfo> findByLastnameContainingIgnoreCase(String lastName);

}
