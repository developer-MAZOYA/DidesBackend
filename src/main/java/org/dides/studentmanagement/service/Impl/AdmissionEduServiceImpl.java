package org.dides.studentmanagement.service.Impl;

import org.dides.studentmanagement.model.AdmissionEdu;
import org.dides.studentmanagement.repo.EduRepo;
import org.dides.studentmanagement.service.AdmissionEduService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdmissionEduServiceImpl implements AdmissionEduService {

    @Autowired
    private EduRepo educationRepository;

    @Override
    public List<AdmissionEdu> getAllEducationBackgrounds() {
        return educationRepository.findAll();
    }

    @Override
    public Optional<AdmissionEdu> getEducationBackgroundById(Long eduId) {
        return educationRepository.findById(eduId);
    }

    @Override
    public AdmissionEdu createEducationBackground(AdmissionEdu educationBackground) {
        // Validate if index number and qualification type combination already exists
        if (educationBackground.getIndexNumber() != null && educationBackground.getQualificationType() != null) {
            Optional<AdmissionEdu> existing = educationRepository.findByIndexNumberAndQualificationType(
                    educationBackground.getIndexNumber(),
                    educationBackground.getQualificationType()
            );
            if (existing.isPresent()) {
                throw new RuntimeException("Education background with index number " +
                        educationBackground.getIndexNumber() + " and qualification type " +
                        educationBackground.getQualificationType() + " already exists");
            }
        }

        return educationRepository.save(educationBackground);
    }

    @Override
    public AdmissionEdu updateEducationBackground(Long eduId, AdmissionEdu educationBackground) {
        Optional<AdmissionEdu> existingEducation = educationRepository.findById(eduId);

        if (existingEducation.isPresent()) {
            AdmissionEdu updatedEducation = existingEducation.get();

            // Update all fields
            updatedEducation.setQualificationType(educationBackground.getQualificationType());
            updatedEducation.setFromYear(educationBackground.getFromYear());
            updatedEducation.setToYear(educationBackground.getToYear());
            updatedEducation.setSchoolName(educationBackground.getSchoolName());
            updatedEducation.setExaminationAuthority(educationBackground.getExaminationAuthority());
            updatedEducation.setExaminationCentre(educationBackground.getExaminationCentre());
            updatedEducation.setCountry(educationBackground.getCountry());
            updatedEducation.setDivision(educationBackground.getDivision());
            updatedEducation.setIndexNumber(educationBackground.getIndexNumber());
            updatedEducation.setDescription(educationBackground.getDescription());

            return educationRepository.save(updatedEducation);
        } else {
            throw new RuntimeException("Education background not found with id: " + eduId);
        }
    }

    @Override
    public void deleteEducationBackground(Long eduId) {
        if (educationRepository.existsById(eduId)) {
            educationRepository.deleteById(eduId);
        } else {
            throw new RuntimeException("Education background not found with id: " + eduId);
        }
    }

    @Override
    public List<AdmissionEdu> getEducationByQualificationType(String qualificationType) {
        return educationRepository.findByQualificationType(qualificationType);
    }

    @Override
    public List<AdmissionEdu> getEducationBySchoolName(String schoolName) {
        return educationRepository.findBySchoolNameContainingIgnoreCase(schoolName);
    }

    @Override
    public List<AdmissionEdu> getEducationByIndexNumber(String indexNumber) {
        return educationRepository.findByIndexNumber(indexNumber);
    }

    @Override
    public List<AdmissionEdu> getEducationByCountry(String country) {
        return educationRepository.findByCountry(country);
    }

    @Override
    public Optional<AdmissionEdu> getEducationByIndexAndQualification(String indexNumber, String qualificationType) {
        return educationRepository.findByIndexNumberAndQualificationType(indexNumber, qualificationType);
    }
}