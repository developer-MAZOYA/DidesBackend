package org.dides.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dides.studentmanagement.model.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdmissionResponse {
    private Long admissionId;
    private String applicationNumber;
    private AdmissionPersonInfo personalInfo;
    private AdmissionNextOfKin nextOfKin;
    private List<AdmissionEdu> educationBackgrounds;
    private AdmissionSponsor sponsor;
    private List<AdmissionSelectedCourse> selectedCourses; // CHANGED
    private String status;
    private String currentStep;
    private LocalDateTime createdAt;
    private LocalDateTime submittedAt;
    private String message;
}