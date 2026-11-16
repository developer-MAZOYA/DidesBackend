package org.dides.studentmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dides.studentmanagement.enums.QualificationType;

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

    @Enumerated(EnumType.STRING)
    private QualificationType qualificationType; // CPEE, CSEE, ACSEE, OTHER

    @Column(name = "from_year")
    private Integer fromYear;
    @Column(name = "to_year")
    private Integer toYear;
    private String schoolName;
    private String country;
    private String division;
    private String indexNumber;


    @ManyToOne
    @JoinColumn(name = "admission_id")
    @JsonIgnore
    private Admission admission;
}
