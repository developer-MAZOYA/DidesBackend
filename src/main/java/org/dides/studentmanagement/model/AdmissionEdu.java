package org.dides.studentmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "education_background")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdmissionEdu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eduId;

    @Column(nullable = false)
    private String qualificationType; // CPEE, CSEE, ACSEE, OTHER

    private String fromYear;
    private String toYear;
    private String schoolName;
    private String examinationAuthority;
    private String examinationCentre;
    private String country;
    private String division;
    private String indexNumber;
    private String description;

    @ManyToOne
    @JoinColumn(name = "admission_id")
    @JsonIgnore
    private Admission admission;
}