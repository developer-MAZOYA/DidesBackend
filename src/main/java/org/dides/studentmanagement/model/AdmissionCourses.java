package org.dides.studentmanagement.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AdmissionCourses {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String courseName;

        // COURSE TYPES: CERTIFICATE, DIPLOMA, VETA, SHORT
        private String courseType;

        // Applies only for VETA courses (Long / Short)
        private String natureOfCourse;


        private Boolean isFree = false;


        private Boolean active = true;


}
