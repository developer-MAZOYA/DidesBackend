package org.dides.studentmanagement.service;

import org.dides.studentmanagement.dto.AuthRequest;
import org.dides.studentmanagement.dto.AuthResponse;
import org.dides.studentmanagement.dto.UserDTO;
import org.dides.studentmanagement.enums.Roles;
import org.dides.studentmanagement.model.UserPrincipal;
import org.dides.studentmanagement.model.Users;
import org.dides.studentmanagement.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public UserService(UserRepo userRepository) {
        this.userRepo = userRepository;
    }

    // ---------- mapper ----------
    private UserDTO toDto(Users u) {
        UserDTO d = new UserDTO();
        d.setFirstname(u.getFirstname());
        d.setLastname(u.getLastname());
        d.setEmail(u.getEmail());
        d.setRole(u.getRole());
        if (u.getRole() != null) {
            d.setPermissions(u.getRole().getPermissions()
                    .stream().map(Enum::name).collect(Collectors.toSet()));
        }
        return d; // no password
    }

    // ---------- register ----------
    public UserDTO register(UserDTO dto) {
        Users user = new Users();
        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setRole(dto.getRole() != null ? dto.getRole() : Roles.STUDENT);
        return toDto(userRepo.save(user));
    }

    // ---------- login ----------
    public AuthResponse verify(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(), authRequest.getPassword()));

        if (!authentication.isAuthenticated()) {
            throw new RuntimeException("Authentication failed");
        }

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        Users user = principal.getUser();

        String token = jwtService.generateToken(
                user.getEmail(), user.getFirstname(), user.getLastname());

        Set<String> roles = Set.of(user.getRole().name());
        Set<String> perms = user.getRole().getPermissions().stream()
                .map(Enum::name).collect(Collectors.toSet());

        return new AuthResponse(token, roles, perms);
    }

    // ---------- change password ----------
    public ResponseEntity<?> changePassword(String authorizationHeader,
                                            String currentPassword,
                                            String newPassword) {
        try {
            String token = extractTokenFromHeader(authorizationHeader);
            if (token == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Missing or invalid Authorization header"));
            }

            if (!jwtService.validateToken(token)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Invalid or expired token"));
            }

            String email = jwtService.extractUsername(token);
            if (email == null || email.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Collections.singletonMap("error", "Invalid token payload"));
            }

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, currentPassword));

            if (authentication.isAuthenticated()) {
                Users user = userRepo.findByEmail(email);
                if (user == null) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Collections.singletonMap("error", "User not found"));
                }
                user.setPassword(encoder.encode(newPassword));
                userRepo.save(user);
                return ResponseEntity.ok(Collections.singletonMap("message", "Password updated successfully"));
            }
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Current password is incorrect"));
        } catch (Exception e) {
            System.err.println("Password change error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Password change failed. Please try again."));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("error", "Authentication failed"));
    }

    private String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    // ---------- CRUD ----------
    public List<UserDTO> list() {
        return userRepo.findAll().stream().map(this::toDto).collect(Collectors.toList());
    }

    public Optional<UserDTO> findById(Long id) {
        return userRepo.findById(id).map(this::toDto);
    }

    public UserDTO updateUser(Long id, UserDTO dto) {
        Users user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFirstname(dto.getFirstname());
        user.setLastname(dto.getLastname());
        user.setEmail(dto.getEmail());

        if (dto.getPassword() != null && !dto.getPassword().trim().isEmpty()) {
            user.setPassword(encoder.encode(dto.getPassword()));
        }
        // ✅ allow role change here too (protected at controller level)
        if (dto.getRole() != null) {
            user.setRole(dto.getRole());
        }

        return toDto(userRepo.save(user));
    }

    // ✅ NEW: admin-only role update (keeps updateUser focused)
    public UserDTO updateRole(Long id, Roles role) {
        Users user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        return toDto(userRepo.save(user));
    }

    public Users getUserById(Long userId) {
        return userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<String> getAllNames() {
        return userRepo.findAll().stream().map(Users::getFirstname).toList();
    }
}
