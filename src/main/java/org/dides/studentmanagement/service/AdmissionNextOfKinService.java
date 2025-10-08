package org.dides.studentmanagement.service;

import org.dides.studentmanagement.model.AdmissionNextOfKin;
import java.util.List;
import java.util.Optional;

public interface AdmissionNextOfKinService {

    List<AdmissionNextOfKin> getAllNextOfKins();

    Optional<AdmissionNextOfKin> getNextOfKinById(Long kinId);

    AdmissionNextOfKin createNextOfKin(AdmissionNextOfKin nextOfKin);

    AdmissionNextOfKin updateNextOfKin(Long kinId, AdmissionNextOfKin nextOfKin);

    void deleteNextOfKin(Long kinId);

    List<AdmissionNextOfKin> getNextOfKinByName(String name);

    List<AdmissionNextOfKin> getNextOfKinByRelationship(String relationship);

    Optional<AdmissionNextOfKin> getNextOfKinByMobileNumber(String mobileNumber);
}