package org.dides.studentmanagement.service;

import org.dides.studentmanagement.model.AdmissionPersonInfo;
import java.util.List;
import java.util.Optional;

public interface AdmissionPersonInfoService {

    List<AdmissionPersonInfo> getAllPersonalInfo();

    Optional<AdmissionPersonInfo> getPersonalInfoById(Long id);

    AdmissionPersonInfo createPersonalInfo(AdmissionPersonInfo personalInfo);

    AdmissionPersonInfo updatePersonalInfo(Long id, AdmissionPersonInfo personalInfo);

    void deletePersonalInfo(Long id);

    Optional<AdmissionPersonInfo> getPersonalInfoByEmail(String email);

    List<AdmissionPersonInfo> getPersonalInfoByLastName(String lastName);
}