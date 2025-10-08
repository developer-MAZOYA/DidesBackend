package org.dides.studentmanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;
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

    private MaritalStatus maritalStatus;

    private Date dateOfBirth;

    private boolean sex;

    private String citizenship;

    private String homeAddress;

    private String mobileNumber;

    private String email;

    private String contactAddress;

}
