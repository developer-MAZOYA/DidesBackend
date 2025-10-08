package org.dides.studentmanagement.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.Id;


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
}
