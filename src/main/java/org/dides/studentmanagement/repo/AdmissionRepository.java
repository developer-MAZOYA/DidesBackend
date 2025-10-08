package org.dides.studentmanagement.repo;

import org.dides.studentmanagement.model.Admission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdmissionRepository extends JpaRepository<Admission, Long> {

    Optional<Admission> findByApplicationNumber(String applicationNumber);

    List<Admission> findByStatus(String status);

    @Query("SELECT a FROM Admission a WHERE a.personalInfo.email = :email")
    Optional<Admission> findByPersonalInfoEmail(@Param("email") String email);

    @Query("SELECT a FROM Admission a WHERE a.personalInfo.mobileNumber = :mobileNumber")
    Optional<Admission> findByPersonalInfoMobileNumber(@Param("mobileNumber") String mobileNumber);

    List<Admission> findByCurrentStep(String currentStep);
}