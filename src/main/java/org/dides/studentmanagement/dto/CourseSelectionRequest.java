package org.dides.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSelectionRequest {
    private Long personalInfoId; // ID from personal info table
    private List<Long> selectedCourseIds; // List of course IDs from checkboxes
    private boolean submitted;
}