package org.dides.studentmanagement.controller;

import org.dides.studentmanagement.dto.AuthRequest;
import org.dides.studentmanagement.dto.AuthResponse;
import org.dides.studentmanagement.dto.UserDTO;
import org.dides.studentmanagement.enums.Roles;
import org.dides.studentmanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
public class UsersController {

    @Autowired
    private UserService userService;

    // ---------------- PUBLIC ----------------

    @PostMapping("/auth/register")
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {
        userDTO.setRole(Roles.STUDENT);              // ✅ public signup = STUDENT only
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(userDTO));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            AuthResponse res = userService.verify(authRequest);
            return ResponseEntity.ok(res);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid username or password"));
        }
    }

    // ---------------- PROTECTED ----------------

    @GetMapping("/list-All")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.list());
    }

    @GetMapping("/get-user/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','DIRECTOR') or #id == authentication.principal.id")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<UserDTO> userDTO = userService.findById(id);
        return userDTO.<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", "User not found")));
    }

    @PutMapping("/update-user/{id}")
    @PreAuthorize("hasAuthority('USER_UPDATE')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, userDTO));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "User not found for update"));
        }
    }

    // ✅ NEW: admin-only role assignment
    @PutMapping("/admin/users/{id}/role")
    @PreAuthorize("hasAuthority('ROLE_MANAGE')")
    public ResponseEntity<?> updateRole(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            Roles role = Roles.valueOf(body.get("role"));
            return ResponseEntity.ok(userService.updateRole(id, role));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", "Invalid role"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", "User not found"));
        }
    }
}
