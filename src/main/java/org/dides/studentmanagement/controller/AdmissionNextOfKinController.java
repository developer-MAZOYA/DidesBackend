package org.dides.studentmanagement.controller;

import org.dides.studentmanagement.model.AdmissionNextOfKin;
import org.dides.studentmanagement.service.AdmissionNextOfKinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/next-of-kin")
public class AdmissionNextOfKinController {

    @Autowired
    private AdmissionNextOfKinService nextOfKinService;

    @GetMapping
    public ResponseEntity<List<AdmissionNextOfKin>> getAllNextOfKins() {
        List<AdmissionNextOfKin> nextOfKins = nextOfKinService.getAllNextOfKins();
        return ResponseEntity.ok(nextOfKins);
    }

    @GetMapping("/{kinId}")
    public ResponseEntity<AdmissionNextOfKin> getNextOfKinById(@PathVariable Long kinId) {
        Optional<AdmissionNextOfKin> nextOfKin = nextOfKinService.getNextOfKinById(kinId);
        return nextOfKin.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<AdmissionNextOfKin> createNextOfKin(@RequestBody AdmissionNextOfKin nextOfKin) {
        try {
            AdmissionNextOfKin createdKin = nextOfKinService.createNextOfKin(nextOfKin);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdKin);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{kinId}")
    public ResponseEntity<AdmissionNextOfKin> updateNextOfKin(
            @PathVariable Long kinId,
            @RequestBody AdmissionNextOfKin nextOfKin) {
        try {
            AdmissionNextOfKin updatedKin = nextOfKinService.updateNextOfKin(kinId, nextOfKin);
            return ResponseEntity.ok(updatedKin);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{kinId}")
    public ResponseEntity<Void> deleteNextOfKin(@PathVariable Long kinId) {
        try {
            nextOfKinService.deleteNextOfKin(kinId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search/name")
    public ResponseEntity<List<AdmissionNextOfKin>> getNextOfKinByName(@RequestParam String name) {
        List<AdmissionNextOfKin> nextOfKins = nextOfKinService.getNextOfKinByName(name);
        return ResponseEntity.ok(nextOfKins);
    }

    @GetMapping("/search/relationship")
    public ResponseEntity<List<AdmissionNextOfKin>> getNextOfKinByRelationship(@RequestParam String relationship) {
        List<AdmissionNextOfKin> nextOfKins = nextOfKinService.getNextOfKinByRelationship(relationship);
        return ResponseEntity.ok(nextOfKins);
    }

    @GetMapping("/search/mobile")
    public ResponseEntity<AdmissionNextOfKin> getNextOfKinByMobileNumber(@RequestParam String mobileNumber) {
        Optional<AdmissionNextOfKin> nextOfKin = nextOfKinService.getNextOfKinByMobileNumber(mobileNumber);
        return nextOfKin.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}