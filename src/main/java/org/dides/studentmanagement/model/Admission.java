package org.dides.studentmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dides.studentmanagement.enums.AdmissionStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "admissions")
public class Admission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long admissionId;

    @Column(unique = true, nullable = false)
    private String applicationNumber;

    // Step 1: Personal Information
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "personal_info_id")
    private AdmissionPersonInfo personalInfo;

    // Step 2: Next of Kin
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "next_of_kin_id")
    private AdmissionNextOfKin nextOfKin;

    // Step 3: Education Background
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admission")
    private List<AdmissionEdu> educationBackgrounds = new ArrayList<>();

    // Step 4: Sponsor Information
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "sponsor_id")
    private AdmissionSponsor sponsor;

    // Step 5: Course Selection (CHANGED - Now using AdmissionSelectedCourse)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "admission")
    private List<AdmissionSelectedCourse> selectedCourses = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private AdmissionStatus status = AdmissionStatus.DRAFT;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime submittedAt;

    private String currentStep = "personal_info";

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (applicationNumber == null) {
            applicationNumber = "APP-" + LocalDateTime.now().getYear() + "-" +
                    String.format("%03d", (int)(Math.random() * 1000));
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}

