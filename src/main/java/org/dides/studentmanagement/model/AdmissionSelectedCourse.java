package org.dides.studentmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admission_selected_courses")
public class AdmissionSelectedCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "admission_id")
    @JsonIgnore
    private Admission admission;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private AdmissionCourses course;

    private LocalDateTime selectedAt;
    private int priorityOrder; // 1st choice, 2nd choice, etc.

    @PrePersist
    protected void onCreate() {
        selectedAt = LocalDateTime.now();
    }
}