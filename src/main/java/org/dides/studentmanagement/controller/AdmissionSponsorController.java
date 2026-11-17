package org.dides.studentmanagement.controller;

import org.dides.studentmanagement.model.AdmissionSponsor;
import org.dides.studentmanagement.service.AdmissionSponsorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/sponsors")
public class AdmissionSponsorController {

    private final AdmissionSponsorService sponsorService;

    public AdmissionSponsorController(AdmissionSponsorService sponsorService) {
        this.sponsorService = sponsorService;
    }

    // ---------- CRUD ----------

    @PostMapping
    public ResponseEntity<?> createSponsor(@RequestBody AdmissionSponsor sponsor) {
        try {
            AdmissionSponsor created = sponsorService.createSponsor(sponsor);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException e) {
            return error(HttpStatus.BAD_REQUEST, e);
        }
    }

    @GetMapping
    public ResponseEntity<List<AdmissionSponsor>> getAllSponsors() {
        return ResponseEntity.ok(sponsorService.getAllSponsors());
    }

    @GetMapping("/{sponsorId}")
    public ResponseEntity<?> getSponsorById(@PathVariable Long sponsorId) {
        Optional<AdmissionSponsor> sponsor = sponsorService.getSponsorById(sponsorId);
        return sponsor.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> error(HttpStatus.NOT_FOUND, "Sponsor not found with id: " + sponsorId));
    }

    @PutMapping("/{sponsorId}")
    public ResponseEntity<?> updateSponsor(@PathVariable Long sponsorId,
                                           @RequestBody AdmissionSponsor sponsor) {
        try {
            AdmissionSponsor updated = sponsorService.updateSponsor(sponsorId, sponsor);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            // "Not found" is a 404; anything else (for example, a bad sponsor type) is a 400
            HttpStatus status = isNotFound(e) ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
            return error(status, e);
        }
    }

    @DeleteMapping("/{sponsorId}")
    public ResponseEntity<?> deleteSponsor(@PathVariable Long sponsorId) {
        try {
            sponsorService.deleteSponsor(sponsorId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return error(HttpStatus.NOT_FOUND, e);
        }
    }

    // ---------- Filters and searches ----------

    @GetMapping("/type/{sponsorType}")
    public ResponseEntity<List<AdmissionSponsor>> getSponsorsByType(@PathVariable String sponsorType) {
        return ResponseEntity.ok(sponsorService.getSponsorsByType(sponsorType));
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<AdmissionSponsor>> getSponsorsByName(
            @RequestParam("sponsorName") String sponsorName) {
        return ResponseEntity.ok(sponsorService.getSponsorsByName(sponsorName));
    }

    @GetMapping("/search/applicant")
    public ResponseEntity<List<AdmissionSponsor>> getSponsorsByApplicantName(
            @RequestParam("applicantName") String applicantName) {
        return ResponseEntity.ok(sponsorService.getSponsorsByApplicantName(applicantName));
    }

    @GetMapping("/search/mobile")
    public ResponseEntity<?> getSponsorByMobileNumber(@RequestParam("mobileNumber") String mobileNumber) {
        return sponsorService.getSponsorByMobileNumber(mobileNumber)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> error(HttpStatus.NOT_FOUND, "No sponsor with mobile number " + mobileNumber));
    }

    @GetMapping("/search/email")
    public ResponseEntity<?> getSponsorByEmail(@RequestParam("email") String email) {
        return sponsorService.getSponsorByEmail(email)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> error(HttpStatus.NOT_FOUND, "No sponsor with email " + email));
    }

    // ---------- Helpers ----------

    private ResponseEntity<Map<String, String>> error(HttpStatus status, RuntimeException e) {
        String message = e.getMessage() != null ? e.getMessage() : e.getClass().getSimpleName();
        return ResponseEntity.status(status).body(Map.of("message", message));
    }

    private ResponseEntity<Map<String, String>> error(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(Map.of("message", message));
    }

    private boolean isNotFound(RuntimeException e) {
        return e.getMessage() != null && e.getMessage().toLowerCase().contains("not found");
    }
}
