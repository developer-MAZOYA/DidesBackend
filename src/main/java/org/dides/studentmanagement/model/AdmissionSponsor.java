package org.dides.studentmanagement.model;

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
    //has one of the 3 either values{government,private or other}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sponsorId;
    private String sponsorType;
    private String sponsorFullName;
    private String sponsorPostalAddress;
    private String sponsorMobileNumber;
    private String sponsorEmailAddress;
    private String applicantName;
    private String institutionName = "Dodoma Institute of Development and Entrepreneurship Studies-DIDES";


    // for declaration
    private boolean agreeTermsOfDides;

}
