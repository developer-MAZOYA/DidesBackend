package org.dides.studentmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name="NextOfKin")
@Entity
public class AdmissionNextOfKin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long kinId;

    private String name;
    private String mobileNumber;
    private String residence;
    private String relationship;

    @OneToOne(mappedBy = "nextOfKin")
    @JsonIgnore
    private Admission admission;
}