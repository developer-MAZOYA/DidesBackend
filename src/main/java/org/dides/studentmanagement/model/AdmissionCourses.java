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


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class AdmissionCourses {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long courseId;

        private String courseName;

        // COURSE TYPES: CERTIFICATE, DIPLOMA, VETA, SHORT
        private String courseType;

        // Applies only for VETA courses (Long / Short)
        private String natureOfCourse;


        private Boolean isFree = false;


        private Boolean active = true;


}
