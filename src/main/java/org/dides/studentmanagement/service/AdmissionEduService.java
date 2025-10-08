package org.dides.studentmanagement.service;

import org.dides.studentmanagement.model.AdmissionEdu;
import java.util.List;
import java.util.Optional;

public interface AdmissionEduService {

    List<AdmissionEdu> getAllEducationBackgrounds();

    Optional<AdmissionEdu> getEducationBackgroundById(Long eduId);

    AdmissionEdu createEducationBackground(AdmissionEdu educationBackground);

    AdmissionEdu updateEducationBackground(Long eduId, AdmissionEdu educationBackground);

    void deleteEducationBackground(Long eduId);

    List<AdmissionEdu> getEducationByQualificationType(String qualificationType);

    List<AdmissionEdu> getEducationBySchoolName(String schoolName);

    List<AdmissionEdu> getEducationByIndexNumber(String indexNumber);

    List<AdmissionEdu> getEducationByCountry(String country);

    Optional<AdmissionEdu> getEducationByIndexAndQualification(String indexNumber, String qualificationType);
}