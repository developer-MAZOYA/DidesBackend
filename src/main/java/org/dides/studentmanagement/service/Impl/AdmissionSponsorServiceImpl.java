package org.dides.studentmanagement.service.Impl;

import org.dides.studentmanagement.model.AdmissionSponsor;
import org.dides.studentmanagement.repo.SponsorShipRepo;
import org.dides.studentmanagement.service.AdmissionSponsorService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class AdmissionSponsorServiceImpl implements AdmissionSponsorService {

    private static final List<String> VALID_TYPES = List.of("government", "private", "other");

    private final SponsorShipRepo sponsorRepository;

    public AdmissionSponsorServiceImpl(SponsorShipRepo sponsorRepository) {
        this.sponsorRepository = sponsorRepository;
    }

    // ---------- Create ----------

    @Override
    @Transactional
    public AdmissionSponsor createSponsor(AdmissionSponsor sponsor) {
        if (sponsor == null) {
            throw new RuntimeException("Sponsor details are required");
        }

        sponsor.setSponsorType(normaliseType(sponsor.getSponsorType()));
        sponsor.setSponsorFullName(requireText(sponsor.getSponsorFullName(), "Sponsor full name"));
        sponsor.setSponsorPostalAddress(requireText(sponsor.getSponsorPostalAddress(), "Sponsor postal address"));
        sponsor.setSponsorMobileNumber(requireText(sponsor.getSponsorMobileNumber(), "Sponsor mobile number"));
        sponsor.setSponsorEmailAddress(requireText(sponsor.getSponsorEmailAddress(), "Sponsor email address"));
        sponsor.setApplicantName(requireText(sponsor.getApplicantName(), "Applicant name"));
        sponsor.setInstitutionName(requireText(sponsor.getInstitutionName(), "Institution name"));

        // agreeTermsOfDides is not checked here. It is recorded at final submission.
        return sponsorRepository.save(sponsor);
    }

    // ---------- Read ----------

    @Override
    @Transactional(readOnly = true)
    public List<AdmissionSponsor> getAllSponsors() {
        return sponsorRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdmissionSponsor> getSponsorById(Long sponsorId) {
        return sponsorRepository.findById(sponsorId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdmissionSponsor> getSponsorsByType(String sponsorType) {
        String type = clean(sponsorType).toLowerCase(Locale.ROOT);
        if (type.isEmpty()) {
            return List.of();
        }
        return sponsorRepository.findBySponsorType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdmissionSponsor> getSponsorsByName(String sponsorName) {
        String name = clean(sponsorName);
        if (name.isEmpty()) {
            return List.of();
        }
        return sponsorRepository.findBySponsorFullNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AdmissionSponsor> getSponsorsByApplicantName(String applicantName) {
        String name = clean(applicantName);
        if (name.isEmpty()) {
            return List.of();
        }
        return sponsorRepository.findByApplicantNameContainingIgnoreCase(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdmissionSponsor> getSponsorByMobileNumber(String mobileNumber) {
        String mobile = clean(mobileNumber);
        if (mobile.isEmpty()) {
            return Optional.empty();
        }
        // Several sponsors may share a mobile number, so return the first match
        return sponsorRepository.findBySponsorMobileNumber(mobile).stream().findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AdmissionSponsor> getSponsorByEmail(String email) {
        String value = clean(email).toLowerCase(Locale.ROOT);
        if (value.isEmpty()) {
            return Optional.empty();
        }
        return sponsorRepository.findBySponsorEmailAddress(value).stream().findFirst();
    }

    // ---------- Update ----------

    /**
     * Partial update: only fields that are sent (non-null) are changed.
     * agreeTermsOfDides is never changed here.
     */
    @Override
    @Transactional
    public AdmissionSponsor updateSponsor(Long sponsorId, AdmissionSponsor changes) {
        AdmissionSponsor existing = sponsorRepository.findById(sponsorId)
                .orElseThrow(() -> new RuntimeException("Sponsor not found with id: " + sponsorId));

        if (changes == null) {
            return existing;
        }

        if (changes.getSponsorType() != null) {
            existing.setSponsorType(normaliseType(changes.getSponsorType()));
        }
        if (changes.getSponsorFullName() != null) {
            existing.setSponsorFullName(requireText(changes.getSponsorFullName(), "Sponsor full name"));
        }
        if (changes.getSponsorPostalAddress() != null) {
            existing.setSponsorPostalAddress(requireText(changes.getSponsorPostalAddress(), "Sponsor postal address"));
        }
        if (changes.getSponsorMobileNumber() != null) {
            existing.setSponsorMobileNumber(requireText(changes.getSponsorMobileNumber(), "Sponsor mobile number"));
        }
        if (changes.getSponsorEmailAddress() != null) {
            existing.setSponsorEmailAddress(requireText(changes.getSponsorEmailAddress(), "Sponsor email address"));
        }
        if (changes.getApplicantName() != null) {
            existing.setApplicantName(requireText(changes.getApplicantName(), "Applicant name"));
        }
        if (changes.getInstitutionName() != null) {
            existing.setInstitutionName(requireText(changes.getInstitutionName(), "Institution name"));
        }

        return sponsorRepository.save(existing);
    }

    // ---------- Delete ----------

    @Override
    @Transactional
    public void deleteSponsor(Long sponsorId) {
        if (!sponsorRepository.existsById(sponsorId)) {
            throw new RuntimeException("Sponsor not found with id: " + sponsorId);
        }
        try {
            sponsorRepository.deleteById(sponsorId);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException(
                    "Sponsor " + sponsorId + " is linked to an admission and cannot be deleted");
        }
    }

    // ---------- Helpers ----------

    /** Trims a value and returns "" for null. */
    private String clean(String value) {
        return value == null ? "" : value.trim();
    }

    /** Trims a required text value and rejects blanks. */
    private String requireText(String value, String fieldName) {
        String cleaned = clean(value);
        if (cleaned.isEmpty()) {
            throw new RuntimeException(fieldName + " is required");
        }
        return cleaned;
    }

    /** Accepts any casing and spacing, and returns government, private, or other. */
    private String normaliseType(String value) {
        String type = clean(value).toLowerCase(Locale.ROOT);
        if (type.isEmpty()) {
            throw new RuntimeException("Sponsor type is required");
        }
        if (!VALID_TYPES.contains(type)) {
            throw new RuntimeException("Invalid sponsor type. Must be: government, private, or other");
        }
        return type;
    }
}
