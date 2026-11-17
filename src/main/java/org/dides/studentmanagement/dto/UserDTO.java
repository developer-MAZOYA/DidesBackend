package org.dides.studentmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.dides.studentmanagement.enums.Roles;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String firstname;
    private String lastname;
    private String email;
    private String password;          // only used on write; never returned
    private Roles role;               // ✅ NEW
    private Set<String> permissions;  // ✅ NEW (populated on read)
}
