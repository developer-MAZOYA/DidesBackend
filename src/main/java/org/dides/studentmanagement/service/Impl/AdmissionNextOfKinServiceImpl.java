package org.dides.studentmanagement.service.Impl;

import org.dides.studentmanagement.model.AdmissionNextOfKin;
import org.dides.studentmanagement.repo.NextOfKinRepo;
import org.dides.studentmanagement.service.AdmissionNextOfKinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdmissionNextOfKinServiceImpl implements AdmissionNextOfKinService {

    @Autowired
    private NextOfKinRepo nextOfKinRepository;

    @Override
    public List<AdmissionNextOfKin> getAllNextOfKins() {
        return nextOfKinRepository.findAll();
    }

    @Override
    public Optional<AdmissionNextOfKin> getNextOfKinById(Long kinId) {
        return nextOfKinRepository.findById(kinId);
    }

    @Override
    public AdmissionNextOfKin createNextOfKin(AdmissionNextOfKin nextOfKin) {
        // Validate mobile number uniqueness if needed
        if (nextOfKin.getMobileNumber() != null) {
            Optional<AdmissionNextOfKin> existing = nextOfKinRepository.findByMobileNumber(nextOfKin.getMobileNumber());
            if (existing.isPresent()) {
                throw new RuntimeException("Next of kin with mobile number " + nextOfKin.getMobileNumber() + " already exists");
            }
        }

        return nextOfKinRepository.save(nextOfKin);
    }

    @Override
    public AdmissionNextOfKin updateNextOfKin(Long kinId, AdmissionNextOfKin nextOfKin) {
        Optional<AdmissionNextOfKin> existingKin = nextOfKinRepository.findById(kinId);

        if (existingKin.isPresent()) {
            AdmissionNextOfKin updatedKin = existingKin.get();

            // Update fields
            updatedKin.setName(nextOfKin.getName());
            updatedKin.setMobileNumber(nextOfKin.getMobileNumber());
            updatedKin.setResidence(nextOfKin.getResidence());
            updatedKin.setRelationship(nextOfKin.getRelationship());

            return nextOfKinRepository.save(updatedKin);
        } else {
            throw new RuntimeException("Next of kin not found with id: " + kinId);
        }
    }

    @Override
    public void deleteNextOfKin(Long kinId) {
        if (nextOfKinRepository.existsById(kinId)) {
            nextOfKinRepository.deleteById(kinId);
        } else {
            throw new RuntimeException("Next of kin not found with id: " + kinId);
        }
    }

    @Override
    public List<AdmissionNextOfKin> getNextOfKinByName(String name) {
        return nextOfKinRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public List<AdmissionNextOfKin> getNextOfKinByRelationship(String relationship) {
        return nextOfKinRepository.findByRelationship(relationship);
    }

    @Override
    public Optional<AdmissionNextOfKin> getNextOfKinByMobileNumber(String mobileNumber) {
        return nextOfKinRepository.findByMobileNumber(mobileNumber);
    }
}