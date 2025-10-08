package org.dides.studentmanagement.controller;

import org.dides.studentmanagement.model.AdmissionSponsor;
import org.dides.studentmanagement.service.AdmissionSponsorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sponsors")
public class AdmissionSponsorController {

    @Autowired
    private AdmissionSponsorService sponsorService;

    @PostMapping
    public ResponseEntity<?> createSponsor(@RequestBody AdmissionSponsor sponsor) {
        try {
            AdmissionSponsor createdSponsor = sponsorService.createSponsor(sponsor);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdSponsor);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<AdmissionSponsor>> getAllSponsors() {
        List<AdmissionSponsor> sponsors = sponsorService.getAllSponsors();
        return ResponseEntity.ok(sponsors);
    }

    @GetMapping("/{sponsorId}")
    public ResponseEntity<AdmissionSponsor> getSponsorById(@PathVariable Long sponsorId) {
        Optional<AdmissionSponsor> sponsor = sponsorService.getSponsorById(sponsorId);
        return sponsor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{sponsorId}")
    public ResponseEntity<?> updateSponsor(
            @PathVariable Long sponsorId,
            @RequestBody AdmissionSponsor sponsor) {
        try {
            AdmissionSponsor updatedSponsor = sponsorService.updateSponsor(sponsorId, sponsor);
            return ResponseEntity.ok(updatedSponsor);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{sponsorId}")
    public ResponseEntity<Void> deleteSponsor(@PathVariable Long sponsorId) {
        try {
            sponsorService.deleteSponsor(sponsorId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Filter endpoints - use when needed
    @GetMapping("/type/{sponsorType}")
    public ResponseEntity<List<AdmissionSponsor>> getSponsorsByType(@PathVariable String sponsorType) {
        List<AdmissionSponsor> sponsors = sponsorService.getSponsorsByType(sponsorType);
        return ResponseEntity.ok(sponsors);
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<AdmissionSponsor>> getSponsorsByName(@RequestParam String sponsorName) {
        List<AdmissionSponsor> sponsors = sponsorService.getSponsorsByName(sponsorName);
        return ResponseEntity.ok(sponsors);
    }

    @GetMapping("/search/applicant")
    public ResponseEntity<List<AdmissionSponsor>> getSponsorsByApplicantName(@RequestParam String applicantName) {
        List<AdmissionSponsor> sponsors = sponsorService.getSponsorsByApplicantName(applicantName);
        return ResponseEntity.ok(sponsors);
    }

    @GetMapping("/search/mobile")
    public ResponseEntity<AdmissionSponsor> getSponsorByMobileNumber(@RequestParam String mobileNumber) {
        Optional<AdmissionSponsor> sponsor = sponsorService.getSponsorByMobileNumber(mobileNumber);
        return sponsor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search/email")
    public ResponseEntity<AdmissionSponsor> getSponsorByEmail(@RequestParam String email) {
        Optional<AdmissionSponsor> sponsor = sponsorService.getSponsorByEmail(email);
        return sponsor.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}