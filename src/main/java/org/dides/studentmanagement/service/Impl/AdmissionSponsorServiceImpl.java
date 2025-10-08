package org.dides.studentmanagement.service.Impl;

import org.dides.studentmanagement.model.AdmissionSponsor;
import org.dides.studentmanagement.repo.SponsorShipRepo;
import org.dides.studentmanagement.service.AdmissionSponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdmissionSponsorServiceImpl implements AdmissionSponsorService {

    @Autowired
    private SponsorShipRepo sponsorRepository;

    @Override
    public AdmissionSponsor createSponsor(AdmissionSponsor sponsor) {
        // Validate required fields
        if (sponsor.getSponsorType() == null || sponsor.getSponsorType().trim().isEmpty()) {
            throw new RuntimeException("Sponsor type is required");
        }

        if (!sponsor.isAgreeTermsOfDides()) {
            throw new RuntimeException("You must agree to DIDES terms and conditions");
        }

        // Validate sponsor type
        List<String> validSponsorTypes = List.of("government", "private", "other");
        if (!validSponsorTypes.contains(sponsor.getSponsorType().toLowerCase())) {
            throw new RuntimeException("Invalid sponsor type. Must be: government, private, or other");
        }

        // Check for duplicate mobile number
        if (sponsor.getSponsorMobileNumber() != null) {
            Optional<AdmissionSponsor> existingByMobile = sponsorRepository.findBySponsorMobileNumber(
                    sponsor.getSponsorMobileNumber()
            );
            if (existingByMobile.isPresent()) {
                throw new RuntimeException("Sponsor with mobile number " +
                        sponsor.getSponsorMobileNumber() + " already exists");
            }
        }

        // Check for duplicate email
        if (sponsor.getSponsorEmailAddress() != null) {
            Optional<AdmissionSponsor> existingByEmail = sponsorRepository.findBySponsorEmailAddress(
                    sponsor.getSponsorEmailAddress()
            );
            if (existingByEmail.isPresent()) {
                throw new RuntimeException("Sponsor with email " +
                        sponsor.getSponsorEmailAddress() + " already exists");
            }
        }

        return sponsorRepository.save(sponsor);
    }

    @Override
    public List<AdmissionSponsor> getAllSponsors() {
        return sponsorRepository.findAll();
    }

    @Override
    public Optional<AdmissionSponsor> getSponsorById(Long sponsorId) {
        return sponsorRepository.findById(sponsorId);
    }

    @Override
    public AdmissionSponsor updateSponsor(Long sponsorId, AdmissionSponsor sponsor) {
        Optional<AdmissionSponsor> existingSponsor = sponsorRepository.findById(sponsorId);

        if (existingSponsor.isPresent()) {
            AdmissionSponsor updatedSponsor = existingSponsor.get();

            // Update fields
            updatedSponsor.setSponsorType(sponsor.getSponsorType());
            updatedSponsor.setSponsorFullName(sponsor.getSponsorFullName());
            updatedSponsor.setSponsorPostalAddress(sponsor.getSponsorPostalAddress());
            updatedSponsor.setSponsorMobileNumber(sponsor.getSponsorMobileNumber());
            updatedSponsor.setSponsorEmailAddress(sponsor.getSponsorEmailAddress());
            updatedSponsor.setApplicantName(sponsor.getApplicantName());
            updatedSponsor.setAgreeTermsOfDides(sponsor.isAgreeTermsOfDides());

            return sponsorRepository.save(updatedSponsor);
        } else {
            throw new RuntimeException("Sponsor not found with id: " + sponsorId);
        }
    }

    @Override
    public void deleteSponsor(Long sponsorId) {
        if (sponsorRepository.existsById(sponsorId)) {
            sponsorRepository.deleteById(sponsorId);
        } else {
            throw new RuntimeException("Sponsor not found with id: " + sponsorId);
        }
    }

    @Override
    public List<AdmissionSponsor> getSponsorsByType(String sponsorType) {
        return sponsorRepository.findBySponsorType(sponsorType);
    }

    @Override
    public List<AdmissionSponsor> getSponsorsByName(String sponsorName) {
        return sponsorRepository.findBySponsorFullNameContainingIgnoreCase(sponsorName);
    }

    @Override
    public List<AdmissionSponsor> getSponsorsByApplicantName(String applicantName) {
        return sponsorRepository.findByApplicantNameContainingIgnoreCase(applicantName);
    }

    @Override
    public Optional<AdmissionSponsor> getSponsorByMobileNumber(String mobileNumber) {
        return sponsorRepository.findBySponsorMobileNumber(mobileNumber);
    }

    @Override
    public Optional<AdmissionSponsor> getSponsorByEmail(String email) {
        return sponsorRepository.findBySponsorEmailAddress(email);
    }
}