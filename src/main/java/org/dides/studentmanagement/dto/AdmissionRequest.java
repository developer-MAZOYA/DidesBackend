package org.dides.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dides.studentmanagement.model.AdmissionEdu;
import org.dides.studentmanagement.model.AdmissionNextOfKin;
import org.dides.studentmanagement.model.AdmissionPersonInfo;
import org.dides.studentmanagement.model.AdmissionSponsor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmissionRequest {
    private AdmissionPersonInfo personalInfo;
    private AdmissionNextOfKin nextOfKin;
    private List<AdmissionEdu> educationBackgrounds;
    private AdmissionSponsor sponsor;
    private List<Long> selectedCourseIds;
    private String currentStep;
}