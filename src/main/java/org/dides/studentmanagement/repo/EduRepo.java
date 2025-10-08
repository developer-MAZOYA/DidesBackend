package org.dides.studentmanagement.repo;

import org.dides.studentmanagement.model.AdmissionEdu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EduRepo extends JpaRepository<AdmissionEdu,Long> {
    List<AdmissionEdu> findByQualificationType(String qualificationType);

    List<AdmissionEdu> findBySchoolNameContainingIgnoreCase(String schoolName);

    List<AdmissionEdu> findByIndexNumber(String indexNumber);

    List<AdmissionEdu> findByCountry(String country);

    Optional<AdmissionEdu> findByIndexNumberAndQualificationType(String indexNumber, String qualificationType);
}
