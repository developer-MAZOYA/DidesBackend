package org.dides.studentmanagement.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;

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

        // CPEE, CSEE, ACSEE, OTHER
        @Column(nullable = false)
        private String qualificationType;

        private String fromYear;
        private String toYear;

        private String schoolName;
        private String examinationAuthority;
        private String examinationCentre;

        private String country;
        private String division;
        private String indexNumber;

        // In case of "Other qualifications"
        private String description;

}
