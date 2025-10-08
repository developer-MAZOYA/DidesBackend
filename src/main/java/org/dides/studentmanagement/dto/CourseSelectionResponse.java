package org.dides.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dides.studentmanagement.model.AdmissionCourses;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSelectionResponse {
    private Long selectionId;
    private Long personalInfoId;
    private String studentEmail;
    private String applicantName;
    private List<AdmissionCourses> selectedCourses;
    private LocalDateTime selectionDate;
    private boolean submitted;
    private int totalCoursesSelected;
    private String message;
}