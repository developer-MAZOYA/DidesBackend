package org.dides.studentmanagement.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dides.studentmanagement.enums.Roles;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "users")
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;

    private String lastname;

    private String registrationNumber;

    @NotNull
    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @NotNull
    private String password;

    // ✅ NEW: role column, defaults to STUDENT for backwards compatibility
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Roles role = Roles.STUDENT;
}
