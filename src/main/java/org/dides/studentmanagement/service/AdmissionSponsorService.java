package org.dides.studentmanagement.service;

import org.dides.studentmanagement.model.AdmissionSponsor;
import java.util.List;
import java.util.Optional;

public interface AdmissionSponsorService {

    AdmissionSponsor createSponsor(AdmissionSponsor sponsor);

    List<AdmissionSponsor> getAllSponsors();

    Optional<AdmissionSponsor> getSponsorById(Long sponsorId);

    AdmissionSponsor updateSponsor(Long sponsorId, AdmissionSponsor sponsor);

    void deleteSponsor(Long sponsorId);

    List<AdmissionSponsor> getSponsorsByType(String sponsorType);

    List<AdmissionSponsor> getSponsorsByName(String sponsorName);

    List<AdmissionSponsor> getSponsorsByApplicantName(String applicantName);

    Optional<AdmissionSponsor> getSponsorByMobileNumber(String mobileNumber);

    Optional<AdmissionSponsor> getSponsorByEmail(String email);
}