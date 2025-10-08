package org.dides.studentmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dides.studentmanagement.enums.MaritalStatus;

import java.util.Date;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="PersonalInfo")
@Entity
public class AdmissionPersonInfo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;
    private boolean disability;

    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;

    private Date dateOfBirth;
    private boolean sex;
    private String citizenship;
    private String homeAddress;
    private String mobileNumber;
    private String email;
    private String contactAddress;

    @OneToOne(mappedBy = "personalInfo")
    @JsonIgnore
    private Admission admission;
}