package org.dides.studentmanagement.service.Impl;

import org.dides.studentmanagement.model.AdmissionPersonInfo;
import org.dides.studentmanagement.repo.PersonalInfoRepo;
import org.dides.studentmanagement.service.AdmissionPersonInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdmissionPersonInfoServiceImpl implements AdmissionPersonInfoService {

    @Autowired
    private PersonalInfoRepo personalInfoRepo;

    @Override
    public List<AdmissionPersonInfo> getAllPersonalInfo() {
        return personalInfoRepo.findAll();
    }

    @Override
    public Optional<AdmissionPersonInfo> getPersonalInfoById(Long id) {
        return personalInfoRepo.findById(id);
    }

    @Override
    public AdmissionPersonInfo createPersonalInfo(AdmissionPersonInfo personalInfo) {
        // Validate email uniqueness if needed
        if (personalInfo.getEmail() != null) {
            Optional<AdmissionPersonInfo> existing = personalInfoRepo.findByEmail(personalInfo.getEmail());
            if (existing.isPresent()) {
                throw new RuntimeException("Personal info with email " + personalInfo.getEmail() + " already exists");
            }
        }

        return personalInfoRepo.save(personalInfo);
    }

    @Override
    public AdmissionPersonInfo updatePersonalInfo(Long id, AdmissionPersonInfo personalInfo) {
        Optional<AdmissionPersonInfo> existingInfo = personalInfoRepo.findById(id);

        if (existingInfo.isPresent()) {
            AdmissionPersonInfo updatedInfo = existingInfo.get();

            // Update fields
            updatedInfo.setFirstname(personalInfo.getFirstname());
            updatedInfo.setLastname(personalInfo.getLastname());
            updatedInfo.setDisability(personalInfo.isDisability());
            updatedInfo.setMaritalStatus(personalInfo.getMaritalStatus());
            updatedInfo.setDateOfBirth(personalInfo.getDateOfBirth());
            updatedInfo.setSex(personalInfo.isSex());
            updatedInfo.setCitizenship(personalInfo.getCitizenship());
            updatedInfo.setHomeAddress(personalInfo.getHomeAddress());
            updatedInfo.setMobileNumber(personalInfo.getMobileNumber());
            updatedInfo.setEmail(personalInfo.getEmail());
            updatedInfo.setContactAddress(personalInfo.getContactAddress());

            return personalInfoRepo.save(updatedInfo);
        } else {
            throw new RuntimeException("Personal info not found with id: " + id);
        }
    }

    @Override
    public void deletePersonalInfo(Long id) {
        if (personalInfoRepo.existsById(id)) {
            personalInfoRepo.deleteById(id);
        } else {
            throw new RuntimeException("Personal info not found with id: " + id);
        }
    }

    @Override
    public Optional<AdmissionPersonInfo> getPersonalInfoByEmail(String email) {
        return personalInfoRepo.findByEmail(email);
    }

    @Override
    public List<AdmissionPersonInfo> getPersonalInfoByLastName(String lastName) {
        return personalInfoRepo.findByLastnameContainingIgnoreCase(lastName);
    }
}