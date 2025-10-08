package org.dides.studentmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "student_course_selections")
public class StudentCourseSelection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long selectionId;

    @Column(nullable = false)
    private Long personalInfoId; // Reference to personal info

    @Column(nullable = false)
    private String studentEmail; // Taken from personal info

    @Column(nullable = false)
    private String applicantName; // Taken from personal info

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "selected_courses_mapping",
            joinColumns = @JoinColumn(name = "selection_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<AdmissionCourses> selectedCourses = new ArrayList<>();

    private LocalDateTime selectionDate;

    private boolean submitted = false;

    @PrePersist
    protected void onCreate() {
        selectionDate = LocalDateTime.now();
    }
}