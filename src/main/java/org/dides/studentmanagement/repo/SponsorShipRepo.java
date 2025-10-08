package org.dides.studentmanagement.repo;

import org.dides.studentmanagement.model.AdmissionSponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SponsorShipRepo extends JpaRepository<AdmissionSponsor, Long> {
    List<AdmissionSponsor> findBySponsorType(String sponsorType);

    List<AdmissionSponsor> findBySponsorFullNameContainingIgnoreCase(String sponsorFullName);

    Optional<AdmissionSponsor> findBySponsorMobileNumber(String sponsorMobileNumber);

    Optional<AdmissionSponsor> findBySponsorEmailAddress(String sponsorEmailAddress);

    List<AdmissionSponsor> findByApplicantNameContainingIgnoreCase(String applicantName);

    List<AdmissionSponsor> findByAgreeTermsOfDides(boolean agreeTermsOfDides);
}
