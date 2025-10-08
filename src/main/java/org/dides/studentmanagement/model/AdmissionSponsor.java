package org.dides.studentmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name="Sponsorship")
public class AdmissionSponsor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sponsorId;

    private String sponsorType; // government, private, other
    private String sponsorFullName;
    private String sponsorPostalAddress;
    private String sponsorMobileNumber;
    private String sponsorEmailAddress;
    private String applicantName;
    private String institutionName = "Dodoma Institute of Development and Entrepreneurship Studies-DIDES";
    private boolean agreeTermsOfDides;

    @OneToOne(mappedBy = "sponsor")
    @JsonIgnore
    private Admission admission;
}