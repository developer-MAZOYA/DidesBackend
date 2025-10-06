package org.dides.studentmanagement.model;

import jakarta.persistence.Table;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="Sponsorship")
public class AdmissionSponsor {
    private String sponsorFullName;
    private String sponsorPostalAddress;
    private String sponsorMobileNumber;
    private String sponsorEmailAddress;
    private String applicantName;
    private String institutionName = "Dodoma Institute of Development and Entrepreneurship Studies-DDES";

}
